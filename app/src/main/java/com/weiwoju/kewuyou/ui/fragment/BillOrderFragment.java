package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BusinessController;
import com.weiwoju.kewuyou.model.bean.ShoppingEntity;
import com.weiwoju.kewuyou.ui.adapter.OrderProductListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangguobing on 2017/4/27.
 */
@ContentView(R.layout.fragment_bill_order)
public class BillOrderFragment extends BaseFragment<BusinessController.BusinessUiCallbacks>
        implements BusinessController.BusinessUi {

    @Bind(R.id.order_product_listview)
    ListView mOrderProductListView;

    private OrderProductListAdapter mOrderProductListAdapter;

    private static final int EMPTY_LIST_COUNT = 8;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBusinessController();
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);

//        List<PlaceOrderData> orderProductList = new ArrayList<>();
//        orderProductList.add(new PlaceOrderData("布丁奶茶",1, 12.00f));
//        orderProductList.add(new PlaceOrderData("珍珠奶茶",1, 8.00f));
//        orderProductList.add(new PlaceOrderData("烧饼",1, 1.50f));
//        orderProductList.add(new PlaceOrderData("八宝粥",1, 1.00f));
//        orderProductList.add(new PlaceOrderData("现磨豆浆",1, 3.00f));
//        orderProductList.add(new PlaceOrderData("南瓜粥",1, 1.00f));
//        orderProductList.add(new PlaceOrderData("南瓜粥",1, 1.00f));
//        orderProductList.add(new PlaceOrderData("南瓜粥",1, 1.00f));

        mOrderProductListAdapter = new OrderProductListAdapter();
        mOrderProductListView.setAdapter(mOrderProductListAdapter);

        List<ShoppingEntity> orderProductList = new ArrayList<>();
        for (int i = 0; i < EMPTY_LIST_COUNT; i++){
            orderProductList.add(new ShoppingEntity());
        }
        mOrderProductListAdapter.setItems(orderProductList);
    }

    @OnClick({R.id.btn_print_order})
    public void onClick(View view){

    }


}
