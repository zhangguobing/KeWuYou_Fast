package com.weiwoju.kewuyou.base;

import com.weiwoju.kewuyou.util.ViewEventListener;

public interface IViewHolder<T> {

    void setViewEventListener(ViewEventListener<T> viewEventListener);

    void setItem(T item);

    void setPosition(int position);
}
