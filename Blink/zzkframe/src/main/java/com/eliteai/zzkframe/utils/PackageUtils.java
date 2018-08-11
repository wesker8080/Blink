package com.eliteai.zzkframe.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwb
 * Description
 * Date 16/8/25.
 */
public class PackageUtils {

    public static String getVersionName(Context context){
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        String name = null;
        try{
            info = manager.getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES);
            name = info.versionName+"."+info.versionCode;
        }catch (Exception e){
            e.printStackTrace();
        }

        return name == null ? "V1.0.1":"V"+name;
    }

    public static int getVersionCode(Context context){
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        int name = 100;
        try{
            info = manager.getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES);
            name = info.versionCode;
        }catch (Exception e){
            e.printStackTrace();
        }

        return name;
    }

    /*
    * 检查手机上是否安装了指定的软件
    * @param context
    * @param packageName：应用包名
    * @return
            */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 跳转到google play进行好评
     * @param context
     * @param packageName
     */
    public static void goGooglePlay(Context context,String packageName){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));

        }
    }
}
