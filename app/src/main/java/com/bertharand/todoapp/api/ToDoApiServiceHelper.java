package com.bertharand.todoapp.api;

import android.content.Context;
import android.content.Intent;

import com.bertharand.todoapp.api.command.AddTaskCommand;
import com.bertharand.todoapp.api.command.BaseCommand;
import com.bertharand.todoapp.api.command.ChangeTaskCommand;
import com.bertharand.todoapp.api.command.DeleteTaskCommand;
import com.bertharand.todoapp.api.command.GetTaskListCommand;
import com.bertharand.todoapp.api.command.LoginCommand;
import com.bertharand.todoapp.api.command.RestorePasswordCommand;
import com.bertharand.todoapp.api.command.SignUpCommand;
import com.bertharand.todoapp.utils.AppSettings;

public final class ToDoApiServiceHelper {
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

    public void login(String login, String password) {
        Intent i = createIntent(new LoginCommand(login, password));
        startService(i);
    }

    public void signUp(String email, String login, String firstName,
                             String lastName, String password, String confirmPassword){
        Intent i = createIntent(
                new SignUpCommand(email, login, firstName, lastName, password, confirmPassword));
        startService(i);
    }

    public void getTaskList(){
        Intent i = createIntent(new GetTaskListCommand(AppSettings.getToken(mContext)));
        startService(i);
    }

    public void addTask(String title, String description){
        Intent i = createIntent(new AddTaskCommand(AppSettings.getToken(mContext), title, description));
        startService(i);
    }

    public void changeTask(long id, String title, String description){
        Intent i = createIntent(new ChangeTaskCommand(
                AppSettings.getToken(mContext), id, title, description));
        startService(i);
    }

    public void deleteTask(long id){
        Intent i = createIntent(new DeleteTaskCommand(AppSettings.getToken(mContext), id));
        startService(i);
    }

    public void restorePassword(String email){
        Intent i = createIntent(new RestorePasswordCommand(email));
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
