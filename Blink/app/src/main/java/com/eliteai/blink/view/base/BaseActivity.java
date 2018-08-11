package com.eliteai.blink.view.base;


import android.app.ProgressDialog;
import android.os.Bundle;

import com.eliteai.blink.http.Net;
import com.eliteai.blink.http.NetApi;
import com.eliteai.zzkframe.event.NetEvent;
import com.eliteai.zzkframe.http.net.OnNetEventListener;
import com.eliteai.zzkframe.mvvm.AbstractBaseActivity;
import com.eliteai.zzkframe.mvvm.AbstractViewMode;
import com.eliteai.zzkframe.mvvm.IView;


/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/3
 ***************************************/
public abstract class BaseActivity<T extends IView, VM extends AbstractViewMode<T>> extends AbstractBaseActivity<T, VM> implements OnNetEventListener {

    @Override
    public boolean useButterknife() {
        return true;
    }

    public NetApi net() {
        return Net.get(this);
    }

    public NetApi net(boolean flag) {
        if (flag && progressDialog != null) {
            progressDialog.show();
        }
        return Net.get(this);
    }

    protected ProgressDialog progressDialog;

    @Override
    public void initView(Bundle savedInstanceState) {
        //沉浸式状态栏，在5.0上会有阴影效果，用下面这种方式去除重新设置
//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = getWindow();
//            //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            //设置状态栏颜色
//            window.setStatusBarColor(getResources().getColor(R.color.health_red));
//        }
        progressDialog = new ProgressDialog(this);
        //原始Hierarchy viewer 不能连接真机，需要使用gitHub开源项目辅助检测
//        ViewServer.get(this).addWindow(this);
    }

    @Override
    public void netSuccess(NetEvent netEvent) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean netFailed(NetEvent netEvent) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        return false;
    }

    @Override
    public void tokenVerifyFailed() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        reLogin();
    }

    /**
     * 重新登录
     */
    private void reLogin() {


    }

    /**
     * 退出登录
     */
    protected void logout() {

    }

    public void showDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    public void dissMissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
