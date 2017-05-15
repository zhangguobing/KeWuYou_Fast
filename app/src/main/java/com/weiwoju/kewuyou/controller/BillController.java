package com.weiwoju.kewuyou.controller;

import com.google.common.base.Preconditions;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.network.ApiClient;

import javax.inject.Inject;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class BillController extends BaseController<BillController.BillUi,BillController.BillUiCallbacks> {

    private final ApiClient mApiClient;

    @Inject
    public BillController(ApiClient apiClient) {
        super();
        mApiClient = Preconditions.checkNotNull(apiClient, "apiClient cannot be null");
    }

    @Override
    protected BillUiCallbacks createUiCallbacks(BillUi ui) {
        return null;
    }

    public interface BillUiCallbacks {

    }

    public interface BillUi extends BaseController.Ui<BillUiCallbacks>{

    }

}

