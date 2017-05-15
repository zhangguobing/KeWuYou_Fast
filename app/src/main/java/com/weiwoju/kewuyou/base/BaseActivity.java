package com.weiwoju.kewuyou.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;


import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.weiwoju.kewuyou.Constants;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.context.AppCookie;
import com.weiwoju.kewuyou.model.event.AccountChangedEvent;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.ui.Display;
import com.weiwoju.kewuyou.ui.activity.LoginActivity;
import com.weiwoju.kewuyou.util.AppUtils;
import com.weiwoju.kewuyou.util.EventUtil;
import com.weiwoju.kewuyou.util.HideKeyBoardUtil;
import com.weiwoju.kewuyou.util.ToastUtil;

import butterknife.ButterKnife;

/**
 * author: cheikh.wang on 17/1/5
 * email: wanghonghi@126.com
 */
public abstract class BaseActivity<UC> extends CoreActivity<UC> {


    private KProgressHUD mLoading;
    private NormalDialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
//        initialToolbar();
        handleIntent(getIntent(), getDisplay());
        initialViews(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent, getDisplay());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected int getLayoutId() {
        for (Class c = getClass(); c != Context.class; c = c.getSuperclass()) {
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                return annotation.value();
            }
        }
        return 0;
    }

    @Override
    public void onResponseError(final ResponseError error) {
        cancelLoading();
        if(Constants.CustomHttpCode.HTTP_SESSIONID_EXPIRE.equals(error.getCode())){
            showSessionExpireDialog(error.getMessage());
        }else{
            ToastUtil.show(error.getMessage());
        }
    }

    private void showSessionExpireDialog(String message){
        if(mDialog != null && mDialog.isShowing()) return;
        mDialog = new NormalDialog(this);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.isTitleShow(true)
                .title("提示")
                .btnNum(1)
                .btnText("确定")
                .cornerRadius(5)
                .content(message)
                .contentTextColor(R.color.black)
                .dividerColor(R.color.divider)
                .btnTextSize(15.5f, 15.5f)
                .btnTextColor(R.color.blue)
                .btnPressColor(R.color.dark_blue)
                .widthScale(0.4f)
                .show();
        mDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mDialog.dismiss();
                EventUtil.sendEvent(new AccountChangedEvent(null));
                Display display = getDisplay();
                if (display != null) {
                    display.showLogin();
                    display.finishActivity();
                }
            }
        });
    }

//    private void initialToolbar() {
//        if (mToolbar != null) {
//            mToolbar.setTitle(getTitle());
//            setSupportActionBar(mToolbar);
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }

    protected void handleIntent(Intent intent, Display display) {}

    protected void initialViews(Bundle savedInstanceState) {
        HideKeyBoardUtil.init(this);
    }

    @Override
    public void setTitle(CharSequence title) {
//        if (mToolbar != null) {
//            mToolbar.setTitle(title);
//        }
    }

    @Override
    public void setTitle(@StringRes int titleResId) {
//        if (mToolbar != null) {
//            mToolbar.setTitle(titleResId);
//        }
    }

    protected final void showLoading(@StringRes int textResId) {
        showLoading(getString(textResId));
    }

    protected final void showLoading(String text) {
        cancelLoading();
        mLoading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(text)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        mLoading.show();
    }

    protected final void cancelLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    public Dialog getDialog(){
        return mDialog;
    }
}
