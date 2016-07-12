package com.bertharand.todoapp.event;

import com.bertharand.todoapp.api.model.response.User;

public class SignSuccessEvent {
    private User mUser;

    public SignSuccessEvent() {
    }

    public SignSuccessEvent(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public final void setUser(User user) {
        mUser = user;
    }
}
