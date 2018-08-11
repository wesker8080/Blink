package com.eliteai.zzkframe;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.eliteai.zzkframe.http.RequestInstance;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/16
 ***************************************/
public class ApplicationManager extends MultiDexApplication {
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RequestInstance.getInstance(this);
        application = this;
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(this);
//        }
    }

    @Override
    public void onLowMemory() {
        Log.e("runtesttag", "\n===================================");
        Log.e("runtesttag", "========!!!!!!!!!!!!!!!!!!=========");
        Log.e("runtesttag", "=                                 =");
        Log.e("runtesttag", "====系统内存过低,全局参数即将被回收========");
        Log.e("runtesttag", "=                                 =");
        Log.e("runtesttag", "===================================");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        Log.e("runtesttag", "\n===================================");
        Log.e("runtesttag", "========!!!!!!!!!!!!!!!!!!=========");
        Log.e("runtesttag", "=                                 =");
        Log.e("runtesttag", "====系统内存过低,application被回收======");
        Log.e("runtesttag", "=                                 =");
        Log.e("runtesttag", "===================================");
        super.onTerminate();
    }
}
