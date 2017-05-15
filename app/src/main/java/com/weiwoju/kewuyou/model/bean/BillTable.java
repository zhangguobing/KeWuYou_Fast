package com.weiwoju.kewuyou.model.bean;

/**
 * Created by zhangguobing on 2017/5/2.
 */
public class BillTable {
    private String time;
    private String price;

    public BillTable() {
    }

    public BillTable(String time, String price) {
        this.time = time;
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
