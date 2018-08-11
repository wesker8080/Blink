package com.eliteai.zzkframe.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zwb
 * Description
 * Date 16/8/19.
 */
public class FileUtils {
    /**
     * sd卡的根目录
     */
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
    /**
     * 手机的缓存根目录
     */
    private static String mDataRootPath = null;
    /**
     * 保存Image的目录名
     */
    private final static String FOLDER_NAME = "/PetCircleImages";

    private Context mContext;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader" + mCount.getAndIncrement());
        }
    };
    private ThreadPoolExecutor threadPoolExecutor;

    public FileUtils(Context context) {
        mDataRootPath = context.getCacheDir().getPath();
        mContext = context;
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(128), THREAD_FACTORY);
    }


    /**
     * 获取储存Image的目录
     *
     * @return
     */
    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
    }

    /**
     * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
     *
     * @param fileName
     * @param bitmap
     * @throws IOException
     */
    public void savaBitmap(final String fileName, final Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (isFileExists(fileName)) {
            return;
        }
        final String path = getStorageDirectory();
//        Log.e("info","====image_path======="+path);
        File folderFile = new File(path);
        if (!folderFile.exists()) {
            folderFile.mkdir();
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                File file = new File(path + File.separator + fileName);
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    file.delete();
                }
                /**
                 * 通知相册更新图片,否则系统相册显示异常
                 */
                Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                mContext.sendBroadcast(media);
            }
        };
        threadPoolExecutor.execute(runnable);

    }


    /**
     * 从手机或者sd卡获取Bitmap
     *
     * @param fileName
     * @return
     */
    public Bitmap getBitmap(String fileName) {
        return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + fileName);
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public boolean isFileExists(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).exists();
    }

    /**
     * 获取文件的大小
     *
     * @param fileName
     * @return
     */
    public long getFileSize(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).length();
    }

    /**
     * 删除SD卡或者手机的缓存图片和目录
     */
    public void deleteFile() {
        File dirFile = new File(getStorageDirectory());
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                File file = new File(dirFile, children[i]);
                file.delete();
                Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                mContext.sendBroadcast(media);
            }
        }
        dirFile.delete();

    }

    /*
    * 删除SD卡或者手机的缓存图片和目录
    */
    public void deleteFile(File dirFile) {
        if (dirFile == null || !dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                File file = new File(dirFile, children[i]);
                file.delete();
                Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                mContext.sendBroadcast(media);
            }
        }
        dirFile.delete();
    }

    /**
     * 获取文件的大小
     *
     * @return
     */
    public long getFileSize(File file) {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.isDirectory()) {
            File[] file1 = file.listFiles();
            for (File file2 : file1) {
                size += getFileSize(file2);
            }
        } else {
            size += file.length();
        }
        return size;
    }

    /***
     * 转换文件大小单位(b/kb/mb/gb)
     ***/
    public String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            fileSizeString = "00.00B";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获取图片地址/目录
     */
    public String getDirName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        int lastIndex = fileName.lastIndexOf("/");
        return fileName.substring(0, lastIndex);
    }

    /**
     * 获取图片地址/最后面的地址
     */
    public String getFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        int lastIndex = fileName.lastIndexOf("/");
        String url = fileName.substring(lastIndex + 1);
        if (url.endsWith(".jpg") || url.endsWith(".png")) {
            return url;
        }
        if (url.contains(".jpg")) {
            url = url.substring(0, url.lastIndexOf(".jpg") + 4);
        } else if (url.contains(".png")) {
            url = url.substring(0, url.lastIndexOf(".png") + 4);
        }
//        Log.d("bitmap","======"+url);
        return url;
    }

    /**
     * 获得缓存的大小
     *
     * @return
     */
    public String getCacheSize() {
        String size = "00.00B";
        /**
         * 内部缓存
         */
        File file = mContext.getCacheDir();
        long size1 = getFileSize(file);
        long size2 = 0;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file2 = mContext.getExternalCacheDir();
            size2 = getFileSize(file2);
        }
        size = FormetFileSize(size1 + size2);
        return size;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        File file = mContext.getCacheDir();
        deleteFile(file);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file2 = mContext.getExternalCacheDir();
            deleteFile(file2);
        }
    }
}
