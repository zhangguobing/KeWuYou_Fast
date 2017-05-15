package com.weiwoju.kewuyou.controller;

import com.google.common.base.Preconditions;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.model.event.AccountChangedEvent;
import com.weiwoju.kewuyou.network.ApiClient;
import com.weiwoju.kewuyou.ui.Display;
import com.weiwoju.kewuyou.util.EventUtil;

import javax.inject.Inject;

/**
 * Created by zhangguobing on 2017/4/21.
 */
public class MainController extends BaseController<MainController.MainUi,MainController.MainUiCallbacks> {

    private final UserController mUserController;
    private final BusinessController mBusinessController;
    private final ProductController mProductController;
    private final BillController mBillController;
    private final PrinterController mPrinterController;

    private final ApiClient mApiClient;

    @Inject
    public MainController(UserController userController,
                          BusinessController businessController,
                          ProductController productController,
                          BillController billController,
                          PrinterController printerController,
                          ApiClient apiClient) {
        super();
        mUserController = Preconditions.checkNotNull(userController, "userController cannot be null");
        mBusinessController = Preconditions.checkNotNull(businessController, "businessController cannot be null");
        mProductController = Preconditions.checkNotNull(productController, "productController cannot be null");
        mBillController = Preconditions.checkNotNull(billController, "billController cannot be null");
        mPrinterController = Preconditions.checkNotNull(printerController, "printerController cannot be null");
        mApiClient = Preconditions.checkNotNull(apiClient, "apiClient cannot be null");
    }

    @Override
    protected void onInited() {
        super.onInited();
        mUserController.init();
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
        mUserController.suspend();
    }

    public void attachDisplay(Display display) {
        Preconditions.checkNotNull(display, "display is null");
        Preconditions.checkState(getDisplay() == null, "we currently have a display");
        setDisplay(display);
    }

    public void detachDisplay(Display display) {
        Preconditions.checkNotNull(display, "display is null");
        Preconditions.checkState(getDisplay() == display, "display is not attached");
        setDisplay(null);
    }

    @Override
    public void setDisplay(Display display) {
        super.setDisplay(display);
        mUserController.setDisplay(display);
    }

    /**
     * 当返回键被按下时
     */
    public void onBackButtonPressed() {
        Display display = getDisplay();
        if (display != null) {
            if (!display.popTopFragmentBackStack()) {
                display.navigateUp();
            }
        }
    }

    /**
     * 用户退出登录
     * @param callingId
     */
    private void doLogout(final int callingId) {
        MainUi ui = findUi(callingId);
        EventUtil.sendEvent(new AccountChangedEvent(null));
        ui.logoutFinish();
    }

    public final UserController getUserController() {
        return mUserController;
    }

    public final BusinessController getBusinessController(){
        return mBusinessController;
    }

    public final ProductController getProductController(){
        return mProductController;
    }

    public final BillController getBillController(){
        return mBillController;
    }

    public final PrinterController getPrinterController(){
        return mPrinterController;
    }

    @Override
    protected MainUiCallbacks createUiCallbacks(final MainUi ui) {
        return new MainUiCallbacks() {
            @Override
            public void logout() {
                doLogout(getId(ui));
            }
        };
    }

    public interface MainUi extends BaseController.Ui<MainUiCallbacks>{
        void logoutFinish();
    }

    public interface MainUiCallbacks  {
        void logout();
    }
}
