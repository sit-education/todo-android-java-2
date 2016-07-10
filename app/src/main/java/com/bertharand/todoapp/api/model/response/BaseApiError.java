package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseApiError {
    @SerializedName("errors")
    private List<ApiError> mErrorList;

    public List<ApiError> getErrorList() {
        return mErrorList;
    }

    public final void setErrorList(List<ApiError> errorList) {
        mErrorList = errorList;
    }
}
