package com.bertharand.todoapp.api;

import android.content.Context;
import android.content.Intent;

import com.bertharand.todoapp.api.command.BaseCommand;
import com.bertharand.todoapp.api.command.LoginCommand;

public class ToDoApiServiceHelper {
    private static volatile ToDoApiServiceHelper mInstance;
    private final Context mContext;

    private ToDoApiServiceHelper(Context context) {
        mContext = context;
    }

    public static ToDoApiServiceHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ToDoApiServiceHelper.class) {
                if (mInstance == null) {
                    mInstance = new ToDoApiServiceHelper(context);
                }
            }
        }
        return mInstance;
    }

    public final void login(String login, String password) {
        Intent i = createIntent(new LoginCommand(login, password));
        startService(i);
    }

    private Intent createIntent(BaseCommand command) {
        Intent i = new Intent(mContext, CommandExecutorService.class);
        i.setAction(CommandExecutorService.ACTION_EXECUTE_COMMAND);
        i.putExtra(CommandExecutorService.EXTRA_COMMAND, command);

        return i;
    }

    private void startService(Intent i) {
        mContext.startService(i);
    }
}
