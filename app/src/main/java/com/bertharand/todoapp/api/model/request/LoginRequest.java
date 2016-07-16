package com.bertharand.todoapp.api.model.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("login")
    private final String mLogin;
    @SerializedName("password")
    private final String mPassword;

    public LoginRequest(String login, String password) {
        mLogin = login;
        mPassword = password;
    }

    public final String getLogin() {
        return mLogin;
    }

    public final String getPassword() {
        return mPassword;
    }
}
