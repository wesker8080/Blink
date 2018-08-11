package com.eliteai.zzkframe.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zwb
 * Description 抓取日志
 * Date 2017/3/6.
 */

public class LogUtils {

    /**
     * 写入日志
     *
     * @param content 内容
     * @param flag    是否写入
     */
    public static void writeLog(String content, boolean flag) {
        flag = false;
        if (!flag) {
            return;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "petCircle_log/";
            File dir = new File(PATH);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e("LogUtils", "Log Directory not creat");
                }
            }
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            File file = new File(PATH + "log" + ".txt");
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                pw.print(time);
                pw.println(TimeZone.getDefault().getID());
                pw.println(content);
                pw.println();
                pw.close();
            } catch (Exception e) {

            }
        }
    }
}
