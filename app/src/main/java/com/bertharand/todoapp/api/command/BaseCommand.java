package com.bertharand.todoapp.api.command;

import android.os.Parcelable;

import de.greenrobot.event.EventBus;

public abstract class BaseCommand implements Parcelable {

    public final void execute() {
        doExecute();
    }

    protected abstract void doExecute();

    final void notifySubscribers(Object event) {
        EventBus.getDefault().post(event);
    }
}