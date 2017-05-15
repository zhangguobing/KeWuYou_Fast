package com.weiwoju.kewuyou.controller;

import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.model.bean.DeleteOrderProData;
import com.weiwoju.kewuyou.model.bean.PlaceOrderResult;
import com.weiwoju.kewuyou.network.ApiClient;
import com.weiwoju.kewuyou.network.GsonHelper;
import com.weiwoju.kewuyou.network.RequestCallback;
import com.weiwoju.kewuyou.network.ResponseError;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class BusinessController extends BaseController<BusinessController.BusinessUi,BusinessController.BusinessUiCallbacks> {

    private final ApiClient mApiClient;

    @Inject
    public BusinessController(ApiClient apiClient) {
        super();
        mApiClient = Preconditions.checkNotNull(apiClient, "apiClient cannot be null");
    }

    private void doFetchOrder(final int callingId, String id){

    }

    private void doCreateOrder(final int callingId, String prolist){
        mApiClient.businessService()
                .orderCreate(prolist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestCallback<PlaceOrderResult>() {
                    @Override
                    public void onResponse(PlaceOrderResult result) {
                        BusinessUi ui = findUi(callingId);
                        if(ui instanceof OrderUi) {
                            ((OrderUi) ui).createOrderFinish(result);
                        }
                    }

                    @Override
                    public void onFailure(ResponseError error) {
                        BusinessUi ui = findUi(callingId);
                        if(ui instanceof OrderUi){
                            ui.onResponseError(error);
                        }
                    }
                });
    }


    public void doPay(final int callingId, Map<String,String> params){
        mApiClient.businessService()
                .pay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestCallback<Object>() {
                    @Override
                    public void onResponse(Object obj) {
                        BusinessUi ui = findUi(callingId);
                        if(ui instanceof SettleUi) {
                            ((SettleUi) ui).payFinish();
                        }
                    }

                    @Override
                    public void onFailure(ResponseError error) {
                        BusinessUi ui = findUi(callingId);
                        if(ui instanceof SettleUi){
                            ui.onResponseError(error);
                        }
                    }
                });
    }


    private void doDeleteOrderProduct(final int callingId, String orderNo,List<DeleteOrderProData> data){
        String prolistJson = GsonHelper.builderGson().toJson(data);
        mApiClient.businessService()
                .deleteOrderProduct(orderNo,"delete",prolistJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestCallback<Object>() {
                    @Override
                    public void onResponse(Object obj) {
                        BusinessUi ui = findUi(callingId);
                        if (ui instanceof OrderUi) {
                            ((OrderUi) ui).deleteOrderProFinish();
                        }
                    }

                    @Override
                    public void onFailure(ResponseError error) {
                        BusinessUi ui = findUi(callingId);
                        if (ui instanceof OrderUi) {
                            ui.onResponseError(error);
                        }
                    }
                });
    }

    @Override
    protected BusinessUiCallbacks createUiCallbacks(final BusinessUi ui) {
        return new BusinessUiCallbacks() {
            @Override
            public void createOrder(String prolist) {
                doCreateOrder(getId(ui),prolist);
            }

            @Override
            public void pay(Map<String, String> params) {
                doPay(getId(ui),params);
            }

            @Override
            public void deleteOrderProduct(String orderNo,List<DeleteOrderProData> data) {
                doDeleteOrderProduct(getId(ui),orderNo,data);
            }
        };
    }

    public interface BusinessUiCallbacks {
        void createOrder(String prolist);
        void pay(Map<String,String> params);
        void deleteOrderProduct(String orderNo, List<DeleteOrderProData> data);
    }

    public interface BusinessUi extends BaseController.Ui<BusinessUiCallbacks>{

    }

    public interface OrderUi extends BusinessUi {
        void createOrderFinish(PlaceOrderResult placeOrderResult);
        void deleteOrderProFinish();
    }

    public interface SettleUi extends BusinessUi {
        void payFinish();
    }
}
