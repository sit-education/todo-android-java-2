package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{
    @SerializedName("data")
    private UserData mUserData;

    public UserData getUserData() {
        return mUserData;
    }

    public final void setUserData(UserData userData) {
        mUserData = userData;
    }
}
