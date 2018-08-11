package com.eliteai.zzkframe.http.net;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/5/20
 ***************************************/
public enum RequestType {

    GET(0),

    POST(1);

    RequestType(int i) {
        type = i;
    }

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
