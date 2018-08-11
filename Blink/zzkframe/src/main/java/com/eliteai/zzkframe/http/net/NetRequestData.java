/*
 *
 *   Copyright 2016 YunDi
 *
 *   The code is part of Yunnan, Shenzhen Branch of the internal architecture of YunDi source group
 *
 *   DO NOT DIVULGE
 *
 */

package com.eliteai.zzkframe.http.net;


import com.eliteai.zzkframe.http.HttpRequest;
import com.eliteai.zzkframe.http.MultipartEntity;

import java.lang.reflect.Type;
import java.util.Map;

/************************************************************
 * Author:  Zhouml
 * Description:     // 模块描述
 * Date: 2016/3/15
 ************************************************************/
public class NetRequestData {

    OnNetEventListener context;

    String url;
    HttpRequest.RequstType type;
    int requestType;
    Type[] types;

    String methodName;
    MultipartEntity multipartParams;
    Map<String, Object> params;
    boolean shouldCache;



    public NetRequestData(String url, HttpRequest.RequstType type, int requestType,
                          String methodName, Map<String, Object> params, MultipartEntity multipartParams) {
        this.url = url;
        this.type = type;
        this.requestType = requestType;
        this.methodName = methodName;
        this.params = params;
        this.multipartParams = multipartParams;
    }

    public NetRequestData(String url, HttpRequest.RequstType type, int requestType,
                          String methodName, Map<String, Object> params) {
        this.url = url;
        this.type = type;
        this.requestType = requestType;
        this.methodName = methodName;
        this.params = params;
    }
}
