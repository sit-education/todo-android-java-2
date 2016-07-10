package com.bertharand.todoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class AppSettings {
    private static final String DEFAULT_VALUE = "";
    private static final String APP_SETTINGS = "APP_SETTINGS";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_TOKEN_EXPIRED = "KEY_TOKEN_EXPIRED";

    private static String mToken;
    private static long mTokenExpired;

    private AppSettings() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static synchronized void clearSettings(Context context) {
        mToken = null;
        mTokenExpired = 0;

        getSharedPreferences(context).edit().clear().apply();
    }


    public static synchronized String getToken(Context context) {
        if (TextUtils.isEmpty(mToken)) {
            mToken = getSharedPreferences(context).getString(KEY_TOKEN, DEFAULT_VALUE);
        }
        return mToken;
    }

    public static synchronized void setToken(Context context, String value) {
        mToken = value;
        getSharedPreferences(context).edit().putString(KEY_TOKEN, value).apply();
    }

    public static synchronized long getTokenExpired(Context context) {
        if (mTokenExpired == 0) {
            mTokenExpired = getSharedPreferences(context).getLong(KEY_TOKEN_EXPIRED, 0);
        }
        return mTokenExpired;
    }

    public static synchronized void setTokenExpired(Context context, long value) {
        mTokenExpired = value;
        getSharedPreferences(context).edit().putLong(KEY_TOKEN_EXPIRED, value).apply();
    }

    public static synchronized boolean isAuthorized(Context context) {
        return !TextUtils.isEmpty(getToken(context));
    }
}
