package com.weiwoju.kewuyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.widget.NumberKeyboardView;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangguobing on 2017/5/9.
 */
public class ProductSpecDialog extends BaseDialog<ProductSpecDialog> {

    private ProductSpecDialog.CallBack mCallBack;

    private TextView mTitleTxt;
    private TextView mProductNumTxt;
    private TextView mTotalPriceTxt;

    private Pattern mPattern;

    private String str = "";

    private String mPrice;
    private String mHint;
    private String mTitle;

    public ProductSpecDialog(Context context,CallBack callBack) {
        super(context);
        mCallBack = callBack;
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        widthScale(0.32f);
        heightScale(0.65f);
    }

    @Override
    public View onCreateView() {
        return getLayoutInflater().inflate(R.layout.dialog_product_spec,null);
    }

    @Override
    public void setUiBeforShow() {
        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    String num = mProductNumTxt.getText().toString().trim();
                    if (num.length() > 0) {
                        mCallBack.onOkClick(Float.valueOf(num));
                        dismiss();
                    }
                }
            }
        });
        mTitleTxt = (TextView) findViewById(R.id.tv_title);
        mProductNumTxt = (TextView) findViewById(R.id.tv_product_num);
        mTotalPriceTxt = (TextView) findViewById(R.id.tv_total_price);

        mTitleTxt.setText(mTitle);
        mProductNumTxt.setHint(mHint);
        mTotalPriceTxt.setText(mPrice);

        NumberKeyboardView keyboardView = (NumberKeyboardView) findViewById(R.id.number_keyboard);
        keyboardView.setOnNumberClickListener(new NumberKeyboardView.OnNumberClickListener() {
            @Override
            public void onNumberReturn(String number) {
                //过滤
                Matcher matcher = mPattern.matcher(number);
                //已经输入小数点的情况下，只能输入数字
                if(str.contains(".")){
                    if(!matcher.matches()){
                        return;
                    }
                    if(number.equals(".")){
                        //只能输入一个小数点
                        return;
                    }
                    //验证小数点精度，保证小数点后只能输入两位
                    int index = str.indexOf(".");
                    int length = str.length() - index;
                    if(length > 1){
                        return;
                    }
                }else{
                    //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
                    if (!matcher.matches()) {
                        return ;
                    } else {
                        if ((".".equals(number)) && TextUtils.isEmpty(str)) {
                            return ;
                        }
                    }
                }

                str += number;
                setTextContent(str);
            }

            @Override
            public void onNumberDelete() {
                if (str.length() <= 1) {
                    str = "";
                } else {
                    str = str.substring(0, str.length() - 1);
                }
                setTextContent(str);
            }
        });
    }

    public String getPrice() {
        return mPrice;
    }

    public ProductSpecDialog setPrice(String price) {
        this.mPrice = price;
        return this;
    }

    public String getHint() {
        return mHint;
    }

    public ProductSpecDialog setHint(String hint) {
        this.mHint = hint;
        return this;
    }

    public ProductSpecDialog setTitle(String title){
        this.mTitle = title;
        return this;
    }

    public String getTitle(){
        return mTitle;
    }

    private void setTextContent(String content) {
        mProductNumTxt.setText(content);
        if (TextUtils.isEmpty(content)) {
            mTotalPriceTxt.setText("0.0元");
        } else {
            mTotalPriceTxt.setText(new BigDecimal(content).multiply(new BigDecimal(mPrice)).toString() + "元");
        }
    }

    public interface CallBack {
        void onOkClick(float num);
    }
}
