package com.weiwoju.kewuyou.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.model.bean.PrinterSection;
import com.weiwoju.kewuyou.util.FileUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangguobing on 2017/5/3.
 */
public class PrinterController extends BaseController<PrinterController.PrinterUi,PrinterController.PrinterUiCallbacks> {

    @Inject
    public PrinterController() {
    }

    private void doFetchPrinterData(int callingId){
        String content = FileUtil.readRawJsonFile(AppContext.getContext(), "printer");
        Gson gson = new Gson();
        List<PrinterSection> printerSectionList =
                gson.fromJson(content,new TypeToken<List<PrinterSection>>(){}.getType());
        PrinterUi ui = findUi(callingId);
        if(ui instanceof PrinterListUi){
            ((PrinterListUi)ui).loadPrinterDataFinish(printerSectionList);
        }
    }

    @Override
    protected PrinterUiCallbacks createUiCallbacks(final PrinterUi ui) {
        return new PrinterUiCallbacks() {
            @Override
            public void getPrinterData() {
                doFetchPrinterData(getId(ui));
            }
        };
    }

    public interface PrinterUiCallbacks {
        void getPrinterData();
    }

    public interface PrinterUi extends BaseController.Ui<PrinterUiCallbacks>{

    }
    public interface PrinterListUi extends PrinterUi {
        void loadPrinterDataFinish(List<PrinterSection> printerSections);
    }

    public interface PrinterSettingUi extends PrinterUi {
        void switched(PrinterSection.PrinterInfo printerInfo);
    }
}
