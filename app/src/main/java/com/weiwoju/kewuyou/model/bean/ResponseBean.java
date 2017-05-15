package com.weiwoju.kewuyou.model.bean;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class ResponseBean {

    private String errcode;
    private String errmsg;
    private String alert;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
