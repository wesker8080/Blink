package com.eliteai.zzkframe.http.net;

import com.eliteai.zzkframe.http.HttpRequest;



/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/16
 ***************************************/
public class NetRequest<T> {

    public enum RESPONSE_STATUS {
        PET_NAME_EXIST(-21,"宠物昵称已经存在"),
        ERROR_19(-19,"用户权限不够"),
        NOT_MAIN_DEVICE(-18,"设备非用户的主设备"),
        TOKEN_ERROR(-15,"错误的APP TOKEN"),
        AUTHOR_LACK(-14,"用户权限不够"),
        ID_INVALID(-12,"无效用户ID"),
        IMEI_INVALID(-9,"无效IMEI号，在系统内没有登记过"),
        UNCHECK_PARAMETER(-10,"无效参数"),
        BIND_TIMES(-8,"设备被重复绑定"),
        BIND_REFUSE(-6,"设备被拒绝绑定"),
        USER_NON_EXISTS(-5,"用户名不存在"),
        USR_EXISTS(-4,"用户名已经存在"),
        ERROR(-1,"后台出错,回传异常信息"),
        NOT_OK(0,"请求失败"),
        OK(1,"请求成功"),
        NOT_NET(100,"手机网络连接异常"),
        TIME_OUT(300,"网络超时"),
        PARSE_ERROR(200,"json解析出错"),
        DEV_OTHER_BIND(-22,"设备被其他人绑定"),
        MAX_ELE(-26,"电子围栏数已达到上限"),
        OUT_LINE(-27,"设备断线"),
        ICCID_BIND(-2,"ICCID已经被绑定了");


        RESPONSE_STATUS(int value,String msg) {
            this.value = value;
            this.msg = msg;
        }

        private int value;
        private String msg;

        public int getValue() {
            return value;
        }

        public String getMsg() {
            return msg;
        }
    }


    NetCallBack callBack = new NetCallBack(this);

    private OnNetEventListener netEventListener;

    private NetRequestData data;

    public NetRequestData getData() {
        return data;
    }

    public void setNetEventListener(OnNetEventListener netEventListener) {
        this.netEventListener = netEventListener;
    }

    public OnNetEventListener getNetEventListener() {
        return netEventListener;
    }

    public void setData(NetRequestData data){

        if(data != null){
            this.data = data;
            setNetEventListener(data.context);
            if (data.requestType == NetUtil.REQUEST_DEFAULT) {
                HttpRequest.request(data.type, callBack, data.params, data.url, this, data.shouldCache);
            } else if (data.requestType == NetUtil.REQUEST_FILE) {
                HttpRequest.requestPostCustom(callBack, data.multipartParams, data.url,this, data.shouldCache);
            }
        }
    }
}
