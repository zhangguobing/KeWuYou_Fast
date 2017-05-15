package com.weiwoju.kewuyou.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.model.bean.PrinterSection;
import com.weiwoju.kewuyou.ui.adapter.holder.PrinterHeaderHolder;
import com.weiwoju.kewuyou.ui.adapter.holder.PrinterItemViewHolder;
import com.weiwoju.kewuyou.widget.section.SectionRecyclerViewAdapter;

import java.util.List;

/**
 * Created by zhangguobing on 2017/5/3.
 */
public class PrinterAdapter extends SectionRecyclerViewAdapter<PrinterHeaderHolder,PrinterItemViewHolder,RecyclerView.ViewHolder> {

    private List<PrinterSection> mPrinterSectionList;
    private Context mContext;
    private LayoutInflater mInflater;

    public PrinterAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<PrinterSection> printerSections){
        mPrinterSectionList = printerSections;
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return mPrinterSectionList == null ? 0 : mPrinterSectionList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        List<PrinterSection.PrinterInfo> printerInfoList = mPrinterSectionList.get(section).printerInfoList;
        return  printerInfoList== null ? 0 : printerInfoList.size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected PrinterHeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new PrinterHeaderHolder(mInflater.inflate(R.layout.layout_printer_header,parent,false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected PrinterItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new PrinterItemViewHolder(mInflater.inflate(R.layout.layout_printer_item,parent,false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(PrinterHeaderHolder holder, int section) {
        holder.titleTxt.setText(mPrinterSectionList.get(section).title);
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(PrinterItemViewHolder holder, int section, int position) {
        holder.printerNameTxt.setText(mPrinterSectionList.get(section).printerInfoList.get(position).name);
    }
}
