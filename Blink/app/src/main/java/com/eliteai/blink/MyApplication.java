package com.eliteai.blink;

import com.bailingcloud.bailingvideo.BlinkEngine;
import com.eliteai.blink.utils.LogUtlis;
import com.eliteai.zzkframe.ApplicationManager;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 14:51
 */
public class MyApplication extends ApplicationManager {

    public static DataCache dataCache;

    @Override
    public void onCreate() {
        super.onCreate();
        dataCache = DataCache.getInstance(this);
        //百灵sdk初始化
        BlinkEngine mInit = BlinkEngine.init(getApplicationContext(), C.Net.BlinkCmpServer);
        LogUtlis.e("wesker","mInit : " + mInit);
    }
}
