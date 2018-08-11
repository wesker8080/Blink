package com.eliteai.blink.view.base;


import com.eliteai.zzkframe.mvvm.AbstractViewMode;
import com.eliteai.zzkframe.mvvm.IView;

/**
 * Created by zwb
 * Description
 * Date 16/8/18.
 */
public class BaseVM<T extends IView> extends AbstractViewMode<T> {
    @Override
    public void tokenVerifyFailed() {
        super.tokenVerifyFailed();

    }
}
