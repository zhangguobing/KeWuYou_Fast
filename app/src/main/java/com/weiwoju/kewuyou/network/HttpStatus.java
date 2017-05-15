package com.weiwoju.kewuyou.network;

import com.weiwoju.kewuyou.Constants;
import com.weiwoju.kewuyou.context.AppConfig;

public class HttpStatus {
    private String errcode;
    private String errmsg;

    public HttpStatus(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

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

    /**
     * API是否请求失败
     * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return !Constants.CustomHttpCode.HTTP_SUCCESS.equals(errcode);
    }

}
