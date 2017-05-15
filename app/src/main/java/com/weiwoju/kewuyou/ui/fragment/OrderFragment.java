package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BusinessController;
import com.weiwoju.kewuyou.model.bean.DeleteOrderProData;
import com.weiwoju.kewuyou.model.bean.PlaceOrderData;
import com.weiwoju.kewuyou.model.bean.PlaceOrderResult;
import com.weiwoju.kewuyou.model.bean.ShoppingCart;
import com.weiwoju.kewuyou.model.bean.ShoppingEntity;
import com.weiwoju.kewuyou.model.event.OrderChangeEvent;
import com.weiwoju.kewuyou.model.event.ShoppingCartChangeEvent;
import com.weiwoju.kewuyou.ui.adapter.OrderProductListAdapter;
import com.weiwoju.kewuyou.util.EventUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangguobing on 2017/4/27.
 */
@ContentView(R.layout.fragment_order)
public class OrderFragment extends BaseFragment<BusinessController.BusinessUiCallbacks>
        implements BusinessController.OrderUi {

    @Bind(R.id.tv_order_no)
    TextView mOrderNoTxt;
    @Bind(R.id.tv_order_create_time)
    TextView mOrderCreateTimeTxt;
    @Bind(R.id.order_product_listview)
    ListView mOrderProductListView;
    @Bind(R.id.tv_order_product_sum)
    TextView mOrderProductSumTxt;
    @Bind(R.id.tv_order_total_price)
    TextView mOrderTotalPriceTxt;
    @Bind(R.id.tv_order_coupon_price)
    TextView mOrderCouponPriceTxt;
    @Bind(R.id.tv_order_receive_amount)
    TextView mOrderReceiveAmountTxt;
    @Bind(R.id.tv_order_remark)
    TextView mOrderRemarkTxt;
    @Bind(R.id.btn_edit_order)
    Button mEditOrderBtn;
    @Bind(R.id.btn_place_order)
    Button mPlaceOrderBtn;
    @Bind(R.id.btn_print_order)
    Button mPrintBtn;

    public static final int EMPTY_LIST_COUNT = 8;

    private OrderProductListAdapter mOrderProductListAdapter;

    private int mDeletePosition = -1;

    private List<PlaceOrderResult.OrderDetailBean.ProlistBean> mPlaceOrderProList;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBusinessController();
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

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);

        mOrderProductListAdapter = new OrderProductListAdapter();
        mOrderProductListView.setAdapter(mOrderProductListAdapter);

        List<ShoppingEntity> orderProductList = new ArrayList<>();
        for (int i = 0; i < EMPTY_LIST_COUNT; i++){
            orderProductList.add(new ShoppingEntity());
        }
        mOrderProductListAdapter.setItems(orderProductList);

        mOrderProductListAdapter.setProductOpListener(new OrderProductListAdapter.ProductOpListener() {
            @Override
            public void onDelete(int position) {
                ShoppingEntity entity = mOrderProductListAdapter.getItem(position);
                if (entity.getStatus() == ShoppingEntity.Status.READY) {
                    mOrderProductListAdapter.removeProductItemAndClose(position);
                }else if (entity.getStatus() == ShoppingEntity.Status.ORDERED) {
                    String orderNo = mOrderNoTxt.getText().toString().trim();
                    if (TextUtils.isEmpty(orderNo) || mPlaceOrderProList == null || mPlaceOrderProList.size() == 0)
                        return;
                    showLoading(R.string.label_being_something);
                    mDeletePosition = position;
                    int id = Integer.parseInt(mPlaceOrderProList.get(position).getId());
                    DeleteOrderProData orderProData = new DeleteOrderProData(id, entity.getQuantity());
                    List<DeleteOrderProData> data = new ArrayList<>();
                    data.add(orderProData);
                    getCallbacks().deleteOrderProduct(orderNo, data);
                }
            }
        });

        mOrderProductListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mOrderProductListAdapter.isEdit()) {
                            mOrderProductListAdapter.closeAll();
                        }
                }
                return false;
            }
        });
    }

    @Subscribe
    public void onShoppingCartChange(ShoppingCartChangeEvent event){
        refreshOrderProListAndStatistics();
    }

    private void refreshOrderProListAndStatistics(){
        //refresh order list
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        List<ShoppingEntity> entities = shoppingCart.getShoppingList();
        int size = entities.size();
        if(size < EMPTY_LIST_COUNT){
            for (int i = 0 ; i < EMPTY_LIST_COUNT - size ; i++){
                entities.add(new ShoppingEntity());
            }
        }
        mOrderProductListAdapter.setItems(entities);
        if(size > EMPTY_LIST_COUNT){
            mOrderProductListView.setSelection(size - 1);
        }
        //refresh order statistics
        mOrderProductSumTxt.setText(Html.fromHtml("合计  <font size='22' color='red'>" + size + "</font>  项"));
        String formatPriceStr = new BigDecimal(shoppingCart.getTotalPrice())
                .setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString() + " 元";
        mOrderTotalPriceTxt.setText(formatPriceStr);
        mOrderReceiveAmountTxt.setText(formatPriceStr);

        int ordered_size = shoppingCart.getOrderedShoppingEntities().size();
        mEditOrderBtn.setEnabled(size > 0);
        mPlaceOrderBtn.setEnabled(shoppingCart.getUnOrderShoppingEntities().size() > 0);
        mPrintBtn.setEnabled(ordered_size > 0);
    }

    @OnClick({R.id.btn_edit_order,R.id.btn_place_order,R.id.btn_print_order})
    public void onClick(View view){
        if(view.getId() == R.id.btn_place_order){
            placeOrder();
        }else if(view.getId() == R.id.btn_edit_order){
            editOrder();
        }
    }

    private void editOrder() {
        boolean isEdit = mOrderProductListAdapter.isEdit();
        mOrderProductListAdapter.setEdit(!isEdit);
        if(isEdit){
            mOrderProductListAdapter.closeAll();
        }else{
            mOrderProductListAdapter.openLeftAll();
        }
    }

    private void placeOrder(){
        String placeOrderData = PlaceOrderData.getJsonByShoppingEntity();
        showLoading(R.string.label_being_something);
        getCallbacks().createOrder(placeOrderData);
    }

    @Override
    public void createOrderFinish(PlaceOrderResult placeOrderResult) {
        cancelLoading();
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        shoppingCart.orderedAll();

        mOrderNoTxt.setText(placeOrderResult.getOrder_detail().getNo());
        mOrderCreateTimeTxt.setText(placeOrderResult.getOrder_detail().getCreate_time());
//        mOrderRemarkTxt.setText("");

        mPlaceOrderProList = placeOrderResult.getOrder_detail().getProlist();

        EventUtil.sendEvent(new OrderChangeEvent(OrderChangeEvent.Type.ORDER_CREATE, placeOrderResult.getOrder_detail()));
    }

    @Override
    public void deleteOrderProFinish() {
        cancelLoading();
        if(mDeletePosition != -1){
            mOrderProductListAdapter.removeProductItemAndClose(mDeletePosition);
            mDeletePosition = -1;
        }
    }

    @Subscribe
    public void onOrderChanged(OrderChangeEvent event){
        if(event.type == OrderChangeEvent.Type.PAY_COMPLETE){
            reset();
        }
    }

    private void reset(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        shoppingCart.clearAll();

        mOrderNoTxt.setText("");
        mOrderCreateTimeTxt.setText("");
        mOrderProductListAdapter.clearItems();
        mOrderProductSumTxt.setText("合计  0   项");
        mOrderTotalPriceTxt.setText("0 元");
        mOrderReceiveAmountTxt.setText("0 元");
        mOrderRemarkTxt.setText("");

        mEditOrderBtn.setEnabled(false);
        mPlaceOrderBtn.setEnabled(false);
        mPrintBtn.setEnabled(false);
    }
}
