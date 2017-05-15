package com.weiwoju.kewuyou.context;

import com.weiwoju.kewuyou.Constants;
import com.weiwoju.kewuyou.model.bean.LoginResult;
import com.weiwoju.kewuyou.util.PreferenceUtil;

/**
 * Created by zhangguobing on 2017/4/21.
 */
public class AppCookie {

    // && !StringUtil.isEmpty(getUserInfo().getTel())
    public static boolean isLoggin() {
        return getUserInfo() != null;
    }

    /**
     * 保存用户信息
     * @param loginResult
     */
    public static void saveUserInfo(LoginResult loginResult) {
        PreferenceUtil.set(Constants.Persistence.USER_INFO, loginResult);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static LoginResult getUserInfo() {
        return PreferenceUtil.getObject(Constants.Persistence.USER_INFO, LoginResult.class);
    }

    /**
     * 保存最后一次登录的手机号
     * @param phone
     */
    public static void saveLastPhone(String phone) {
        PreferenceUtil.set(Constants.Persistence.LAST_LOGIN_PHONE, phone);
    }

    /**
     * 获取最后一次登录的手机号
     * @return
     */
    public static String getLastPhone() {
        return PreferenceUtil.getString(Constants.Persistence.LAST_LOGIN_PHONE, "");
    }

    public static void saveSessionId(String session_id){
        PreferenceUtil.set(Constants.Persistence.SESSION_ID, session_id);
    }

    public static String getSessionId(){
        return PreferenceUtil.getString(Constants.Persistence.SESSION_ID, null);
    }
}
