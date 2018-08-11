package com.eliteai.blink;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 14:52
 */
public class DataCache {
    private static final String SHARENAME = "eliteai";
    private static volatile DataCache sInstance;

    private static SharedPreferences sPreferences;

    private DataCache(Context context) {
        if (sPreferences == null) {
            sPreferences = context.getSharedPreferences(SHARENAME, Context.MODE_PRIVATE);
        }
    }
    /**
     * 保证多线程安全，及高性能，volatile变量的操作，是不允许和它之前的读写操作打乱顺序。
     *
     * @return
     */
    public static DataCache getInstance(Context context) {
        DataCache instance = sInstance;
        if (sInstance == null) {
            synchronized (DataCache.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new DataCache(context);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }
    /**
     * 获取服务器地址
     *
     * @return 获取服务器地址，切换服务器时要用
     */
    public String getServerPath() {
        return sPreferences.getString("server_path", "http://47.106.156.161:8080/api");
    }
    /**
     * 保存服务器地址
     *
     * @param url:服务器地址
     */
    public void saveServerPath(String url) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("server_path", url);
        editor.apply();
    }

    public String getBlinkToken() {
        return sPreferences.getString("blink_token", null);
    }

    public void setBlinkToken(String token) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("blink_token", token);
        editor.apply();
    }

    public String getBlinkUid() {
        return sPreferences.getString("blink_uid", null);
    }

    public void setBlinkUid(String uid) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("blink_uid", uid);
        editor.apply();
    }
}
