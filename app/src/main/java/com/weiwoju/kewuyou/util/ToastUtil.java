package com.weiwoju.kewuyou.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwoju.kewuyou.R;

/**
 * Created by zhangguobing on 2016/10/31.
 */
public class ToastUtil {

    private static Toast mToast;

    private static TextView mTextView;

    private ToastUtil(){
    }

    public static void init(Context context){
        if(mToast == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.toast_layout, null);
            mTextView = (TextView) layout.findViewById(R.id.toast_text);
            mToast = new Toast(context);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(layout);
        }
    }


    public static void show(String text) {
        if(mToast != null){
            mTextView.setText(text);
            mToast.show();
        }
    }

    public static void show(int res) {
        if(mToast != null && res > 0){
            mTextView.setText(res);
            mToast.show();
        }
    }
}
