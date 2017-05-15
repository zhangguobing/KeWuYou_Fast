package com.weiwoju.kewuyou.model.bean;

/**
 * Created by zhangguobing on 2017/5/2.
 */
public class Bill {
    public String id;
    public String no;
    private String table_name;
    private String create_time;
    private String price;
    private String serial_no;

    public Bill(String id, String no, String table_name, String create_time, String price, String serial_no) {
        this.id = id;
        this.no = no;
        this.table_name = table_name;
        this.create_time = create_time;
        this.price = price;
        this.serial_no = serial_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }
}
