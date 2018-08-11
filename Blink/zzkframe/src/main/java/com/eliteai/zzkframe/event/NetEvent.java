package com.eliteai.zzkframe.event;

import com.eliteai.zzkframe.http.net.NetRequest;

/***************************************
 * Author MR.ZHANG
 * Description .网络请求回调的参数
 * Date:2016/6/16
 ***************************************/
public class NetEvent {
    private Object what;
    private int code;
    private NetRequest.RESPONSE_STATUS status;
    private Object result;
    public String repMsg;
    public String rep;

    public String getRepMsg() {
        return repMsg;
    }

    public void setRepMsg(String repMsg) {
        this.repMsg = repMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public NetRequest.RESPONSE_STATUS getStatus() {
        return status;
    }

    public void setStatus(NetRequest.RESPONSE_STATUS status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public NetEvent what(Object what) {
        this.what = what;
        return this;
    }

    public boolean whatEqual(NetRequest request) {
        if (request == null) {
            return false;
        }
        return what.equals(request);
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return "NetEvent{" +
                "what=" + what +
                ", code=" + code +
                ", status=" + status +
                ", result=" + result +
                ", repMsg=" + repMsg +
                ", rep=" + rep +
                '}';
    }
}
