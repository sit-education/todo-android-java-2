package com.bertharand.todoapp.api.model.request;

import com.google.gson.annotations.SerializedName;

public class RestorePasswordData {
    @SerializedName("email")
    private String mEmail;

    public RestorePasswordData(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public final void setEmail(String email) {
        mEmail = email;
    }
}
