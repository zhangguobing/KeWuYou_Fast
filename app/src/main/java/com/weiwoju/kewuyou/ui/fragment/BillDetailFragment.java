package com.weiwoju.kewuyou.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.GridView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BillController;
import com.weiwoju.kewuyou.model.bean.Bill;
import com.weiwoju.kewuyou.ui.adapter.BillListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangguobing on 2017/4/28.
 */
@ContentView(R.layout.fragment_bill_detail)
public class BillDetailFragment extends BaseFragment<BillController.BillUiCallbacks>
        implements BillController.BillUi {

    @Bind(R.id.bill_grid_view)
    GridView mBillGridView;

    private BillListAdapter mBillListAdapter;

    private Calendar mCalendar = Calendar.getInstance();

    public static BillFragment newInstance() {
        return new BillFragment();
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBillController();
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);

        mBillListAdapter = new BillListAdapter();
        mBillGridView.setAdapter(mBillListAdapter);

        List<Bill> data = new ArrayList<>();
        Bill bill1 = new Bill("1","2","1号桌","10:02","2.00","1");
        Bill bill2 = new Bill("1","2","2号桌","10:03","3.00","2");
        Bill bill3 = new Bill("1","2","3号桌","10:04","2.50","3");
        Bill bill4 = new Bill("1","2","4号桌","10:07","12.00","4");
        Bill bill5 = new Bill("1","2","5号桌","10:34","24.00","5");
        Bill bill6 = new Bill("1","2","6号桌","10:42","26.00","6");
        data.add(bill1);
        data.add(bill2);
        data.add(bill3);
        data.add(bill4);
        data.add(bill5);
        data.add(bill6);

        mBillListAdapter.setItems(data);
    }


    @OnClick({R.id.bill_date_spinner})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.bill_date_spinner:
                showSelectDateDialog();
                break;
        }
    }

    private void showSelectDateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    }
                },mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle("选择日期");
        dialog.show();
    }


}
