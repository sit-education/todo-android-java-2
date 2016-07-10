package com.bertharand.todoapp.api.command;

import android.content.Context;
import android.os.Parcelable;

import de.greenrobot.event.EventBus;

public abstract class BaseCommand implements Parcelable {

    public final void execute(Context context) {
        doExecute(context);
    }

    protected abstract void doExecute(Context context);

    final void notifySubscribers(Object event) {
        EventBus.getDefault().post(event);
    }
}