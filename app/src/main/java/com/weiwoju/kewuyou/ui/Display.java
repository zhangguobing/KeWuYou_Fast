package com.weiwoju.kewuyou.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.google.common.base.Preconditions;
import com.weiwoju.kewuyou.ui.activity.LoginActivity;
import com.weiwoju.kewuyou.ui.activity.MainActivity;
import com.weiwoju.kewuyou.ui.bean.MainTab;

/**
 * Created by zhangguobing on 2017/4/21.
 */
public class Display {

    public static final String PARAM_ID = "_id";
    public static final String PARAM_OBJ = "_obj";

    private final AppCompatActivity mActivity;

    public Display(AppCompatActivity activity) {
        mActivity = Preconditions.checkNotNull(activity, "activity cannot be null");
    }

    /**
     * 显示向上导航的按钮
     * @param isShow
     */
//    public void showUpNavigation(boolean isShow) {
//        final ActionBar ab = mActivity.getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(isShow);
//            ab.setHomeButtonEnabled(isShow);
//            if (isShow) {
//                ab.setHomeAsUpIndicator(R.drawable.ic_back);
//            }
//        }
//    }

    /**
     * 判断当前activity是否已经嵌套了fragment
     * @return
     */
    public boolean hasMainFragment() {
        return getBackStackFragmentCount() > 0;
    }

    /**
     * 获取回退栈里fragment的数量
     * @return
     */
    public int getBackStackFragmentCount() {
        return mActivity.getSupportFragmentManager().getBackStackEntryCount();
    }

    /**
     * 弹出回退栈里最顶上的fragment
     * 如果栈里只有一个fragment的话,则在弹出的同时并结束掉当前的activity
     * @return
     */
    public boolean popTopFragmentBackStack() {
        final FragmentManager fm = mActivity.getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount();
        if (backStackCount > 1) {
            fm.popBackStack();
            return true;
        }

        return false;
    }

    /**
     * 弹出回退栈里的所有fragment
     * @return
     */
    public boolean popEntireFragmentBackStack() {
        final FragmentManager fm = mActivity.getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fm.popBackStack();
        }
        return backStackCount > 0;
    }

    /**
     * 回退
     * @return
     */
    public void navigateUp() {
        final Intent intent = NavUtils.getParentActivityIntent(mActivity);
        if (intent != null) {
            NavUtils.navigateUpTo(mActivity, intent);
        } else {
            finishActivity();
        }
    }

    public void finishActivity() {
        mActivity.finish();
    }

    public void showMain(MainTab mainTab){
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.putExtra(PARAM_OBJ, mainTab);
        mActivity.startActivity(intent);
    }

    public void showLogin(){
        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }
}
