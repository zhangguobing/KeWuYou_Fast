package com.weiwoju.kewuyou.ui.fragment;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.BillController;

/**
 * Created by zhangguobing on 2017/4/28.
 */
@ContentView(R.layout.fragment_bill)
public class BillFragment extends BaseFragment<BillController.BillUiCallbacks>
        implements BillController.BillUi{

    public static BillFragment newInstance() {
        return new BillFragment();
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBillController();
    }
}
