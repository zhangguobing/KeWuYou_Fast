package com.weiwoju.kewuyou.ui.adapter;

import android.view.View;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseAdapter;
import com.weiwoju.kewuyou.base.BaseListAdapter;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.ui.adapter.holder.ProductCategoryViewHolder;

/**
 * Created by zhangguobing on 2017/4/28.
 */
public class ProductCategoryAdapter extends BaseListAdapter<ProductCategory.CatelistBean,ProductCategoryViewHolder> {

    @Override
    protected int getViewLayoutId() {
        return R.layout.layout_product_category_item;
    }

    @Override
    protected ProductCategoryViewHolder createViewHolder(View view) {
        return new ProductCategoryViewHolder(view);
    }

    @Override
    protected void showData(ProductCategoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
