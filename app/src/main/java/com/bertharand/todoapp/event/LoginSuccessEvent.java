package com.bertharand.todoapp.event;

import com.bertharand.todoapp.api.model.response.UserData;

public class LoginSuccessEvent {
    private UserData mUserData;

    public LoginSuccessEvent(UserData userData) {
        mUserData = userData;
    }

    public UserData getUserData() {
        return mUserData;
    }

    public final void setUserData(UserData userData) {
        mUserData = userData;
    }
}
