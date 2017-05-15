package com.weiwoju.kewuyou.model.bean;

/**
 * Created by zhangguobing on 2017/2/17.
 */
public class ShoppingEntity {
    private String id;
    private String proId;
    private String name;
    private float quantity;
    private double unitPrice;
    private double totalPrice;
    private ProductCategory.ProlistBean product;
    private String remark = "";
    private Status status = Status.READY;

    public ShoppingEntity() {
    }

    public static ShoppingEntity initWithProduct(ProductCategory.ProlistBean product) {
        ShoppingEntity entity = new ShoppingEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setUnitPrice(Double.valueOf(product.getPrice()));
        entity.setQuantity(1);
        entity.setProduct(product);

        return entity;
    }

    public static ShoppingEntity initWithProductWithQuantity(ProductCategory.ProlistBean product, float quantity) {
        ShoppingEntity entity = new ShoppingEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setUnitPrice(Double.valueOf(product.getPrice()));
        entity.setQuantity(quantity);
        entity.setProduct(product);

        return entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
        this.totalPrice = this.quantity * this.unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ProductCategory.ProlistBean getProduct() {
        return product;
    }

    public void setProduct(ProductCategory.ProlistBean product) {
        this.product = product;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        READY,ORDERED
    }
}
