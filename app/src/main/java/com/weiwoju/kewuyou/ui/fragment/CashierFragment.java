package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BusinessController;
import com.weiwoju.kewuyou.model.event.OrderChangeEvent;
import com.weiwoju.kewuyou.util.EventUtil;

/**
 * Created by zhangguobing on 2017/4/27.
 */
@ContentView(R.layout.fragment_cashier)
public class CashierFragment extends BaseFragment<BusinessController.BusinessUiCallbacks>
       implements BusinessController.BusinessUi{

//    @Bind(R.id.settle_fragment)
//    Fragment mSettleFragment;


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

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);
        setChildFragmentShown(R.id.settle_fragment, View.GONE);
    }

    public static CashierFragment newInstance() {
        return new CashierFragment();
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBusinessController();
    }

    private void setChildFragmentShown(int fragmentId,int visible){
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(fragmentId);
        if(fragment == null) return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(View.VISIBLE == visible){
            transaction.show(fragment);
        }else if(View.GONE == visible){
            transaction.hide(fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Subscribe
    public void onOrderChanged(OrderChangeEvent event){
       if(event.type == OrderChangeEvent.Type.ORDER_CREATE){
           setChildFragmentShown(R.id.settle_fragment, View.VISIBLE);
       }else if(event.type == OrderChangeEvent.Type.PAY_COMPLETE){
           setChildFragmentShown(R.id.settle_fragment, View.GONE);
       }
    }
}
