package com.weiwoju.kewuyou.util;

import android.text.TextUtils;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class StringUtil {

    public static boolean isEmpty(String text){
        return TextUtils.isEmpty(text) || "null".equals(text.trim());
    }

}
