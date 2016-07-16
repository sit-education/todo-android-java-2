package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("error_key")
    private String mErrorKey;
    @SerializedName("error_message")
    private String mErrorMessage;

    public final String getErrorKey() {
        return mErrorKey;
    }

    public final void setErrorKey(String errorKey) {
        mErrorKey = errorKey;
    }

    public final String getErrorMessage() {
        return mErrorMessage;
    }

    public final void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }
}
