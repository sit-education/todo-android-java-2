package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    private long mUserId;
    @SerializedName("emailVerified")
    private byte mIsEmailVerified;
    @SerializedName("tokenKey")
    private String mTokenKey;
    @SerializedName("tokenExpired")
    private long mTokenExpired;

    public long getUserId() {
        return mUserId;
    }

    public final void setUserId(long userId) {
        mUserId = userId;
    }

    public byte getIsEmailVerified() {
        return mIsEmailVerified;
    }

    public final void setIsEmailVerified(byte isEmailVerified) {
        mIsEmailVerified = isEmailVerified;
    }

    public String getTokenKey() {
        return mTokenKey;
    }

    public final void setTokenKey(String tokenKey) {
        mTokenKey = tokenKey;
    }

    public long getTokenExpired() {
        return mTokenExpired;
    }

    public final void setTokenExpired(long tokenExpired) {
        mTokenExpired = tokenExpired;
    }
}
