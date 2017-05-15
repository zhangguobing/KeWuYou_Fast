package com.weiwoju.kewuyou.ui.fragment;

import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.PrinterController;

/**
 * Created by zhangguobing on 2017/5/3.
 */
@ContentView(R.layout.fragment_printer)
public class PrinterFragment extends BaseFragment<PrinterController.PrinterUiCallbacks>
      implements PrinterController.PrinterUi{

    public static PrinterFragment newInstance() {
        return new PrinterFragment();
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getPrinterController();
    }
}
