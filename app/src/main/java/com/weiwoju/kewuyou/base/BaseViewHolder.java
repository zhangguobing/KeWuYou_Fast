package com.weiwoju.kewuyou.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weiwoju.kewuyou.util.ViewEventListener;

import butterknife.ButterKnife;

/**
 * Created by zhangguobing on 2017/4/27.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements IViewHolder<T> {

    protected ViewEventListener<T> mViewEventListener;
    protected T mItem;
    protected int mPosition;

    public BaseViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setViewEventListener(ViewEventListener<T> viewEventListener) {
        mViewEventListener = viewEventListener;
    }

    @Override
    public void setItem(T item) {
        mItem = item;
    }

    @Override
    public void setPosition(int position) {
        mPosition = position;
    }

    protected void notifyItemAction(int actionId, T item, View view) {
        if (mViewEventListener != null) {
            mViewEventListener.onViewEvent(actionId, item, getAdapterPosition(), view);
        }
    }

    protected void notifyItemAction(int actionId, T item) {
        notifyItemAction(actionId, item, itemView);
    }

    protected void notifyItemAction(int actionId) {
        notifyItemAction(actionId, mItem, itemView);
    }

    protected String getString(@StringRes int strResId) {
        return itemView.getContext().getString(strResId);
    }

    protected String getString(@StringRes int stringResId, Object... formatArgs) {
        return itemView.getContext().getResources().getString(stringResId, formatArgs);
    }

    @ColorInt
    protected int getColor(@ColorRes int colorResId) {
        return itemView.getContext().getResources().getColor(colorResId);
    }

    protected Drawable getDrawable(@DrawableRes int drawableId){
        return ContextCompat.getDrawable(itemView.getContext(),drawableId);
    }

    protected Context getContext(){
        return itemView.getContext();
    }
}
