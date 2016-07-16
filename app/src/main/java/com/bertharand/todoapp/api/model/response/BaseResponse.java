package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("status")
    private byte mStatus;

    public final byte getStatus() {
        return mStatus;
    }

    public final void setStatus(byte status) {
        mStatus = status;
    }
}
