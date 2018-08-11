package com.eliteai.blink.view.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.eliteai.blink.http.Net;
import com.eliteai.blink.http.NetApi;
import com.eliteai.zzkframe.event.NetEvent;
import com.eliteai.zzkframe.http.net.OnNetEventListener;
import com.eliteai.zzkframe.mvvm.AbstractBaseFragment;
import com.eliteai.zzkframe.mvvm.AbstractViewMode;
import com.eliteai.zzkframe.mvvm.IView;

import butterknife.ButterKnife;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/13
 ***************************************/
public abstract class BaseFragment<T extends IView, VM extends AbstractViewMode<T>> extends AbstractBaseFragment<T, VM> implements OnNetEventListener {
    private Context context;
    private Activity activity;
    public NetApi net(boolean flag){
        if(flag && progressDialog != null){
            progressDialog.show();
        }
        return Net.get(this);
    }

    protected ProgressDialog progressDialog;

    @Override
    public boolean useButterknife() {
        return true;
    }

    @Override
    protected void injectView(View mRootView) {
        ButterKnife.bind(this, mRootView);
        context = getActivity();
        activity = getActivity();
        progressDialog = new ProgressDialog(activity);
    }
    public NetApi net(){
        return Net.get(this);
    }

    @Override
    public void netSuccess(NetEvent netEvent) {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean netFailed(NetEvent netEvent) {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        return false;
    }



    @Override
    public void tokenVerifyFailed() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        //MyApplication.dataCache.setToken(null);
        reLogin();
    }
    /**
     * 重新登录
     */
    private void reLogin() {
        //readyGo(DialogQuitActivity.class);
    }

    public void showDialog(){
        if(progressDialog != null) {
            progressDialog.show();
        }
    }
    public void dissMissDialog(){
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
