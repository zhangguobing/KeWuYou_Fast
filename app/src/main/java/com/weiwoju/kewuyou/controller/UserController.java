package com.weiwoju.kewuyou.controller;

import com.google.common.base.Preconditions;
import com.squareup.otto.Subscribe;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.context.AppCookie;
import com.weiwoju.kewuyou.model.bean.LoginResult;
import com.weiwoju.kewuyou.model.bean.ResponseBean;
import com.weiwoju.kewuyou.model.bean.ShoppingCart;
import com.weiwoju.kewuyou.model.event.AccountChangedEvent;
import com.weiwoju.kewuyou.network.ApiClient;
import com.weiwoju.kewuyou.network.RequestCallback;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.util.EventUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangguobing on 2017/4/22.
 */
public class UserController extends BaseController<UserController.UserUi,UserController.UserUiCallbacks> {

    private final ApiClient mApiClient;

    @Inject
    public UserController(ApiClient apiClient) {
        super();
        mApiClient = Preconditions.checkNotNull(apiClient, "restApiClient cannot be null");
    }

    @Subscribe
    public void onAccountChanged(AccountChangedEvent event) {
        LoginResult loginResult = event.getLoginResult();
        // && !StringUtil.isEmpty(loginResult.getTel())
        if (loginResult != null) {
            AppCookie.saveUserInfo(loginResult);
            AppCookie.saveLastPhone(loginResult.getTel());
            AppCookie.saveSessionId(loginResult.getSession_id());
            mApiClient.setSessionId(loginResult.getSession_id());
        } else {
            AppCookie.saveUserInfo(null);
            AppCookie.saveSessionId(null);
            mApiClient.setSessionId(null);
            ShoppingCart.getInstance().clearAll();
        }
        populateUis();
    }

    @Override
    protected void onInited() {
        super.onInited();
        EventUtil.register(this);
    }

    @Override
    protected void onSuspended() {
        EventUtil.unregister(this);
        super.onSuspended();
    }

    @Override
    protected UserUiCallbacks createUiCallbacks(final UserUi ui) {
        return new UserUiCallbacks() {
            @Override
            public void login(String userName, String password) {
                doLogin(getId(ui),userName,password);
            }

            @Override
            public void setShop(String shopId) {
                doSetShop(getId(ui),shopId);
            }
        };
    }

    private void doLogin(final int callingId, String userName, String password){
        mApiClient.accountService()
                .login(userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestCallback<LoginResult>() {
                    @Override
                    public void onResponse(LoginResult result) {
                        UserUi ui = findUi(callingId);
                        if(ui instanceof UserLoginUi){
                            ((UserLoginUi) ui).userLoginFinish(result.getShoplist());
                            // 发送用户账户改变的事件
                            EventUtil.sendEvent(new AccountChangedEvent(result));
                        }
                    }

                    @Override
                    public void onFailure(ResponseError error) {
                        UserUi ui = findUi(callingId);
                        if (ui instanceof UserLoginUi) {
                            ui.onResponseError(error);
                        }
                    }
                });

    }

    private void doSetShop(final int callingId,String shopId){
        mApiClient.accountService()
                .setShop(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestCallback<ResponseBean>() {
                    @Override
                    public void onResponse(ResponseBean response) {
                        UserUi ui = findUi(callingId);
                        if(ui instanceof UserLoginUi){
                            ((UserLoginUi) ui).setShopFinish();
                        }
                    }

                    @Override
                    public void onFailure(ResponseError error) {
                        UserUi ui = findUi(callingId);
                        if(ui instanceof UserLoginUi){
                            ui.onResponseError(error);
                        }
                    }
                });
    }


    public interface UserUiCallbacks {
        void login(String userName,String password);
        void setShop(String shopId);
    }

    public interface UserUi extends BaseController.Ui<UserUiCallbacks>{

    }

    public interface UserLoginUi extends UserUi{
        void userLoginFinish(List<LoginResult.ShopBean> shopList);
        void setShopFinish();
    }

}
