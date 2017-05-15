package com.weiwoju.kewuyou.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.ui.bean.MenuBean;
import com.weiwoju.kewuyou.widget.verticaltablayout.adapter.TabAdapter;
import com.weiwoju.kewuyou.widget.verticaltablayout.widget.TabView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import q.rorbin.badgeview.Badge;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class MainTabAdapter implements TabAdapter{

    List<MenuBean> mMenus;
    private Context mContext;

    public MainTabAdapter(Context context) {
        mMenus = new ArrayList<>();
        mContext = context;
        Collections.addAll(mMenus
                , new MenuBean(R.mipmap.tab_cashier, R.mipmap.tab_cashier, "收银")
                , new MenuBean(R.mipmap.tab_bill, R.mipmap.tab_bill, "账单查询")
                , new MenuBean(R.mipmap.tab_printer, R.mipmap.tab_printer, "打印机")
                , new MenuBean(R.mipmap.tab_update, R.mipmap.tab_update, "软件升级")
                , new MenuBean(R.mipmap.tab_about, R.mipmap.tab_about, "关于")
                , new MenuBean(R.mipmap.tab_exit, R.mipmap.tab_exit, "退出"));
    }

    @Override
    public int getCount() {
        return mMenus.size();
    }

    @Override
    public TabView.TabBadge getBadge(int position) {
//        return new TabView.TabBadge.Builder().setBadgeNumber(999).setBackgroundColor(0xff2faae5)
//                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                    @Override
//                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                    }
//                }).build();
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        MenuBean menu = mMenus.get(position);
        return new TabView.TabIcon.Builder()
                .setIcon(menu.mSelectIcon, menu.mNormalIcon)
                .setIconGravity(Gravity.TOP)
                .setIconMargin(dp2px(5))
                .setIconSize(dp2px(24), dp2px(24))
                .build();
    }

    @Override
    public TabView.TabTitle getTitle(int position) {
        MenuBean menu = mMenus.get(position);
        return new TabView.TabTitle.Builder()
                .setContent(menu.mTitle)
                .setTextSize(13)
                .setTextColor(0xFFFFFFFF, 0xFFFFFFFF)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return -1;
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
