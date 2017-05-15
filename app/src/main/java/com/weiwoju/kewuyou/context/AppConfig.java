package com.weiwoju.kewuyou.context;

import com.weiwoju.kewuyou.BuildConfig;
import com.weiwoju.kewuyou.R;

/**
 * Created by zhangguobing on 2017/4/21.
 * APP配置信息
 */
public class AppConfig {

    /**
     * 是否是debug模式
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;
    /**
     * 服务器地址
     */
    public static final String SERVER_URL = "http://www.weiwoju.com/FastApi/";

    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s

    /**
     * 响应超时时间
     */
    public static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s


    /**
     * 应用名称
     */
    public static String APP_NAME = AppContext.getContext().getString(R.string.app_name);

    /**
     * APP密钥
     */
    public static String APP_KEY= "ikewuyouikewuyouikewuyouikewuyou";
}
