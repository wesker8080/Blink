package com.eliteai.blink.viewmodel;

import android.os.SystemClock;
import android.util.Log;

import com.eliteai.blink.http.Net;
import com.eliteai.blink.model.UserLocationModel;
import com.eliteai.blink.view.activity.MainActivity;
import com.eliteai.blink.view.base.BaseVM;
import com.eliteai.zzkframe.event.NetEvent;
import com.eliteai.zzkframe.http.net.NetRequest;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 15:11
 */
public class MainVM extends BaseVM<MainActivity>{

    private NetRequest mainNR = null;

    public void getDataFromServer(String devType, String value) {
        mainNR = Net.get(this).getdataFromServer(devType,value);
    }

    @Override
    public void netSuccess(NetEvent netEvent) {
        super.netSuccess(netEvent);
        if (getView() == null) {
            return;
        }
        SystemClock.sleep(2000);
        getView().dissMissDialog();
        if (netEvent.whatEqual(mainNR)) {
            UserLocationModel mResult = (UserLocationModel) netEvent.getResult();
            Log.e("wesker","result : " + mResult);
            getView().showRequestResult(mResult.toString());
        }
    }

    @Override
    public boolean netFailed(NetEvent netEvent) {
        if (getView() != null) {
        }

        return super.netFailed(netEvent);
    }
}
