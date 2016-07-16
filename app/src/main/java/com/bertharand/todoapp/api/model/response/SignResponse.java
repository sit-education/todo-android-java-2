package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class SignResponse {
    @SerializedName("data")
    private User mUser;

    public final User getUser() {
        return mUser;
    }

    public final void setUser(User user) {
        mUser = user;
    }
}
