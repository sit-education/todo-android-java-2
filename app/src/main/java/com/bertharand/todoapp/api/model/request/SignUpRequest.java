package com.bertharand.todoapp.api.model.request;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("email")
    private final String mEmail;
    @SerializedName("login")
    private final String mLogin;
    @SerializedName("firstName")
    private final String mFirstName;
    @SerializedName("lastName")
    private final String mLastName;
    @SerializedName("password")
    private final String mPassword;
    @SerializedName("confPass")
    private final String mConfirmPassword;

    public SignUpRequest(String email, String login, String firstName,
                         String lastName, String password, String confirmPassword) {
        mEmail = email;
        mLogin = login;
        mFirstName = firstName;
        mLastName = lastName;
        mPassword = password;
        mConfirmPassword = confirmPassword;
    }

    public final String getEmail() {
        return mEmail;
    }

    public final String getLogin() {
        return mLogin;
    }

    public final String getFirstName() {
        return mFirstName;
    }

    public final String getLastName() {
        return mLastName;
    }

    public final String getPassword() {
        return mPassword;
    }

    public final String getConfirmPassword() {
        return mConfirmPassword;
    }
}
