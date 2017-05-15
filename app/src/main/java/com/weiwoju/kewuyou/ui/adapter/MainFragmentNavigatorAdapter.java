package com.weiwoju.kewuyou.ui.adapter;

import android.support.v4.app.Fragment;

import com.weiwoju.kewuyou.ui.bean.MainTab;
import com.weiwoju.kewuyou.ui.fragment.BillFragment;
import com.weiwoju.kewuyou.ui.fragment.CashierFragment;
import com.weiwoju.kewuyou.ui.fragment.PrinterFragment;
import com.weiwoju.kewuyou.widget.navigator.FragmentNavigatorAdapter;

/**
 * Created by zhangguobing on 2016/7/14.
 */
public class MainFragmentNavigatorAdapter implements FragmentNavigatorAdapter {

    private static final String TABS[] = {"cashier", "bill", "printer", "update", "about", "exit"};

    @Override
    public Fragment onCreateFragment(int position) {
        Fragment fragment = null;
        if(position == MainTab.CASHIER.position){
            fragment =  CashierFragment.newInstance();
        }else if(position == MainTab.BILL.position){
            fragment = BillFragment.newInstance();
        }else if(position == MainTab.PRINTER.position){
            fragment = PrinterFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public String getTag(int position) {
        return TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
