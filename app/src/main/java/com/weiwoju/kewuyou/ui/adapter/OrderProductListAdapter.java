package com.weiwoju.kewuyou.ui.adapter;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseListAdapter;
import com.weiwoju.kewuyou.model.bean.ShoppingCart;
import com.weiwoju.kewuyou.model.bean.ShoppingEntity;
import com.weiwoju.kewuyou.ui.adapter.holder.OrderProductItemViewHolder;
import com.weiwoju.kewuyou.ui.fragment.OrderFragment;
import com.weiwoju.kewuyou.widget.EditLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangguobing on 2017/4/27.
 */
public class OrderProductListAdapter extends BaseListAdapter<ShoppingEntity,OrderProductItemViewHolder> {

    private boolean isEdit;  //是否处于编辑状态
    private List<EditLayout> allItems = new ArrayList<>();

    private EditLayout mRightOpenItem;  //向右展开的删除项，只会存在一项

    private ProductOpListener mProductOpListener;

    @Override
    protected int getViewLayoutId() {
        return R.layout.layout_order_product_item;
    }

    @Override
    protected OrderProductItemViewHolder createViewHolder(View view) {
        return new OrderProductItemViewHolder(view);
    }

    @Override
    protected void showData(final OrderProductItemViewHolder holder,final int position) {
        ShoppingEntity shoppingEntity = getItem(position);
        holder.bind(shoppingEntity, position);

        final EditLayout editLayout = holder.mEditLayout;

        if (shoppingEntity.getId() != null && !TextUtils.isEmpty(shoppingEntity.getId())
                && !allItems.contains(editLayout)) {
            allItems.add(editLayout);
        }
        editLayout.setEdit(isEdit);

        holder.mPreDeleteLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (isEdit && mRightOpenItem != null) {
                            mRightOpenItem.openLeft();
                        }
                }
                return false;
            }
        });

        holder.mPreDeleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    editLayout.openRight();
                }
            }
        });

        holder.mDeleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductOpListener != null) {
                    mProductOpListener.onDelete(position);
                }
            }
        });

        editLayout.setOnDragStateChangeListener(new EditLayout.OnStateChangeListener() {

            @Override
            public void onLeftOpen(EditLayout layout) {
                if (mRightOpenItem == layout) {
                    mRightOpenItem = null;
                }
            }

            @Override
            public void onRightOpen(EditLayout layout) {
                if (mRightOpenItem != layout) {
                    mRightOpenItem = layout;
                }
            }

            @Override
            public void onClose(EditLayout layout) {
                if (mRightOpenItem == layout) {
                    mRightOpenItem = null;
                }
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        allItems.clear();
        super.notifyDataSetChanged();
    }

    public void removeProductItemAndClose(int position){
        setEdit(false);
        mRightOpenItem = null;
        allItems.remove(position);
        ShoppingCart.getInstance().removeShoppingList(getItem(position));
        closeAll();
    }

    /**
     * 设置编辑状态
     *
     * @param isEdit 是否为编辑状态
     */
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        for (EditLayout editLayout : allItems) {
            editLayout.setEdit(isEdit);
        }
    }


    /**
     * 关闭所有 item
     */
    public void closeAll() {
        for (EditLayout editLayout : allItems) {
            editLayout.close();
        }
    }

    /**
     * 将所有 item 向左展开
     */
    public void openLeftAll() {
        for (EditLayout editLayout : allItems) {
            editLayout.openLeft();
        }
    }

    /**
     * 获取编辑状态
     *
     * @return 是否为编辑状态
     */
    public boolean isEdit() {
        return isEdit;
    }

    /**
     * 获取向右展开的 item
     *
     * @return 向右展开的 item
     */
    public EditLayout getRightOpenItem() {
        return mRightOpenItem;
    }

    public interface ProductOpListener{
        void onDelete(int position);
    }

    public ProductOpListener getProductOpListener() {
        return mProductOpListener;
    }

    public void setProductOpListener(ProductOpListener productOpListener) {
        this.mProductOpListener = productOpListener;
    }
}
