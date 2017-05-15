package com.weiwoju.kewuyou.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseViewHolder;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.model.bean.ShoppingCart;

import java.math.BigDecimal;

import butterknife.Bind;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by zhangguobing on 2017/4/28.
 */
public class ProductCategoryViewHolder extends BaseViewHolder<ProductCategory.CatelistBean> {

    @Bind(R.id.tv_category_name)
    TextView mCategoryNameTxt;
    @Bind(R.id.badge_view)
    BGABadgeFrameLayout mBadgeView;

    public ProductCategoryViewHolder(View view) {
        super(view);
    }

    public void bind(ProductCategory.CatelistBean category){
        mCategoryNameTxt.setText(category.getName());
        float count = ShoppingCart.getInstance().getQuantityForCategory(category);
        if (count > 0) {
            mBadgeView.showTextBadge(new BigDecimal(count).setScale(1,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
        } else {
            mBadgeView.hiddenBadge();
        }
    }
}
