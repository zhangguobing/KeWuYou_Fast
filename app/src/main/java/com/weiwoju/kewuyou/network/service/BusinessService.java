package com.weiwoju.kewuyou.network.service;

import com.weiwoju.kewuyou.model.bean.PlaceOrderResult;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public interface BusinessService {

    @FormUrlEncoded
    @POST("orderCreate")
    Observable<PlaceOrderResult> orderCreate(@Field("prolist") String prolist);

    @FormUrlEncoded
    @POST("pay")
    Observable<Object> pay(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("orderOp")
    Observable<Object> deleteOrderProduct(@Field("no") String orderNo,
                                   @Field("op_type") String opType,
                                   @Field("prolist") String prolist);

    @FormUrlEncoded
    @POST("orderOp")
    Observable<Object> addOrderProduct(@Field("no") String orderNo,
                                       @Field("op_type") String opType,
                                       @Field("prolist") String prolist);
}
