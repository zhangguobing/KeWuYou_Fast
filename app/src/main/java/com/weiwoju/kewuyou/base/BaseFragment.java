package com.weiwoju.kewuyou.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.util.HideKeyBoardUtil;
import com.weiwoju.kewuyou.util.ToastUtil;

import butterknife.ButterKnife;

public abstract class BaseFragment<UC> extends CoreFragment<UC> {

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        handleArguments(getArguments());
        initialViews(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected int getLayoutResId() {
        for (Class c = getClass(); c != Fragment.class; c = c.getSuperclass()) {
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                return annotation.value();
            }
        }
        return 0;
    }

    protected void handleArguments(Bundle arguments) {}

    protected void initialViews(Bundle savedInstanceState) {
        HideKeyBoardUtil.init(getHostActivity());
    }

    protected void setSupportActionBar(Toolbar toolbar) {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    protected ActionBar getSupportActionBar() {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

    protected boolean isShowBack() {
        return true;
    }

    protected final void showLoading(@StringRes int textResId) {
        showLoading(getString(textResId));
    }

    protected final void showLoading(String text) {
        Activity hostActivity = getHostActivity();
        if (hostActivity instanceof BaseActivity) {
            ((BaseActivity) hostActivity).showLoading(text);
        }
    }

    protected final void cancelLoading() {
        Activity hostActivity = getHostActivity();
        if (hostActivity instanceof BaseActivity) {
            ((BaseActivity) hostActivity).cancelLoading();
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        Activity hostActivity = getHostActivity();
        if(hostActivity instanceof BaseActivity){
            ((BaseActivity)hostActivity).onResponseError(error);
        }
    }

    public Activity getHostActivity(){
        return getActivity();
    }
}
