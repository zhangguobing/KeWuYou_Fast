package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BillController;
import com.weiwoju.kewuyou.model.bean.BillTable;
import com.weiwoju.kewuyou.ui.adapter.BillTableListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/5/2.
 */
@ContentView(R.layout.fragment_bill_table)
public class BillTableFragment extends BaseFragment<BillController.BillUiCallbacks>
        implements BillController.BillUi{

    public static final int EMPTY_ITEM_COUNT = 17;

    @Bind(R.id.bill_table_list_view)
    ListView mBillTableListView;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBillController();
    }


    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);
        BillTableListAdapter adapter = new BillTableListAdapter();
        mBillTableListView.setAdapter(adapter);

        List<BillTable> data = new ArrayList<>();
        for (int i = 0; i < EMPTY_ITEM_COUNT; i ++){
            data.add(new BillTable());
        }
//        BillTable table1 = new BillTable("2016-12-29","0.00");
//        BillTable table2 = new BillTable("2016-12-29","0.00");
//        BillTable table3 = new BillTable("2016-12-29","0.00");
//        BillTable table4 = new BillTable("2016-12-29","0.00");
//        BillTable table5 = new BillTable("2016-12-29","0.00");
//        BillTable table6 = new BillTable("2016-12-29","0.00");
//        data.add(table1);
//        data.add(table2);
//        data.add(table3);
//        data.add(table4);
//        data.add(table5);
//        data.add(table6);

        adapter.setItems(data);
    }
}
