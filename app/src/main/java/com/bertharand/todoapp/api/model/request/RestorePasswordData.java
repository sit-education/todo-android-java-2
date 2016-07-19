package com.bertharand.todoapp.api.model.request;

import com.google.gson.annotations.SerializedName;

public class RestorePasswordData {
    @SerializedName("email")
    private String mEmail;

    public RestorePasswordData(String email) {
        mEmail = email;
    }

    public final String getEmail() {
        return mEmail;
    }
}
