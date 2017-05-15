package com.weiwoju.kewuyou.ui.bean;

public enum MainTab {
    CASHIER(0),
    BILL(1),
    PRINTER(2),
    UPDATE(3),
    ABOUT(4),
    EXIT(5);

    public int position;

    MainTab(int position) {
        this.position = position;
    }

//    public int getPosition() {
//        return position;
//    }
}
