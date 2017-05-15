package com.weiwoju.kewuyou.util;

import java.math.BigDecimal;

/**
 * Created by zhangguobing on 2017/3/8.
 */
public class DecimalUtil {

    public static String stripTrailingZeros(float decimal,int newScale){
       return new BigDecimal(decimal).setScale(newScale,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String stripTrailingZeros(String decimal,int newScale){
       return stripTrailingZeros(Float.valueOf(decimal),newScale);
    }
}
