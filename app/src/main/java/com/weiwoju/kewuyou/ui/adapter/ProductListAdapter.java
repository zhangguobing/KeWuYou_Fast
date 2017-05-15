package com.weiwoju.kewuyou.ui.adapter;

import android.view.View;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseListAdapter;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.ui.adapter.holder.ProductItemViewHolder;

/**
 * Created by zhangguobing on 2017/4/28.
 */
public class ProductListAdapter extends BaseListAdapter<ProductCategory.ProlistBean,ProductItemViewHolder> {
    @Override
    protected int getViewLayoutId() {
        return R.layout.layout_product_item;
    }

    @Override
    protected ProductItemViewHolder createViewHolder(View view) {
        return new ProductItemViewHolder(view);
    }

    @Override
    protected void showData(ProductItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
