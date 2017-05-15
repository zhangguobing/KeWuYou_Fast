package com.weiwoju.kewuyou.model.bean;

import com.weiwoju.kewuyou.model.event.ShoppingCartChangeEvent;
import com.weiwoju.kewuyou.util.EventUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangguobing on 2017/2/17.
 * 购物车单例类
 */
public class ShoppingCart {

    private static ShoppingCart instance;

    private Map<String,ShoppingEntity> mShoppingList;

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    private ShoppingCart(){
        mShoppingList = new LinkedHashMap<>();
    }

    private void sendChangeEvent() {
        EventUtil.sendEvent(new ShoppingCartChangeEvent());
    }

    /**
     * 往购物车内添加商品
     * @param product 添加的商品对象
     */
    public void push(ProductCategory.ProlistBean product){
        String id =  product.getId();
        ShoppingEntity entity;
        if(mShoppingList.isEmpty()){
            // 通过Product对象初始化一个ShoppingEntity对象
            entity = ShoppingEntity.initWithProduct(product);
        }else{
            entity = mShoppingList.containsKey(id) ? mShoppingList.get(id) : null;
            if(entity == null){
                entity = ShoppingEntity.initWithProduct(product);
            }else{
                entity.setQuantity(entity.getQuantity() + 1);
            }
        }
        mShoppingList.put(id, entity);
        sendChangeEvent();
    }


    public void push(ProductCategory.ProlistBean product, float quantity){
        String id = product.getId();
        ShoppingEntity entity;
        if(mShoppingList.isEmpty()){
            // 通过Product对象初始化一个ShoppingEntity对象
            entity = ShoppingEntity.initWithProduct(product);
        }else{
            entity = mShoppingList.containsKey(id) ? mShoppingList.get(id) : null;
            if(entity == null){
                entity = ShoppingEntity.initWithProductWithQuantity(product, quantity);
            }else{
                entity.setQuantity(entity.getQuantity() + quantity);
            }
        }
        mShoppingList.put(id, entity);
        sendChangeEvent();
    }

    /**
     * 往购物车里减少商品
     * @param product 需要减少的商品对象
     * @return 是否减少成功
     */
    public boolean pop(ProductCategory.ProlistBean product){
        String id = product.getId();
        if(mShoppingList.containsKey(id)){
            ShoppingEntity entity = mShoppingList.get(id);
            float originalQuantity = entity.getQuantity();
            if(originalQuantity > 1){
                entity.setQuantity(--originalQuantity);
                mShoppingList.put(id, entity);
            }else if(originalQuantity == 1){
                mShoppingList.remove(id);
            }
            sendChangeEvent();
            return true;
        }
        return false;
    }

    /**
     * 往购物车里添加指定数量的商品
     * @param product 需要添加的商品对象
     * @return 是否添加成功
     */
    public void set(ProductCategory.ProlistBean product,float quantity){
        String id = product.getId();
        ShoppingEntity entity = mShoppingList.containsKey(id) ? mShoppingList.get(id) : null;
        if (entity == null) {
            entity = ShoppingEntity.initWithProduct(product);
        }
        if (quantity > 0) {
            entity.setQuantity(quantity);
            mShoppingList.put(id, entity);
        } else {
            mShoppingList.remove(id);
        }
        sendChangeEvent();
    }

    /**
     * 再来一单
     * @param shoppingEntities
     */
    public void again(List<ShoppingEntity> shoppingEntities) {
        mShoppingList.clear();
        for (ShoppingEntity entity : shoppingEntities) {
            ProductCategory.ProlistBean product = entity.getProduct();
            if (product != null) {
                mShoppingList.put(product.getId(), entity);
            }
        }
        sendChangeEvent();
    }


    public void setShoppingEntity(ShoppingEntity entity,String remark){
        if(mShoppingList.containsKey(entity.getId())){
            mShoppingList.get(entity.getId()).setRemark(remark);
        }
    }


    public void clearAll(){
        mShoppingList.clear();
        sendChangeEvent();
    }

    /**
     * 将所有商品设为已下单状态
     */
    public void orderedAll(){
        for (ShoppingEntity entity : mShoppingList.values()){
            entity.setStatus(ShoppingEntity.Status.ORDERED);
        }
        sendChangeEvent();
    }

    /**
     * 获取所有未下单的商品
     * @return
     */
    public List<ShoppingEntity> getUnOrderShoppingEntities(){
        List<ShoppingEntity> shoppingEntities = new ArrayList<>();
        for (ShoppingEntity entity : mShoppingList.values()){
            if(entity.getStatus() == ShoppingEntity.Status.READY){
                shoppingEntities.add(entity);
            }
        }
        return shoppingEntities;
    }

    /**
     * 获取所有已下单的商品
     * @return
     */
    public List<ShoppingEntity> getOrderedShoppingEntities(){
        List<ShoppingEntity> shoppingEntities = new ArrayList<>();
        for (ShoppingEntity entity : mShoppingList.values()){
            if(entity.getStatus() == ShoppingEntity.Status.ORDERED){
                shoppingEntities.add(entity);
            }
        }
        return shoppingEntities;
    }

    /**
     * 获取购物车里所有商品的总价
     * @return 商品总价
     */
    public double getTotalPrice(){
        double totalPrice = 0.0d;
        for (ShoppingEntity entity : mShoppingList.values()){
            totalPrice += entity.getTotalPrice();
        }
        return totalPrice;
    }


    /**
     * 获取购物车里所有商品的数量
     * @return 商品数量
     */
    public float getTotalQuantity() {
        float totalQuantity = 0;
        for (ShoppingEntity entry : mShoppingList.values()) {
            totalQuantity += entry.getQuantity();
        }

        return totalQuantity;
    }


    /**
     * 获取购物车里指定商品分类的数量
     * @param category 指定的商品分类
     * @return 商品数量
     */
    public float getQuantityForCategory(ProductCategory.CatelistBean category) {
        float totalQuantity = 0;
        for (ShoppingEntity entry : mShoppingList.values()) {
            ProductCategory.ProlistBean product = entry.getProduct();
            if (product != null && product.getCate_id().equals(category.getId())) {
                totalQuantity += entry.getQuantity();
            }
        }
        return totalQuantity;
    }

    /**
     * 获取购物车里指定商品的数量
     * @param product 指定的商品
     * @return 商品数量
     */
    public float getQuantityForProduct(ProductCategory.ProlistBean  product) {
        String id = product.getId();
        if (mShoppingList.containsKey(id)) {
            return mShoppingList.get(id).getQuantity();
        }
        return 0;
    }


    public void removeShoppingList(ShoppingEntity entity){
        mShoppingList.remove(entity.getId());
        sendChangeEvent();
    }

    /**
     * 获取购物车的选购列表
     * @return 选购列表
     */
    public List<ShoppingEntity> getShoppingList() {
        List<ShoppingEntity> entities = new ArrayList<>();
        for (ShoppingEntity entry : mShoppingList.values()) {
            entities.add(entry);
        }
        return entities;
    }

}
