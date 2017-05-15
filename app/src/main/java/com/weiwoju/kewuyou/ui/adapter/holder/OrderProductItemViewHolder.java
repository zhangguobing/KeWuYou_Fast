package com.weiwoju.kewuyou.ui.adapter.holder;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseViewHolder;
import com.weiwoju.kewuyou.model.bean.ShoppingEntity;
import com.weiwoju.kewuyou.widget.EditLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/4/27.
 */
public class OrderProductItemViewHolder extends BaseViewHolder<ShoppingEntity>{

    @Bind(R.id.tv_product_no)
    TextView mProductNoTxt;
    @Bind(R.id.tv_product_name)
    TextView mProductNameTxt;
    @Bind(R.id.tv_product_num)
    TextView mProductNumTxt;
    @Bind(R.id.tv_product_price)
    TextView mProductPriceTxt;
    @Bind(R.id.edit_layout)
    public EditLayout mEditLayout;
    @Bind(R.id.fl_pre_delete)
    public FrameLayout mPreDeleteLayout;
    @Bind(R.id.fl_delete)
    public FrameLayout mDeleteLayout;

    private List<EditLayout> allItems = new ArrayList<>();

    public OrderProductItemViewHolder(View view) {
        super(view);
    }

    public void bind(ShoppingEntity shoppingEntity, int position){
        mProductNoTxt.setText((position + 1) + "");
        if(shoppingEntity.getId() == null || TextUtils.isEmpty(shoppingEntity.getId())){
            mProductNameTxt.setText("");
            mProductNameTxt.setCompoundDrawables(null, null, null, null);
            mProductNumTxt.setText("");
            mProductPriceTxt.setText("");
        }else {
            mProductNameTxt.setText(shoppingEntity.getName());
            Drawable leftDrawable = shoppingEntity.getStatus() == ShoppingEntity.Status.READY ?
                    getDrawable(R.mipmap.ic_unordered) : getDrawable(R.mipmap.ic_ordered);
            leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
            mProductNameTxt.setCompoundDrawables(leftDrawable, null, null, null);
            mProductNumTxt.setText(new BigDecimal(shoppingEntity.getQuantity())
                    .setScale(1,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString() + "份");
            mProductPriceTxt.setText(new BigDecimal(shoppingEntity.getTotalPrice())
                    .setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
        }
    }
}
