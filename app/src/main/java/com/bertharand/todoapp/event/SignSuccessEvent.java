package com.bertharand.todoapp.event;

import com.bertharand.todoapp.api.model.response.UserData;

public class SignSuccessEvent {
    private UserData mUserData;

    public SignSuccessEvent() {
    }

    public SignSuccessEvent(UserData userData) {
        mUserData = userData;
    }

    public UserData getUserData() {
        return mUserData;
    }

    public final void setUserData(UserData userData) {
        mUserData = userData;
    }
}
