package com.weiwoju.kewuyou.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.ui.Display;
import com.weiwoju.kewuyou.util.ToastUtil;

public abstract class CoreFragment<UC> extends Fragment implements BaseController.Ui<UC> {

    private UC mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getController().attachUi(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().startUi(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getController().detachUi(this);
        AppContext.getContext().getRefWatcher().watch(this);
    }

    @Override
    public UC getCallbacks() {
        return mCallbacks;
    }

    @Override
    public void setCallbacks(UC callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public void onResponseError(ResponseError error) {
    }

    protected abstract BaseController getController();

    protected Display getDisplay() {
        return ((BaseActivity) getActivity()).getDisplay();
    }
}
