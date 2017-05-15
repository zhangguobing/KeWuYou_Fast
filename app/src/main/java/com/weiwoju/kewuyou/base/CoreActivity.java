package com.weiwoju.kewuyou.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.MainController;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.ui.Display;
import com.weiwoju.kewuyou.util.ActivityStack;
import com.weiwoju.kewuyou.util.ToastUtil;

public abstract class CoreActivity<UC> extends AppCompatActivity
        implements BaseController.Ui<UC> {

    private MainController mMainController;
    private Display mDisplay;
    private UC mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mDisplay = new Display(this);
        mMainController = AppContext.getContext().getMainController();

        getController().attachUi(this);
        ActivityStack.create().add(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainController.init();
        mMainController.attachDisplay(mDisplay);
        getController().startUi(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainController.suspend();
        mMainController.detachDisplay(mDisplay);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getController().detachUi(this);
        ActivityStack.create().remove(this);
        AppContext.getContext().getRefWatcher().watch(this);
    }

    protected abstract int getLayoutId();
    protected abstract BaseController getController();

    @Override
    public void setCallbacks(UC callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public UC getCallbacks() {
        return mCallbacks;
    }

    @Override
    public void onResponseError(ResponseError error) {
    }

    public final Display getDisplay() {
        return mDisplay;
    }

    protected final MainController getMainController() {
        return mMainController;
    }

    @Override
    public void onBackPressed() {
        getMainController().onBackButtonPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getMainController().onBackButtonPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
