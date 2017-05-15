package com.weiwoju.kewuyou.repository;

import com.weiwoju.kewuyou.model.bean.LoginResult;
import com.weiwoju.kewuyou.util.PreferenceUtil;

public class UserManager {

    private static final Class<LoginResult> CLAZZ = LoginResult.class;

    private LoginResult loginResult;

    public void saveUser(LoginResult loginResult) {
        if (loginResult == null) {
            return;
        }
        this.loginResult = loginResult;
        PreferenceUtil.set(CLAZZ.getName(), this.loginResult);
//        if (!AppConfig.DEBUG) {
//            try {
//                CrashReport.setUserId(loginResult.getId());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public LoginResult getLoginResult() {
        if (loginResult == null) {
            loginResult = PreferenceUtil.getObject(CLAZZ.getName(), CLAZZ);
        }

        return loginResult;
    }

//    public String getToken() {
//        if (getLoginResult() != null) {
//            return getLoginResult().getToken();
//        }
//        return null;
//    }

    public void clear() {
        loginResult = null;
        PreferenceUtil.set(CLAZZ.getName(), "");
    }
}
