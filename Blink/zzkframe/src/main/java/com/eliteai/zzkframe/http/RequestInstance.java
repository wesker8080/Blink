package com.eliteai.zzkframe.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eliteai.zzkframe.utils.SimpleImageLoader;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/13
 ***************************************/
public class RequestInstance {
    public static final String TAG = RequestInstance.class
            .getSimpleName();
    private static RequestInstance requestInstance = null;
    private RequestQueue mRequestQueue;
    private SimpleImageLoader loader;

    public static RequestInstance getInstance(Context context) {
        if (requestInstance == null) {
            synchronized (RequestInstance.class) {
                if (requestInstance == null) {
                    requestInstance = new RequestInstance(context);
                }
            }
        }
        return requestInstance;
    }

    public static RequestInstance getInstance() {
        return requestInstance;
    }

    public RequestInstance(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        loader = SimpleImageLoader.getInstance(context, mRequestQueue);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public SimpleImageLoader getLoader() {
        return loader;
    }

    public <T> void addToRequestQueue(Request<T> req, Object tag) {
        // set the default tag if tag is empty
        req.setTag(tag == null ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
        Log.i("info","=====addToRequestQueue=====");
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
