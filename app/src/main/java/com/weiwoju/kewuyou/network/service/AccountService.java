package com.weiwoju.kewuyou.network.service;

import com.weiwoju.kewuyou.model.bean.LoginResult;
import com.weiwoju.kewuyou.model.bean.ResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangguobing on 2017/4/22.
 */
public interface AccountService {

    @FormUrlEncoded
    @POST("login")
    Observable<LoginResult> login(@Field("name") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("setShop")
    Observable<ResponseBean> setShop(@Field("shop_id") String shopId);
}
