package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.dtr.settingview.lib.SettingButton;
import com.dtr.settingview.lib.SettingView;
import com.dtr.settingview.lib.entity.SettingData;
import com.dtr.settingview.lib.entity.SettingViewItemData;
import com.dtr.settingview.lib.item.BasicItemViewH;
import com.dtr.settingview.lib.item.CheckItemViewV;
import com.dtr.settingview.lib.item.ImageItemView;
import com.dtr.settingview.lib.item.SwitchItemView;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.PrinterController;
import com.weiwoju.kewuyou.model.bean.PrinterSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/5/3.
 */
@ContentView(R.layout.fragment_printer_setting)
public class PrinterSettingFragment extends BaseFragment<PrinterController.PrinterUiCallbacks>
     implements PrinterController.PrinterSettingUi{

    @Bind(R.id.tv_printer_setting_title)
    TextView mTitleTxt;
    @Bind(R.id.sv_printer_setting_part_one)
    SettingView mPrinterSettingPartOneSv;
    @Bind(R.id.sv_printer_setting_part_two)
    SettingView mPrinterSettingPartTwoSv;

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);
        initView();
    }

    private void initView() {
        List<SettingViewItemData> mListData = new ArrayList<>();

        SettingViewItemData mItemViewData = new SettingViewItemData();
        SettingData mItemData = new SettingData();
        mItemData.setTitle("打印机名称");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getContext()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("打印纸张规格");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getContext()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("打印机IP地址");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getContext()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("打印机端口号");
        mItemData.setSubTitle("9100");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getContext()));
        mListData.add(mItemViewData);

        mPrinterSettingPartOneSv.setAdapter(mListData);

        mPrinterSettingPartOneSv.setOnSettingViewItemClickListener(new SettingView.onSettingViewItemClickListener() {
            @Override
            public void onItemClick(int index) {

            }
        });
        mPrinterSettingPartOneSv.setOnSettingViewItemSwitchListener(new SettingView.onSettingViewItemSwitchListener() {
            @Override
            public void onSwitchChanged(int index, boolean isChecked) {

            }
        });

        mListData.clear();

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("打印份数");
        mItemData.setSubTitle("1份");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getContext()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("开启自动打印");
        mItemData.setChecked(true);

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new SwitchItemView(getContext()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("是否打印结账联");
        mItemData.setChecked(false);

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new SwitchItemView(getContext()));
        mListData.add(mItemViewData);

        mPrinterSettingPartTwoSv.setAdapter(mListData);

        mPrinterSettingPartTwoSv.setOnSettingViewItemClickListener(new SettingView.onSettingViewItemClickListener() {
            @Override
            public void onItemClick(int index) {

            }
        });
        mPrinterSettingPartTwoSv.setOnSettingViewItemSwitchListener(new SettingView.onSettingViewItemSwitchListener() {
            @Override
            public void onSwitchChanged(int index, boolean isChecked) {

            }
        });
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getPrinterController();
    }

    @Override
    public void switched(PrinterSection.PrinterInfo printerInfo) {

    }


}
