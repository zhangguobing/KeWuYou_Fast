package com.weiwoju.kewuyou.ui.adapter;

import android.view.View;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseListAdapter;
import com.weiwoju.kewuyou.model.bean.Bill;
import com.weiwoju.kewuyou.ui.adapter.holder.BillItemViewHolder;

/**
 * Created by zhangguobing on 2017/5/2.
 */
public class BillListAdapter extends BaseListAdapter<Bill,BillItemViewHolder> {
    @Override
    protected int getViewLayoutId() {
        return R.layout.layout_bill_item;
    }

    @Override
    protected BillItemViewHolder createViewHolder(View view) {
        return new BillItemViewHolder(view);
    }

    @Override
    protected void showData(BillItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
