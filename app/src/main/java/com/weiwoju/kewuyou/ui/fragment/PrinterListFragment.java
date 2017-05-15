package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.PrinterController;
import com.weiwoju.kewuyou.model.bean.PrinterSection;
import com.weiwoju.kewuyou.ui.adapter.PrinterAdapter;
import com.weiwoju.kewuyou.widget.MultiStateView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/5/3.
 */
@ContentView(R.layout.fragment_printer_list)
public class PrinterListFragment extends BaseFragment<PrinterController.PrinterUiCallbacks>
implements PrinterController.PrinterListUi{

    @Bind(R.id.multi_state_view)
    MultiStateView mMultiStateView;

//    @Bind(R.id.refresh_layout)
//    TwinklingRefreshLayout mRefreshLayout;

    @Bind(R.id.printer_recycler_view)
    RecyclerView mPrinterRecyclerView;

    private PrinterAdapter mPrinterAdapter;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getPrinterController();
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);
        mMultiStateView.setState(MultiStateView.STATE_LOADING);

        mPrinterAdapter = new PrinterAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mPrinterRecyclerView.setLayoutManager(layoutManager);
        mPrinterRecyclerView.setAdapter(mPrinterAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCallbacks().getPrinterData();
    }

    @Override
    public void loadPrinterDataFinish(List<PrinterSection> printerSections) {
        mMultiStateView.setState(MultiStateView.STATE_CONTENT);
        mPrinterAdapter.setData(printerSections);
    }
}
