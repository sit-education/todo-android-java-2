package com.bertharand.todoapp.event;

public class ApiErrorEvent {
    private String mErrorMessage;

    public ApiErrorEvent(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public final void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }
}
