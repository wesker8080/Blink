package com.eliteai.zzkframe.http.net;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.eliteai.zzkframe.ApplicationManager;
import com.eliteai.zzkframe.R;
import com.eliteai.zzkframe.event.NetEvent;
import com.eliteai.zzkframe.http.HttpCallBack;
import com.eliteai.zzkframe.http.NetInfo;
import com.eliteai.zzkframe.utils.LogUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/16
 ***************************************/
public class NetCallBack implements HttpCallBack {
    private NetRequest netRequest;

    public NetCallBack(NetRequest netRequest) {
        this.netRequest = netRequest;
    }

    @Override //统一处理请求结果
    public void response(NetInfo netInfo) {
        if (netInfo == null) {
            return;
        }
        LogUtils.writeLog(netInfo.toString(), true);
        Log.v("response", netInfo.toString());
        Observable.just(netInfo).
                subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                map(new Func1<NetInfo, NetEvent>() {
                    @Override
                    public NetEvent call(NetInfo netInfo) {
                        int code = netInfo.getCode();
                        NetRequest.RESPONSE_STATUS status;
                        Object result = null;
                        String msg;
                        if (code == NetRequest.RESPONSE_STATUS.OK.getValue()) {//请求成功
                            status = NetRequest.RESPONSE_STATUS.OK;
                            String dataString = netInfo.getData();
                            if (!TextUtils.isEmpty(dataString) && netRequest.getData().types != null) {
                                result = JSON.parseObject(dataString, netRequest.getData().types[0]);
                            }
//                            msg = NetRequest.RESPONSE_STATUS.OK.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.OK);

                        } else if (code == NetRequest.RESPONSE_STATUS.PARSE_ERROR.getValue()) {//json解析出错
                            status = NetRequest.RESPONSE_STATUS.PARSE_ERROR;
//                            msg = NetRequest.RESPONSE_STATUS.PARSE_ERROR.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.PARSE_ERROR);

                        } else if (code == NetRequest.RESPONSE_STATUS.NOT_NET.getValue()) {//手机网络连接异常
                            status = NetRequest.RESPONSE_STATUS.NOT_NET;
//                            msg = NetRequest.RESPONSE_STATUS.NOT_NET.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.NOT_NET);

                        } else if (code == NetRequest.RESPONSE_STATUS.TIME_OUT.getValue()) {//手机网络连接异常
                            status = NetRequest.RESPONSE_STATUS.TIME_OUT;
//                            msg = NetRequest.RESPONSE_STATUS.NOT_NET.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.TIME_OUT);

                        } else if (code == NetRequest.RESPONSE_STATUS.NOT_OK.getValue()) {//请求失败
                            status = NetRequest.RESPONSE_STATUS.NOT_OK;
//                            msg = NetRequest.RESPONSE_STATUS.NOT_OK.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.NOT_OK);

                        } else if (code == NetRequest.RESPONSE_STATUS.USR_EXISTS.getValue()) {//用户名已经存在
                            status = NetRequest.RESPONSE_STATUS.USR_EXISTS;
//                            msg = NetRequest.RESPONSE_STATUS.USR_EXISTS.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.USR_EXISTS);

                        } else if (code == NetRequest.RESPONSE_STATUS.USER_NON_EXISTS.getValue()) {//用户名不存
                            status = NetRequest.RESPONSE_STATUS.USER_NON_EXISTS;
//                            msg = NetRequest.RESPONSE_STATUS.USER_NON_EXISTS.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.USER_NON_EXISTS);

                        } else if (code == NetRequest.RESPONSE_STATUS.TOKEN_ERROR.getValue()) {//错误的APP TOKEN
                            status = NetRequest.RESPONSE_STATUS.TOKEN_ERROR;
//                            msg = NetRequest.RESPONSE_STATUS.TOKEN_ERROR.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.TOKEN_ERROR);

                        } else if (code == NetRequest.RESPONSE_STATUS.AUTHOR_LACK.getValue() || code == NetRequest.RESPONSE_STATUS.ERROR_19.getValue()) {//用户权限不够
                            status = NetRequest.RESPONSE_STATUS.AUTHOR_LACK;
//                            msg = NetRequest.RESPONSE_STATUS.AUTHOR_LACK.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.AUTHOR_LACK);

                        } else if (code == NetRequest.RESPONSE_STATUS.ID_INVALID.getValue()) {//无效用户ID
                            status = NetRequest.RESPONSE_STATUS.ID_INVALID;
//                            msg = NetRequest.RESPONSE_STATUS.ID_INVALID.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.ID_INVALID);

                        } else if (code == NetRequest.RESPONSE_STATUS.IMEI_INVALID.getValue()) {//无效IMEI号，在系统内没有登记过
                            status = NetRequest.RESPONSE_STATUS.IMEI_INVALID;
//                            msg = NetRequest.RESPONSE_STATUS.IMEI_INVALID.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.IMEI_INVALID);

                        } else if (code == NetRequest.RESPONSE_STATUS.UNCHECK_PARAMETER.getValue()) {//无效参数
                            status = NetRequest.RESPONSE_STATUS.UNCHECK_PARAMETER;
//                            msg = NetRequest.RESPONSE_STATUS.IMEI_INVALID.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.UNCHECK_PARAMETER);

                        } else if (code == NetRequest.RESPONSE_STATUS.BIND_TIMES.getValue()) {//设备被重复绑定
                            status = NetRequest.RESPONSE_STATUS.BIND_TIMES;
//                            msg = NetRequest.RESPONSE_STATUS.BIND_TIMES.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.BIND_TIMES);

                        } else if (code == NetRequest.RESPONSE_STATUS.DEV_OTHER_BIND.getValue()) {//设备被重复绑定
                            status = NetRequest.RESPONSE_STATUS.DEV_OTHER_BIND;
                            msg = NetRequest.RESPONSE_STATUS.DEV_OTHER_BIND.getMsg();
//                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.BIND_TIMES);

                        } else if (code == NetRequest.RESPONSE_STATUS.BIND_REFUSE.getValue()) {//主人拒绝本次用户申请添加设备
                            status = NetRequest.RESPONSE_STATUS.BIND_REFUSE;
//                            msg = NetRequest.RESPONSE_STATUS.BIND_REFUSE.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.BIND_REFUSE);

                        } else if (code == NetRequest.RESPONSE_STATUS.NOT_MAIN_DEVICE.getValue()) {//设备非用户的主设备
                            status = NetRequest.RESPONSE_STATUS.NOT_MAIN_DEVICE;
//                            msg = NetRequest.RESPONSE_STATUS.NOT_MAIN_DEVICE.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.NOT_MAIN_DEVICE);

                        } else if (code == NetRequest.RESPONSE_STATUS.PET_NAME_EXIST.getValue()) {
                            status = NetRequest.RESPONSE_STATUS.PET_NAME_EXIST;
//                            msg = NetRequest.RESPONSE_STATUS.PET_NAME_EXIST.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.PET_NAME_EXIST);

                        } else if (code == NetRequest.RESPONSE_STATUS.MAX_ELE.getValue()) {
                            status = NetRequest.RESPONSE_STATUS.MAX_ELE;
//                            msg = NetRequest.RESPONSE_STATUS.MAX_ELE.getMsg();
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.MAX_ELE);

                        } else if (code == NetRequest.RESPONSE_STATUS.ICCID_BIND.getValue()) {
                            status = NetRequest.RESPONSE_STATUS.ICCID_BIND;
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.NOT_OK);
                        }
//                        else if (code == NetRequest.RESPONSE_STATUS.OUT_LINE.getValue()) {
//                            status = NetRequest.RESPONSE_STATUS.OUT_LINE;
//                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.gps_dev_ouline);
//                        }
                        else {//后台出错,回传异常信息
                            status = NetRequest.RESPONSE_STATUS.ERROR;
//                            msg = NetRequest.RESPONSE_STATUS.ERROR.getMsg();
//                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.ERROR);
                            msg = ApplicationManager.getApplication().getApplicationContext().getString(R.string.NOT_OK);
                        }
                        NetEvent event = new NetEvent();
                        event.setRep(netInfo.getData());
                        event.setCode(code);
                        event.setResult(result);
                        event.what(netRequest);
                        event.setStatus(status);
//                        event.setRepMsg(netInfo.getMsg());
                        event.setRepMsg(msg);
                        if (event.getCode() == -22) {
                            event.setRepMsg(netInfo.getData());
                        }
                        return event;
                    }
                }).
                subscribe(new Action1<NetEvent>() {
                    @Override
                    public void call(NetEvent netEvent) {
                        if (netEvent.getStatus() == NetRequest.RESPONSE_STATUS.OK) {
                            if (netRequest.getNetEventListener() != null) {
                                netRequest.getNetEventListener().netSuccess(netEvent);
                            }
                        } else if (netEvent.getStatus() == NetRequest.RESPONSE_STATUS.TOKEN_ERROR) {
                            if (netRequest.getNetEventListener() != null) {
                                netRequest.getNetEventListener().tokenVerifyFailed();
                            }
                        } else if (!netRequest.getNetEventListener().netFailed(netEvent)) {
                            if (!TextUtils.isEmpty(netEvent.getRepMsg())) {
//                                    DrawerToast.getInstance(ApplicationManager.getApplication().getApplicationContext()).show(netEvent.getRepMsg());
                                Toast.makeText(ApplicationManager.getApplication().getApplicationContext(), netEvent.getRepMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        //设备断线--推送到MainActivity去处理
//                        if (netEvent.getStatus() == NetRequest.RESPONSE_STATUS.OUT_LINE) {
//                            EventBus.getDefault().post(new OutLineEvent());
//                        }
                    }
                });
    }
}
