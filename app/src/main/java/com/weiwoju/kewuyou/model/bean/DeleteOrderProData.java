package com.weiwoju.kewuyou.model.bean;

/**
 * Created by zhangguobing on 2017/5/8.
 *
 */
public class DeleteOrderProData {
    private int id;
    private float num;

    public DeleteOrderProData(int id, float num) {
        this.id = id;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }
}
