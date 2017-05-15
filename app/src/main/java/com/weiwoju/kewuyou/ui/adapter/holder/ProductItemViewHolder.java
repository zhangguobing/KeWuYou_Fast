package com.weiwoju.kewuyou.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseViewHolder;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.model.bean.ShoppingCart;
import com.weiwoju.kewuyou.ui.dialog.ProductSpecDetailDialog;
import com.weiwoju.kewuyou.ui.dialog.ProductSpecDialog;

import java.math.BigDecimal;

import butterknife.Bind;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;

/**
 * Created by zhangguobing on 2017/4/28.
 */
public class ProductItemViewHolder extends BaseViewHolder<ProductCategory.ProlistBean> {

    @Bind(R.id.badge_view)
    BGABadgeFrameLayout mBadgeView;
    @Bind(R.id.tv_product_name)
    TextView mProductNameTxt;
    @Bind(R.id.tv_product_price)
    TextView mProductPriceTxt;
    @Bind(R.id.tv_product_no)
    TextView mProductNoTxt;

    public ProductItemViewHolder(View view) {
        super(view);
    }

    public void bind(final ProductCategory.ProlistBean product){
        mProductNameTxt.setText(product.getName());
        mProductPriceTxt.setText(product.getPrice() + "");
        float quantity = ShoppingCart.getInstance().getQuantityForProduct(product);
        if(quantity > 0){
            mBadgeView.showTextBadge(new BigDecimal(quantity).setScale(1,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
        }else{
            mBadgeView.hiddenBadge();
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductCategory.ProlistBean.DEFAULT_UNIT_NAME.equals(product.getUnit_name())) {
                    if(product.getStyle_list().size() == 0){
                        ShoppingCart.getInstance().push(product);
                        float oldQuantity = ShoppingCart.getInstance().getQuantityForProduct(product);
                        mBadgeView.showTextBadge(new BigDecimal(oldQuantity)
                                .setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
                    }else{
                        showProductSepcDetailDialog(product, true);
                    }
                } else {
                    ProductSpecDialog dialog = new ProductSpecDialog(getContext(), new ProductSpecDialog.CallBack() {
                        @Override
                        public void onOkClick(float num) {
                            if (product.getStyle_list().size() == 0) {
                                ShoppingCart.getInstance().push(product, num);
                            } else {
                                showProductSepcDetailDialog(product,false);
                            }
                        }
                    });
                    dialog.setTitle(product.getName())
                            .setPrice(product.getPrice())
                            .setHint("输入计量数(" + product.getUnit_name() + ")")
                            .show();
                }
            }
        });
    }


    private void showProductSepcDetailDialog(final ProductCategory.ProlistBean product, boolean isShowCount){
        ProductSpecDetailDialog dialog = new ProductSpecDetailDialog(getContext()
                , product, new ProductSpecDetailDialog.CallBack() {
            @Override
            public void onOkClick(int style_id, float num) {
                product.setStyle_id(style_id);
                ShoppingCart.getInstance().push(product,num);
            }
        });
        dialog.setIsShowCount(isShowCount);
        dialog.show();
    }

}
