package com.weiwoju.kewuyou.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseViewHolder;
import com.weiwoju.kewuyou.model.bean.Bill;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/5/2.
 */
public class BillItemViewHolder extends BaseViewHolder<Bill> {

    @Bind(R.id.tv_gua)
    TextView mGuaTxt;
    @Bind(R.id.tv_fan)
    TextView mFanTxt;
    @Bind(R.id.tv_bill_table_name)
    TextView mTableNameTxt;
    @Bind(R.id.tv_bill_create_time)
    TextView mTimeTxt;
    @Bind(R.id.tv_bill_price)
    TextView mPriceTxt;
    @Bind(R.id.tv_bill_no)
    TextView mNoTxt;

    public BillItemViewHolder(View view) {
        super(view);
    }

    public void bind(Bill bill){
        mTableNameTxt.setText(bill.getTable_name());
        mTimeTxt.setText(bill.getCreate_time());
        mPriceTxt.setText(bill.getPrice() + "元");
        mNoTxt.setText("No." + bill.getSerial_no());
    }
}
