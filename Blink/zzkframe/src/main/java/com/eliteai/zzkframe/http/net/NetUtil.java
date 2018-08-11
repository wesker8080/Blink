package com.eliteai.zzkframe.http.net;

import android.graphics.Bitmap;
import android.util.Log;

import com.eliteai.zzkframe.http.HttpRequest;
import com.eliteai.zzkframe.http.MultipartEntity;
import com.eliteai.zzkframe.http.help.GET;
import com.eliteai.zzkframe.http.help.PARAMS;
import com.eliteai.zzkframe.http.help.POST;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/13
 ***************************************/
public class NetUtil implements InvocationHandler {

    static final int REQUEST_FILE = 1;
    static final int REQUEST_DEFAULT = 3;

    public static Map<String, String> defaultRequestMaps;
    private OnNetEventListener listener;
    private String HOST = "";

    public NetUtil(String host) {
        HOST = host;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    boolean shouldCache = true;

    public void setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
    }

    private static final java.lang.String TAG = "NetUtil";

    public void setListener(OnNetEventListener listener) {
        this.listener = listener;
    }

    public static void setDefaultParams(Map<String, String> defaultRequestMap) {
        defaultRequestMaps = defaultRequestMap;
    }

    public static void addDefaultRequestMaps(String key, String value) {
        if (defaultRequestMaps == null) return;
        if (defaultRequestMaps.containsKey(key) && value == null) {//如果以前包含了token的值，这时候设置value是null就清除以前的token
            defaultRequestMaps.remove(key);
        } else if (value != null) {
            defaultRequestMaps.put(key, value);
        }
    }

    public static void setToken(String key, String value) {
        addDefaultRequestMaps(key, value);
    }

    public static void setUserId(String key, String value) {
        addDefaultRequestMaps(key, value);
    }

    public static void clearDefaultParams() {
        if (defaultRequestMaps != null) {
            defaultRequestMaps.clear();
        }
    }

    @Override
    public NetRequest invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String url = null;
        HttpRequest.RequstType type = null;
        int requestType = containFileOrBitmap(args);
        NetRequest netRequest = new NetRequest();
        NetRequestData data = null;

        if (method.isAnnotationPresent(POST.class)) {
            POST post = method.getAnnotation(POST.class);
            url = post.value();
            type = HttpRequest.RequstType.POST;
        }

        if (method.isAnnotationPresent(GET.class)) {
            GET post = method.getAnnotation(GET.class);
            url = post.value();
            type = HttpRequest.RequstType.GET;
        }
        switch (requestType) {
            case REQUEST_DEFAULT:
                data = defaultProcess(method, args, HOST + url, type);
                break;

            case REQUEST_FILE:
                data = fileProcess(method, args, HOST + url, type);
                break;
        }
        data.requestType = requestType;
        data.types = getTType(method);
        data.context = listener;
        data.shouldCache = shouldCache;
        netRequest.setData(data);

        /*netRequest.setNetEventListener(listener);
        netRequest.setTypes(getTType(method));
        netRequest.setRequestType(requestType);
        netRequest.getData(defaultProcess(method, args, url, type), HOST + url, type, shouldCache);*/
        return netRequest;
    }

    /**
     * 判断方法的参数是否有文件或者bitmap
     *
     * @param objects
     * @return
     */
    private int containFileOrBitmap(Object[] objects) {
        if (objects == null)
            return REQUEST_DEFAULT;
        for (Object obj : objects) {
            if (File.class.isInstance(obj) || Bitmap.class.isInstance(obj)) {
                return REQUEST_FILE;
            }
        }

        return REQUEST_DEFAULT;
    }

    /**
     * 文件相关请求的处理
     *
     * @param method
     * @param objs
     * @param url
     * @param type
     */

    private NetRequestData fileProcess(Method method, Object[] objs, String url, HttpRequest.RequstType type) {
        List<String> paramsName = getMethodParameterNamesByAnnotation(method);
        MultipartEntity multipartEntity = new MultipartEntity();
        Map<String, Object> params = new HashMap<>();
        Log.d(TAG, "-*******---");
        int i = 0;
        for (String name : paramsName) {
            if (objs == null || objs[i] == null) {
                params.put(name, "");
            } else if (File.class.isInstance(objs[i])) {
                params.put(name, "有文件:" + objs[i].toString());
                multipartEntity.addFilePart(name, (File) objs[i]);
            } else if (Bitmap.class.isInstance(objs[i])) {
                params.put(name, "有图片BitMap");
                Bitmap b = (Bitmap) objs[i];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, baos);
                multipartEntity.addBinaryPart(name, baos.toByteArray());
            } else {
                multipartEntity.addStringPart(name, objs[i].toString() + "");
                params.put(name, objs[i].toString() + "");
            }
            i++;
        }
        return new NetRequestData(url, type, 1, method.getName(), params, multipartEntity);
    }

    /**
     * 没有文件的请求处理
     *
     * @param method
     * @param objs
     * @param url
     * @param type
     */
    private NetRequestData defaultProcess(Method method, Object[] objs, String url, HttpRequest.RequstType type) {

        List<String> paramsName = getMethodParameterNamesByAnnotation(method);
        Map<String, Object> params = new HashMap<>();
        int i = 0;
        for (String name : paramsName) {
            if (objs == null || objs[i] == null) {
                params.put(name, "");
            } else {
                params.put(name, objs[i]);
            }
            Log.d(TAG, "-*******---");

            i++;
        }


        return new NetRequestData(url, type, 1, method.getName(), params);
    }

    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    public static List<String> getMethodParameterNamesByAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return new ArrayList<>();
        }
        List<String> parameteNames = new ArrayList<>();
        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof PARAMS) {
                    PARAMS param = (PARAMS) annotation;
                    parameteNames.add(param.value());
                }
            }
        }
        return parameteNames;
    }

    private Type[] getTType(Method method) {
        Type[] types = null;
        try {
            Type returnType = method.getGenericReturnType();// 返回类型
            types = ((ParameterizedType) returnType).getActualTypeArguments();//如果支持泛型，返回表示此类型实际类型参数的Type对象的数组
        } catch (GenericSignatureFormatError error) {
            error.printStackTrace();
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        } catch (MalformedParameterizedTypeException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
        }
        return types;
    }
}
