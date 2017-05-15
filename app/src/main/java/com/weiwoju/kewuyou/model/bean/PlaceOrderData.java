package com.weiwoju.kewuyou.model.bean;

import com.weiwoju.kewuyou.network.GsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangguobing on 2017/5/5.
 * 下单提交数据
 */
public class PlaceOrderData {
    private long proid;
    private float num;
    private int style_id;

    public static String getJsonByShoppingEntity(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        List<ShoppingEntity> entities = shoppingCart.getUnOrderShoppingEntities();
        List<PlaceOrderData> list = new ArrayList<>();
        for(ShoppingEntity entity :  entities){
            if(entity.getStatus() == ShoppingEntity.Status.READY){
                PlaceOrderData placeOrderData = new PlaceOrderData();
                placeOrderData.setProid(Long.valueOf(entity.getProduct().getId()));
                placeOrderData.setNum(entity.getQuantity());
                placeOrderData.setStyle_id(entity.getProduct().getStyle_id());
                list.add(placeOrderData);
            }
        }
        return GsonHelper.builderGson().toJson(list);
    }

    public long getProid() {
        return proid;
    }

    public void setProid(long proid) {
        this.proid = proid;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public int getStyle_id() {
        return style_id;
    }

    public void setStyle_id(int style_id) {
        this.style_id = style_id;
    }
}
