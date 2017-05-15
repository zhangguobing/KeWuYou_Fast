package com.weiwoju.kewuyou.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BasePermissionActivity;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.MainController;
import com.weiwoju.kewuyou.controller.UserController;
import com.weiwoju.kewuyou.ui.adapter.MainFragmentNavigatorAdapter;
import com.weiwoju.kewuyou.ui.adapter.MainTabAdapter;
import com.weiwoju.kewuyou.ui.bean.MainTab;
import com.weiwoju.kewuyou.ui.Display;
import com.weiwoju.kewuyou.util.AppUtils;
import com.weiwoju.kewuyou.widget.navigator.FragmentNavigator;
import com.weiwoju.kewuyou.widget.verticaltablayout.VerticalTabLayout;
import com.weiwoju.kewuyou.widget.verticaltablayout.widget.TabView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/4/25.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BasePermissionActivity<MainController.MainUiCallbacks>
            implements MainController.MainUi{

    @Bind(R.id.tabLayout)
    VerticalTabLayout mTabLayout;

    private FragmentNavigator mNavigator;

    private int lastTabPosition;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController();
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);

        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new MainFragmentNavigatorAdapter(), R.id.container);
        mNavigator.showFragment(0);
        mNavigator.onCreate(savedInstanceState);

        mTabLayout.setTabAdapter(new MainTabAdapter(this));
        mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                tabSelected(tab,position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        requestPermission(new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }


    private void tabSelected(TabView tab, int position){
        if(position == MainTab.CASHIER.position
                || position == MainTab.BILL.position
                || position == MainTab.PRINTER.position){
            lastTabPosition = position;
        }
        if(position == MainTab.EXIT.position){
            showExitDialog();
        }else if(position == MainTab.ABOUT.position){
            showAboutDialog();
        }else if(position == MainTab.UPDATE.position){
            AppUtils.openAppInMarket(this,getPackageName());
        }else{
            setCurrentTab(position);
        }
    }

    private void setCurrentTab(int position) {
        mNavigator.showFragment(position);
    }


    @Override
    protected void handleIntent(Intent intent, Display display) {
        if (intent != null && intent.hasExtra(Display.PARAM_OBJ)) {
            MainTab tab = (MainTab) intent.getSerializableExtra(Display.PARAM_OBJ);
            setCurrentTab(tab.position);
        }
    }

    private void showAboutDialog(){
        final NormalDialog dialog = new NormalDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(true)
                .title("关于")
                .btnNum(1)
                .btnText("确定")
                .cornerRadius(5)
                .content(getString(R.string.dialog_about_content, AppUtils.getVersionName(this)))
                .contentTextColor(R.color.black)
                .dividerColor(R.color.divider)
                .btnTextSize(15.5f, 15.5f)
                .btnTextColor(R.color.blue)
                .btnPressColor(R.color.dark_blue)
                .widthScale(0.4f)
                .show();
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                mTabLayout.setTabSelected(lastTabPosition);
            }
        });
    }

    private void showExitDialog(){
        final NormalDialog dialog = new NormalDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)
                .cornerRadius(5)
                .content("是否确定退出程序?")
                .contentGravity(Gravity.CENTER)
                .contentTextColor(R.color.black)
                .dividerColor(R.color.divider)
                .btnTextSize(15.5f, 15.5f)
                .btnTextColor(R.color.blue, R.color.blue)
                .btnPressColor(R.color.dark_blue)
                .widthScale(0.4f)
//                .showAnim(new BounceTopEnter())
//                .dismissAnim(new SlideBottomExit())
                .show();
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                mTabLayout.setTabSelected(lastTabPosition);
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                getCallbacks().logout();
            }
        });
    }

    @Override
    protected void permissionFail() {
        super.permissionFail();
    }

    @Override
    protected void permissionSuccess() {
        super.permissionSuccess();
    }

    @Override
    public void logoutFinish() {
        Display display = getDisplay();
        if (display != null) {
            display.showLogin();
            display.finishActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
