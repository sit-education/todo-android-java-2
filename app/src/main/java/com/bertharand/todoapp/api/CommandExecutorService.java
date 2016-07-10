package com.bertharand.todoapp.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bertharand.todoapp.api.command.BaseCommand;

public class CommandExecutorService extends Service {
    public static final String ACTION_EXECUTE_COMMAND = "ACTION_EXECUTE_COMMAND";
    public static final String EXTRA_COMMAND = "EXTRA_COMMAND";

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_EXECUTE_COMMAND.equals(intent.getAction())) {
            getCommand(intent).execute(getApplicationContext());
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

    private BaseCommand getCommand(Intent intent) {
        return intent.getParcelableExtra(EXTRA_COMMAND);
    }
}
