package com.weiwoju.kewuyou.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.util.DecimalUtil;
import com.weiwoju.kewuyou.widget.NumberKeyboardView;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangguobing on 2017/5/6.
 */
public class GatherDialog extends BaseDialog<GatherDialog> {

    //现金收款
    public static final int TYPE_CASH = 1;
    //银行卡收款
    public static final int TYPE_BANK_CARD = 4;
    //扫码收款
    public static final int TYPE_SCAN = 2;
    //抹零
    public static final int TYPE_FRACTION_PRICE = 3;

    private int mType;

    private CallBack mCallBack;

    private EditText mReceiveAmountText;

    private TextView mPrepayPriceText;
    private TextView mChangePriceText;

    private String str = "";

    private String mPrepayPrice;

    private Pattern mPattern;

    private DecimalFormat mDecimalFormat;

    private RadioButton rb_preferential;

    private LinearLayout change_layout;
    private RadioGroup rg_select;

    public GatherDialog(Context context, CallBack callBack, String prepay_price, int type) {
        super(context);
        this.mCallBack = callBack;
        this.mPrepayPrice = prepay_price;
        this.mPattern = Pattern.compile("([0-9]|\\.)*");
        this.mDecimalFormat = new DecimalFormat("##0.00");
        this.mType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        widthScale(0.35f);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    public View onCreateView() {
        return getLayoutInflater().inflate(R.layout.dialog_gather, null);
    }

    /**
     * 提交订单
     *
     * @param price 金额
     */
    private void commitOrder(String price, String pay_type) {
        dismiss();
        mCallBack.onOkClick(price, pay_type);
    }

    @Override
    public void setUiBeforShow() {
        Button btn_cancel = (Button) findViewById(R.id.dialog_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        Button btn_ok = (Button)findViewById(R.id.dialog_btn_confirm);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    final String receive_price_str = mReceiveAmountText.getText().toString();

                    if (TYPE_CASH == mType) {
                        if (TextUtils.isEmpty(receive_price_str)) {
                            commitOrder(mPrepayPrice, "clean");
                        } else {
                            // 不为空,如果实收金额小于订单金额，弹框提示抹零，提交实收金额，否则提交订单金额
                            Float receive_price = Float.valueOf(receive_price_str);
                            Float prepay_price = Float.valueOf(mPrepayPrice);
                            // 实收金额小于总金额并且选中优惠
                            if (rb_preferential.isChecked() && receive_price < prepay_price) {
                                String swipe_price_str = mDecimalFormat.format(prepay_price - receive_price);
                                final NormalDialog dialog = new NormalDialog(getContext());
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.isTitleShow(true)
                                        .title("提示")
                                        .btnNum(2)
                                        .btnText("取消","确定")
                                        .cornerRadius(5)
                                        .content("是否抹零" + swipe_price_str + "元？")
                                        .contentTextColor(R.color.black)
                                        .dividerColor(R.color.divider)
                                        .btnTextSize(15.5f, 15.5f)
                                        .btnTextColor(R.color.blue)
                                        .btnPressColor(R.color.dark_blue)
                                        .widthScale(0.4f)
                                        .show();
                                dialog.setOnBtnClickL(new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                    }
                                }, new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                        commitOrder(receive_price_str, "clean");
                                    }
                                });
                            } else if (((RadioButton)findViewById(R.id.rb_cash)).isChecked()) {
                                commitOrder(receive_price_str, "multiple");
                            } else if (receive_price >= prepay_price) {
                                commitOrder(mPrepayPrice, "clean");
                            }
                        }
                    } else {
                        commitOrder(TextUtils.isEmpty(receive_price_str) ? mPrepayPrice : receive_price_str, "multiple");
                    }
                }
            }
        });
        mReceiveAmountText = (EditText)findViewById(R.id.tv_receive_amount);
        mReceiveAmountText.setInputType(InputType.TYPE_NULL);
        mReceiveAmountText.setHint(mPrepayPrice);

        rb_preferential = (RadioButton)findViewById(R.id.rb_preferential);
        change_layout = (LinearLayout) findViewById(R.id.change_layout);
        rg_select = (RadioGroup) findViewById(R.id.rg_select);

        TextView titleTextView = (TextView) findViewById(R.id.dialog_title);
        if (mType == TYPE_CASH) {
            mChangePriceText = (TextView) findViewById(R.id.tv_change_price);
            titleTextView.setText("现金收款");
            mReceiveAmountText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 0) {
                        Float receivePrice = Float.valueOf(s.toString());
                        Float prepayPrice = Float.valueOf(mPrepayPrice);
                        Float changePrice = receivePrice - prepayPrice;
                        if (changePrice > 0) {
                            mChangePriceText.setText(mDecimalFormat.format(changePrice));
                        } else {
                            mChangePriceText.setText("");
                        }
                    }
                }
            });
            rg_select.setVisibility(View.VISIBLE);
        } else if (mType == TYPE_BANK_CARD) {
            titleTextView.setText("银行卡");
        } else if (mType == TYPE_SCAN) {
            titleTextView.setText("扫码收款");
        } else if (mType == TYPE_FRACTION_PRICE) {
            titleTextView.setText("抹零");
        }

        mPrepayPriceText = (TextView) findViewById(R.id.tv_prepay_price);
        mPrepayPriceText.setText(mPrepayPrice);
        NumberKeyboardView keyboardView = (NumberKeyboardView)findViewById(R.id.number_keyboard);
        keyboardView.setOnNumberClickListener(new NumberKeyboardView.OnNumberClickListener() {
            @Override
            public void onNumberReturn(String number) {
                //过滤
                Matcher matcher = mPattern.matcher(number);
                //已经输入小数点的情况下，只能输入数字
                if (str.contains(".")) {
                    if (!matcher.matches()) {
                        return;
                    }
                    if (number.equals(".")) {
                        //只能输入一个小数点
                        return;
                    }
                    //验证小数点精度，保证小数点后只能输入两位
                    int index = str.indexOf(".");
                    int length = str.length() - index;
                    if (length > 2) {
                        return;
                    }
                } else {
                    //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
                    if (!matcher.matches()) {
                        return;
                    } else {
                        if ((".".equals(number)) && TextUtils.isEmpty(str)) {
                            return;
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

    private void setTextContent(String content) {
        mReceiveAmountText.setText(content);
        if (mChangePriceText != null && TextUtils.isEmpty(content)) {
            mChangePriceText.setText("");
        }

        Float prepay_price = Float.valueOf(mPrepayPrice);
        Float receive_price = TextUtils.isEmpty(content) ? prepay_price : Float.valueOf(content);
        float preferential_price = prepay_price - receive_price;

        if (preferential_price < 0) {
            preferential_price = 0;
        }
        rb_preferential.setText("抹零" + DecimalUtil.stripTrailingZeros(preferential_price, 2) + "元");

        // 现金 输入金额大于等于实收金额显示找零 则显示radiobutton
        if (TYPE_CASH == mType) {
            if (receive_price > prepay_price) {
                rg_select.setVisibility(View.GONE);
                change_layout.setVisibility(View.VISIBLE);
            } else {
                rg_select.setVisibility(View.VISIBLE);
                change_layout.setVisibility(View.GONE);
            }
        } else if (TYPE_BANK_CARD == mType || TYPE_SCAN == mType) {
            // 银行卡与扫码支付最大不能超过应收金额
            if (receive_price > prepay_price) {
                mReceiveAmountText.setText(str = mPrepayPrice);
            }
        }
    }

    public interface CallBack {
        /**
         * @param price  收款金额
         * @param pay_way 支付方式 抹零、多次收款
         */
        void onOkClick(String price, String pay_way);
    }
}
