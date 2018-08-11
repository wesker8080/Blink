package com.eliteai.blink.http;

import android.widget.ImageView;

import com.eliteai.blink.C;
import com.eliteai.blink.MyApplication;
import com.eliteai.zzkframe.http.HttpRequest;
import com.eliteai.zzkframe.http.net.NetUtil;
import com.eliteai.zzkframe.http.net.OnNetEventListener;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 14:49
 */
public class Net {
    public static class NetInstance {
        //        public static NetUtil netUtil = new NetUtil(com.eliteai.blink.C.Net.DEBUG_URL);
        public static NetUtil netUtil = new NetUtil(MyApplication.dataCache.getServerPath());
        public static NetApi apiIntance = (NetApi) Proxy.newProxyInstance(
                NetApi.class.getClassLoader(),
                new Class[]{NetApi.class},
                netUtil);

        public static void setDefaultParams() {
            Map<String, String> defaultRequestParams = new HashMap<>();
            netUtil.setDefaultParams(defaultRequestParams);
        }

        public static void setTimeStamp(String stamp) {
            netUtil.addDefaultRequestMaps("timestamp", stamp);
        }

        public static void setToken(String token) {

            netUtil.addDefaultRequestMaps("token", token);
        }

        public static void setUserId(long userId) {
            netUtil.addDefaultRequestMaps("uid", userId + "");
        }

        public static void setMobile(String mobile) {
            netUtil.addDefaultRequestMaps("mobile", mobile);
        }

        public static void clearDefaultRequest() {
            setDefaultParams();
        }
    }


    public static NetApi get(OnNetEventListener listener) {
        return get(true, listener,null);
    }

    /**
     * 主动设置url，有关图片的操作，有一个单独的url，无关设备的操作，有一个单独的url，关于设备的操作，动态获取url
     * @param listener
     * @param url
     * @return
     */
    public static NetApi get(OnNetEventListener listener,String url) {
        return get(true, listener,url);
    }

    public static NetApi get(boolean shouldCache, OnNetEventListener listener,String url) {
        NetInstance.netUtil.setShouldCache(shouldCache);
        NetInstance.netUtil.setListener(listener);
        if(url == null){
            NetInstance.netUtil.setHOST(C.Net.DEBUG_URL);
        }else {
            NetInstance.netUtil.setHOST(url);
        }
        return NetInstance.apiIntance;
    }

    public static void imageLoader(final String imageurl, final ImageView view,
                                   final int defaultImageResId, final int errorImageResId, final HttpRequest.ImageShapeType type) {
        HttpRequest.requestImage(imageurl,
                view, defaultImageResId, errorImageResId, type);
    }
}
