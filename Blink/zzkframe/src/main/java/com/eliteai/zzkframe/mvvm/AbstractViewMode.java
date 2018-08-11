package com.eliteai.zzkframe.mvvm;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.eliteai.zzkframe.event.NetEvent;
import com.eliteai.zzkframe.http.net.OnNetEventListener;

import java.lang.ref.WeakReference;

/***************************************
 * Author MR.ZHANG
 * Description .
 * Date:2016/6/3
 ***************************************/
public abstract class AbstractViewMode<T extends IView> implements OnNetEventListener{
    private WeakReference<T> mView;

    public void onBindView(@NonNull T view) {
        mView = new WeakReference<T>(view);
    }

    public T getView() {
//        if (mView.get() == null) {
//            throw new NullPointerException("the view must be created first!");
//        }
        return mView.get();
    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    @Override
    public void netSuccess(NetEvent netEvent) {

    }

    @Override
    public boolean netFailed(NetEvent netEvent) {
        return false;
    }

    @Override
    public void tokenVerifyFailed() {

    }
}
