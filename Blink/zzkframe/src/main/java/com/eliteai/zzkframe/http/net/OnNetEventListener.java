package com.eliteai.zzkframe.http.net;

import com.eliteai.zzkframe.event.NetEvent;

/***************************************
 * Author MR.ZHANG
 * Description .activity或者fragment要实现的网络请求回调
 * Date:2016/6/16
 ***************************************/
public interface OnNetEventListener {
    /**
     * 成功回调
     *
     * @param netEvent
     */
    void netSuccess(NetEvent netEvent);

    /**
     * 失败回调
     *
     * @param netEvent
     * @return false 由底层去处理，true自己处理事件
     */
    boolean netFailed(NetEvent netEvent);


    /**
     * 登录过期
     */
    void tokenVerifyFailed();
}
