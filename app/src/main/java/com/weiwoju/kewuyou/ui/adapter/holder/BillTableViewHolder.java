package com.weiwoju.kewuyou.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseViewHolder;
import com.weiwoju.kewuyou.model.bean.BillTable;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/5/2.
 */
public class BillTableViewHolder extends BaseViewHolder<BillTable> {

    @Bind(R.id.tv_bill_table_time)
    TextView mTimeTxt;
    @Bind(R.id.tv_bill_table_price)
    TextView mPriceTxt;

    public BillTableViewHolder(View view) {
        super(view);
    }

    public void bind(BillTable billTable){
        mTimeTxt.setText(billTable.getTime());
        mPriceTxt.setText(billTable.getPrice());
    }
}
