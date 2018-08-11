package com.eliteai.blink.http;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 14:47
 */
public class NetLoopRequest {

    public static NetLoopRequest sInstance;
    private NetLoopRequest() {

    }

    public static NetLoopRequest getInstance() {
        NetLoopRequest instance = sInstance;
        if (sInstance == null) {
            synchronized (NetLoopRequest.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new NetLoopRequest();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }
}
