package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BusinessController;
import com.weiwoju.kewuyou.model.bean.ShoppingCart;
import com.weiwoju.kewuyou.model.event.OrderChangeEvent;
import com.weiwoju.kewuyou.model.event.ShoppingCartChangeEvent;
import com.weiwoju.kewuyou.ui.dialog.GatherDialog;
import com.weiwoju.kewuyou.ui.dialog.PayQRCodeDialog;
import com.weiwoju.kewuyou.util.EventUtil;
import com.weiwoju.kewuyou.util.ScanGunKeyEventHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangguobing on 2017/4/28.
 * 结算订单界面 包含一系列对订单的操作
 */
@ContentView(R.layout.fragment_settle)
public class SettleFragment extends BaseFragment<BusinessController.BusinessUiCallbacks>
        implements BusinessController.SettleUi {

    @Bind(R.id.tv_order_price)
    TextView mOrderPriceTxt;
    @Bind(R.id.tv_clean_fraction_price)
    TextView mCleanFractionPrice;
    @Bind(R.id.tv_discount_price)
    TextView mDiscountPriceTxt;
    @Bind(R.id.tv_payed_price)
    TextView mPayedPriceTxt;
    @Bind(R.id.tv_surplus_price)
    TextView mSurplusPriceTxt;
    @Bind(R.id.tv_coupon_price)
    TextView mCouponPrice;
    @Bind(R.id.tv_member)
    TextView mMemberTxt;

    private String mOrderNo;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBusinessController();
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);
    }

    @OnClick({R.id.layout_use_coupon, R.id.layout_vip_login, R.id.tv_pay_cash,
            R.id.tv_pay_bank_card, R.id.tv_pay_barcode, R.id.layout_fraction_price,
            R.id.layout_discount_price, R.id.layout_debt})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_cash:
                showGatherDialog(GatherDialog.TYPE_CASH);
                break;
            case R.id.tv_pay_bank_card:
                showGatherDialog(GatherDialog.TYPE_BANK_CARD);
                break;
            case R.id.tv_pay_barcode:
                showGatherDialog(GatherDialog.TYPE_SCAN);
                break;
        }
    }


    private void showGatherDialog(final int type) {
        new GatherDialog(getHostActivity(), new GatherDialog.CallBack() {
            @Override
            public void onOkClick(String price, String pay_way) {
                if (GatherDialog.TYPE_SCAN == type) {
                    showPayQRCodeDialog(price, pay_way);
                }else if(GatherDialog.TYPE_CASH == type){
                    startPayRequest("现金支付",price,null);
                }else if(GatherDialog.TYPE_BANK_CARD == type){
                    startPayRequest("银行卡支付",price,null);
                }
            }
        }, mSurplusPriceTxt.getText().toString(), type).show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventUtil.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Subscribe
    public void onOrderChanged(OrderChangeEvent event) {
        if(event.type == OrderChangeEvent.Type.ORDER_CREATE){
            mOrderPriceTxt.setText(String.valueOf(event.orderDetail.getPrice()));
            mCleanFractionPrice.setText(event.orderDetail.getClean_fraction_price());
            mDiscountPriceTxt.setText(event.orderDetail.getDiscount_price());
            mPayedPriceTxt.setText(event.orderDetail.getPayed_price());
            mSurplusPriceTxt.setText(String.valueOf(event.orderDetail.getSurplus_price()));
            mCouponPrice.setText(event.orderDetail.getCoupon_price());

            mOrderNo = event.orderDetail.getNo();
        }
    }

    @Subscribe
    public void onShoppingCartChange(ShoppingCartChangeEvent event){
        mOrderPriceTxt.setText(String.valueOf(ShoppingCart.getInstance().getTotalPrice()));
    }

    @Override
    public void payFinish() {
        cancelLoading();
        reset();
        EventUtil.sendEvent(new OrderChangeEvent(OrderChangeEvent.Type.PAY_COMPLETE,null));
    }

    private void showPayQRCodeDialog(final String price, final String pay_way) {
        PayQRCodeDialog dialog = new PayQRCodeDialog(getActivity(), price);
        dialog.setScanGunKeyEventHelper(new ScanGunKeyEventHelper.OnScanSuccessListener() {
            @Override
            public void onScanSuccess(String barcode) {
                startPayRequest("刷码支付",price,barcode);
            }
        });
        dialog.show();
    }

    private void startPayRequest(String pay_method,String price, String barcode){
        if(mOrderNo == null || TextUtils.isEmpty(mOrderNo)) return;
        showLoading(R.string.label_being_something);
        Map<String,String> params = new HashMap<>();
        params.put("pay_method", pay_method);
        params.put("no",mOrderNo);
        params.put("price", price);
        if(barcode != null){
            params.put("auth_code",barcode);
        }
        getCallbacks().pay(params);
    }


    private void reset() {
        mOrderPriceTxt.setText("0");
        mCleanFractionPrice.setText("0");
        mDiscountPriceTxt.setText("0");
        mPayedPriceTxt.setText("0");
        mSurplusPriceTxt.setText("0");
        mCouponPrice.setText("0");

        mOrderNo = null;
    }
}
