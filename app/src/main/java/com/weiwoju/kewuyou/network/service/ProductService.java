package com.weiwoju.kewuyou.network.service;

import com.weiwoju.kewuyou.model.bean.ProductCategory;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public interface ProductService {

    @GET("prolist")
    Observable<ProductCategory> list();
}
