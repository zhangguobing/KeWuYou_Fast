package com.weiwoju.kewuyou.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.util.ScanGunKeyEventHelper;

/**
 * Created by zhangguobing on 2017/5/8.
 */
public class PayQRCodeDialog extends BaseDialog<PayQRCodeDialog> {

    private String mPrice;
    /**扫描枪读取回车键特殊处理*/
    private boolean mAnalysisKeyEvent = false;
    private ScanGunKeyEventHelper mScanGunKeyEventHelper = new ScanGunKeyEventHelper();

    public PayQRCodeDialog(Context context,String price) {
        super(context);
        this.mPrice = price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public View onCreateView() {
        return getLayoutInflater().inflate(R.layout.dialog_pay_qrcode,null);
    }

    @Override
    public void setUiBeforShow() {
        TextView priceTxt=(TextView)findViewById(R.id.dialog_prepay_price);
        priceTxt.setText(mPrice);

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnalysisKeyEvent)
                {
                    mAnalysisKeyEvent = false;
                    return;
                }
                dismiss();
            }
        });
    }

    public void setScanGunKeyEventHelper(ScanGunKeyEventHelper.OnScanSuccessListener listener)
    {
        mScanGunKeyEventHelper.setOnBarCodeCatchListener(listener);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(!"Virtual".equals(event.getDevice().getName()))
        {
            mScanGunKeyEventHelper.analysisKeyEvent(event);

            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                mAnalysisKeyEvent = true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
