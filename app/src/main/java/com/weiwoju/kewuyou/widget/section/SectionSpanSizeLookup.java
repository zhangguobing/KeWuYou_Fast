package com.weiwoju.kewuyou.widget.section;

import android.support.v7.widget.GridLayoutManager;

/**
 * A SpanSizeLookup to draw section headers or footer spanning the whole width of the RecyclerView
 * when using a GridLayoutManager
 */
public class SectionSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    protected SectionRecyclerViewAdapter<?, ?, ?> adapter = null;
    protected GridLayoutManager layoutManager = null;

    public SectionSpanSizeLookup(SectionRecyclerViewAdapter<?, ?, ?> adapter, GridLayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {

        if(adapter.isSectionHeaderPosition(position) || adapter.isSectionFooterPosition(position)){
            return layoutManager.getSpanCount();
        }else{
            return 1;
        }

    }
}