package com.weiwoju.kewuyou.model.event;

import com.weiwoju.kewuyou.model.bean.LoginResult;

public class AccountChangedEvent {
    private LoginResult loginResult;

    public AccountChangedEvent(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }
}