package com.weiwoju.kewuyou.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.widget.ShoppingCountView;
import com.weiwoju.kewuyou.widget.flowlayout.FlowLayout;
import com.weiwoju.kewuyou.widget.flowlayout.TagAdapter;
import com.weiwoju.kewuyou.widget.flowlayout.TagFlowLayout;

/**
 * Created by zhangguobing on 2017/5/9.
 */
public class ProductSpecDetailDialog extends BaseDialog<ProductSpecDetailDialog> {

    private TagFlowLayout mTagFlowLayout;
    private ShoppingCountView mShoppingCountView;
    private Button mConfirmBtn;

    private boolean mIsShowCount = true;

    private ProductCategory.ProlistBean mProduct;

    private TagAdapter<ProductCategory.StyleBean> mTagAdapter;

    private ProductCategory.StyleBean mSelectedStyle;

    public ProductSpecDetailDialog.CallBack mCallBack;

    public ProductSpecDetailDialog(Context context,ProductCategory.ProlistBean product,ProductSpecDetailDialog.CallBack callBack) {
        super(context);
        mProduct = product;
        mCallBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        widthScale(0.35f);
        heightScale(0.5f);
    }

    @Override
    public View onCreateView() {
        return getLayoutInflater().inflate(R.layout.dialog_product_spec_detail,null);
    }

    @Override
    public void setUiBeforShow() {
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.flowLayout);
        mShoppingCountView = (ShoppingCountView) findViewById(R.id.shopping_count_view);
        mConfirmBtn = (Button) findViewById(R.id.btn_confirm);
        LinearLayout mEditNumLayout = (LinearLayout) findViewById(R.id.edit_num_layout);

        mTagFlowLayout.setAdapter(mTagAdapter = new TagAdapter<ProductCategory.StyleBean>(mProduct.getStyle_list()) {
            @Override
            public View getView(FlowLayout parent, int position, ProductCategory.StyleBean style) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_flow_product_style,
                        mTagFlowLayout, false);
                textView.setText(style.getName());
                return textView;
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mSelectedStyle = mTagAdapter.getItem(position);
                return true;
            }
        });
        mTagAdapter.setSelectedList(0);
        mSelectedStyle = mTagAdapter.getItem(0);

        mShoppingCountView.setShoppingCount(1);
        mShoppingCountView.setOnShoppingClickListener(new ShoppingCountView.ShoppingClickListener() {
            @Override
            public void onAddClick(float num) {
            }

            @Override
            public void onMinusClick(float num) {
                mConfirmBtn.setEnabled(num != 0);
            }
        });
        mEditNumLayout.setVisibility(mIsShowCount ? View.VISIBLE : View.GONE);

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(mCallBack != null){
                     dismiss();
                     mCallBack.onOkClick(Integer.valueOf(mSelectedStyle.getId()), mShoppingCountView.getShoppingCount());
                 }
            }
        });
    }

    public boolean isShowCount() {
        return mIsShowCount;
    }

    public void setIsShowCount(boolean isShowCount) {
        this.mIsShowCount = isShowCount;
    }

    public interface CallBack{
        void onOkClick(int style_id, float num);
    }
}
