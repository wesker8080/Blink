package com.eliteai.blink.http;

import com.eliteai.blink.model.UserLocationModel;
import com.eliteai.zzkframe.http.help.PARAMS;
import com.eliteai.zzkframe.http.help.POST;
import com.eliteai.zzkframe.http.net.NetRequest;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 14:49
 */
public interface NetApi {
    //测试与服务器交互
    @POST("/v1.1/dev")
    NetRequest<UserLocationModel> getdataFromServer(
            @PARAMS("cmd") String cmd,
            @PARAMS("value") String value
    );
}
