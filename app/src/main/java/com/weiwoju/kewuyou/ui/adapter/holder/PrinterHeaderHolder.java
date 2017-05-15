package com.weiwoju.kewuyou.ui.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.weiwoju.kewuyou.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangguobing on 2017/5/3.
 */
public class PrinterHeaderHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_printer_header_title)
    public TextView titleTxt;

    public PrinterHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }


}
