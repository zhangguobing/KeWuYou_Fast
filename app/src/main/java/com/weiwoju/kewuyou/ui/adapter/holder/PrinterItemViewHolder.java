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
public class PrinterItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_printer_name)
    public TextView printerNameTxt;

    public PrinterItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
