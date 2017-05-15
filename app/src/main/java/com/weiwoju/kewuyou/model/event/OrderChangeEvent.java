package com.weiwoju.kewuyou.model.event;

import com.weiwoju.kewuyou.model.bean.PlaceOrderResult;

/**
 * Created by zhangguobing on 2017/5/6.
 * 下单完成
 */
public class OrderChangeEvent {
    public Type type = Type.NONE;
    public PlaceOrderResult.OrderDetailBean orderDetail;

    public OrderChangeEvent(Type type, PlaceOrderResult.OrderDetailBean orderDetail) {
        this.type = type;
        this.orderDetail = orderDetail;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        NONE,ORDER_CREATE,PAY_COMPLETE
    }
}
