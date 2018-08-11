package com.eliteai.zzkframe.http;

/***************************************
 * Author MR.ZHANG
 * Description .返回的数据格式，由后台统一
 * Date:2016/6/16
 ***************************************/
public class NetInfo {
    private int code = -100;//状态码
    private String msg;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NetInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
