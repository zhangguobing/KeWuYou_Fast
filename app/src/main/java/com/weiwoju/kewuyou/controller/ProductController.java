package com.weiwoju.kewuyou.controller;

import com.google.common.base.Preconditions;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.network.ApiClient;
import com.weiwoju.kewuyou.network.RequestCallback;
import com.weiwoju.kewuyou.network.ResponseError;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class ProductController extends BaseController<ProductController.ProductUi,ProductController.ProductUiCallbacks> {

    private final ApiClient mApiClient;

    @Inject
    public ProductController(ApiClient apiClient) {
        super();
        mApiClient = Preconditions.checkNotNull(apiClient, "apiClient cannot be null");
    }

    private void doFetchProductAndCategory(final int callingId){
        mApiClient.productService().list().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestCallback<ProductCategory>() {
                    @Override
                    public void onResponse(ProductCategory productCategory) {
                        findUi(callingId).loadProductAndCategoryFinish(productCategory);
                    }

                    @Override
                    public void onFailure(ResponseError error) {
                        findUi(callingId).onResponseError(error);
                    }
                });
    }

    @Override
    protected ProductUiCallbacks createUiCallbacks(final ProductUi ui) {
        return new ProductUiCallbacks() {
            @Override
            public void refresh() {
                doFetchProductAndCategory(getId(ui));
            }

            @Override
            public void loadProductAndCategory() {
                doFetchProductAndCategory(getId(ui));
            }
        };
    }

    public interface ProductUiCallbacks {
        void refresh();
        void loadProductAndCategory();
    }

    public interface ProductUi extends BaseController.Ui<ProductUiCallbacks>{
         void loadProductAndCategoryFinish(ProductCategory productCategory);
    }

}
