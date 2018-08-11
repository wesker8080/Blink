package com.eliteai.zzkframe.http;

import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eliteai.zzkframe.http.net.NetUtil;
import com.eliteai.zzkframe.utils.BitmapTools;
import com.eliteai.zzkframe.utils.LogUtils;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/13
 ***************************************/
public class HttpRequest {
    public enum RequstType {GET, POST}

    public enum ImageShapeType {NORMAL, ROUNDCORNER, ROUND}

    public static final int BITMAP_ROUNDCORNER = 10;

    //    根据map组合get路径
    public static String geturl(final Map<String, String> params, String url) {
        String result;

        StringBuffer getparams = new StringBuffer();

        Set set = params.entrySet();
        Iterator it = set.iterator();
        if (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it
                    .next();
            getparams.append("?" + entry.getKey() + "=" + entry.getValue());
        }
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it
                    .next();
            getparams.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        result = url + getparams.toString();
        String resutf8 = "";
        try {
            resutf8 = new String(result.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resutf8;
    }


    //    post上传图片,文件等
    public static Request requestPostCustom(HttpCallBack listener,
                                            MultipartEntity multipartEntity,
                                            String url, Object tag, boolean shouldCache) {
        Log.e("ddd", "--------post上传图片,文件等------------"+url);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(multipartEntity.getContent()));
            String line = null;
            while ((line = br.readLine()) != null) {
                Log.d("info", line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpListener httpListener = new HttpListener(listener);
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST,
                url,
                httpListener, httpListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (NetUtil.defaultRequestMaps != null)
                    map.putAll(NetUtil.defaultRequestMaps);
                return map;
            }
        };
        // 获取MultipartEntity对象
        //        MultipartEntity multipartEntity = multipartRequest.getMultiPartEntity();
        multipartRequest.setMultiPartEntity(multipartEntity);
        multipartRequest.setShouldCache(shouldCache);
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(90 * 1000, 0, 0));
        RequestInstance.getInstance().addToRequestQueue(multipartRequest, tag);
        return multipartRequest;
    }

   /* //    get请求
    public static Request requestGet(HttpCallBack callBack,
                                      final Map<String, String> params, String url, Object tag, boolean shouldCache) {
        HttpListener listener = new HttpListener(callBack);
        String geturl = geturl(params, url);
        Log.i(tag.toString(), geturl);
        StringRequest stringequest = new StringRequest(Request.Method.GET, geturl,
                listener, listener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (NetUtil.defaultRequestMaps != null)
                    map.putAll(NetUtil.defaultRequestMaps);
                return map;
            }
        };
        stringequest.setShouldCache(shouldCache);
        stringequest.setRetryPolicy(new DefaultRetryPolicy(8000, 0, 0));

        RequestInstance.getInstance().addToRequestQueue(stringequest, tag);

        return stringequest;
    }*/

    /* //    post请求
     public static Request requestPost(HttpCallBack callBack,
                                        final Map<String, String> params,
                                        String url, Object tag, boolean shouldCache) {
         HttpListener listener = new HttpListener(callBack);
         Log.i(tag.toString(), url);
         StringRequest stringequest = new StringRequest(Request.Method.POST, url,
                 listener, listener) {
             @Override
             public Map<String, String> getHeaders() throws AuthFailureError {
                 Map<String, String> map = new HashMap<>();
                 if (NetUtil.defaultRequestMaps != null){
                     map.putAll(NetUtil.defaultRequestMaps);
                 }
                 return map;
             }

             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 // 在这里设置需要post的参数
 //                Map<String, String> x = params;
                 return params;
             }
         };
         stringequest.setShouldCache(shouldCache);
         stringequest.setRetryPolicy(new DefaultRetryPolicy(8000, 0, 0));
         RequestInstance.getInstance().addToRequestQueue(stringequest, tag);

         return stringequest;
     }*/
    //    post请求--post json的方式
    public static Request requestPost1(int method, HttpCallBack callBack,
                                       final Map<String, Object> params,
                                       String url, Object tag, boolean shouldCache) {
        HttpJsonListener listener = new HttpJsonListener(callBack);
        Log.e("request", url);
        JSONObject object = new JSONObject(params);
        Log.e("request", object.toString());
        LogUtils.writeLog(url + "  " + object.toString(), true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, object, listener, listener) {

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

        };
//        StringRequest stringequest = new StringRequest(Request.Method.POST, url,
//                listener, listener) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                if (NetUtil.defaultRequestMaps != null){
//                    map.putAll(NetUtil.defaultRequestMaps);
//                }
//                return map;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                // 在这里设置需要post的参数
////                Map<String, String> x = params;
//                return params;
//            }
//        };
        jsonObjectRequest.setShouldCache(shouldCache);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(90 * 1000, 0, 0));
        RequestInstance mInstance = RequestInstance.getInstance();
        RequestInstance.getInstance().addToRequestQueue(jsonObjectRequest, tag);
        return jsonObjectRequest;
    }

    /***********************************************************
     * Description: 网络请求
     *
     * @param: requestType
     * 请求类型
     * @param: listener
     * 回调接口
     * @param: params
     * 要请求的map数据
     * @param: url 请求地址
     * @param: 请求的tag标示，用于取消请求
     * @return: 无
     ***********************************************************/
    public static Request request(RequstType requstType, HttpCallBack listener,
                                  Map<String, Object> params,
                                  String url, Object tag, boolean shouldCache) {
//        添加token, 应该由业务层做
//        addons(params);
        switch (requstType) {
            case GET:
//                return requestGet(listener, params, url, tag,shouldCache);
                return requestPost1(Request.Method.GET, listener, params, url, tag, shouldCache);
            case POST:
//                return requestPost(listener, params, url, tag,shouldCache);
                return requestPost1(Request.Method.POST, listener, params, url, tag, shouldCache);
            default:
                return null;
        }

    }

    //    取代volley默认的图片监听，加入图片转换
    public static ImageLoader.ImageListener getImageListener(final ImageView view, final String url,
                                                             final int defaultImageResId, final int errorImageResId, final ImageShapeType type) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (view == null) {
                    return;
                }
                if (response != null && response.getBitmap() != null) {
                    if (url.equals(view.getTag().toString())) {
                        switch (type) {
                            case ROUND:
                                view.setImageBitmap(BitmapTools.toRound(response.getBitmap()));
                                break;
                            case ROUNDCORNER:
                                view.setImageBitmap(BitmapTools.toRoundCorner(response.getBitmap(), BITMAP_ROUNDCORNER));
                                break;
                            default:
                                view.setImageBitmap(response.getBitmap());
                                break;
                        }
                    }
                } else {
                    if (defaultImageResId != 0) {
                        Log.d("image", "----defaultImageResId------");
                        view.setImageResource(defaultImageResId);
                    }
                }
            }
        };
    }

    /***********************************************************
     * Description: 取消网络请求
     *
     * @param: Object
     * 请求的tag
     * @return: 无
     ***********************************************************/
    public static void cancelRequest(Object tag) {
        RequestInstance.getInstance().cancelPendingRequests(tag);
    }

    /***********************************************************
     * 网络请求图片
     *
     * @param: imageurl
     * 图片地址
     * @param: view
     * 加载改图片的view
     * @param: defaultImageResId 默认图片资源id
     * @param: errorImageResId 错误时加载的图片id
     * @param: type 接收的图片类型，矩形，圆角，圆形
     * @return: 无
     ***********************************************************/
    public static void requestImage(final String imageurl, final ImageView view,
                                    final int defaultImageResId, final int errorImageResId, final ImageShapeType type) {
        ImageLoader imageLoader = RequestInstance.getInstance().getLoader();

//        可以指定载入图片是原始矩形还是圆角矩形，还是圆形
        imageLoader.get(imageurl, getImageListener(view, imageurl, defaultImageResId, errorImageResId, type));
    }
}
