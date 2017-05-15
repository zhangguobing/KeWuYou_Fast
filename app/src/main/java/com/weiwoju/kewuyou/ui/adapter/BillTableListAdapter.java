package com.weiwoju.kewuyou.ui.adapter;

import android.view.View;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseListAdapter;
import com.weiwoju.kewuyou.model.bean.BillTable;
import com.weiwoju.kewuyou.ui.adapter.holder.BillTableViewHolder;

/**
 * Created by zhangguobing on 2017/5/2.
 */
public class BillTableListAdapter extends BaseListAdapter<BillTable,BillTableViewHolder> {
    @Override
    protected int getViewLayoutId() {
        return R.layout.layout_bill_table_item;
    }

    @Override
    protected BillTableViewHolder createViewHolder(View view) {
        return new BillTableViewHolder(view);
    }

    @Override
    protected void showData(BillTableViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
