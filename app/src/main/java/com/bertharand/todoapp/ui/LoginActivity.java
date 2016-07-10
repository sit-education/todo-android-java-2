package com.bertharand.todoapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.api.model.response.UserData;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.LoginSuccessEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.utils.AppSettings;

public class LoginActivity extends BaseActivity {
    private String mUsernameEditTextError;
    private String mPasswordEditTextError;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private String mProgressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.login_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);

        mProgressMessage = getResources().getString(R.string.progress_dialog_message);
        mUsernameEditTextError = getResources().getString(R.string.username_edittext_error);
        mPasswordEditTextError = getResources().getString(R.string.password_edittext_error);
    }

    public void attemptToLogin(View view) {
        String login = mUsernameEditText.getText().toString().replaceAll("\\s+","");
        String password = mPasswordEditText.getText().toString().replaceAll("\\s+","");

        if (login.isEmpty()) {
            mUsernameEditText.setError(mUsernameEditTextError);
        } else if (password.isEmpty()) {
            mPasswordEditText.setError(mPasswordEditTextError);
        } else {
            login(login, password);
        }
    }

    private void login(String login, String password){
        showProgressDialog(mProgressMessage);
        ToDoApiServiceHelper.getInstance(this).login(login, password);
    }

    public void onEvent(LoginSuccessEvent loginSuccessEvent){
        hideProgressDialog();
        saveUserData(loginSuccessEvent.getUserData());
        startToDoListActivity();
    }

    private void saveUserData(UserData userData){
        AppSettings.setToken(this, userData.getTokenKey());
        AppSettings.setTokenExpired(this, userData.getTokenExpired());
    }

    private void startToDoListActivity(){
        //TODO
    }

    public void onEvent(ApiErrorEvent apiErrorEvent){
        hideProgressDialog();
        showErrorMessage(apiErrorEvent.getErrorMessage());
    }

    public void onEvent(NetworkErrorEvent networkErrorEvent){
        hideProgressDialog();
        showErrorMessage(getString(R.string.error_connection_failed));
    }

    private void showErrorMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.login_alert_title)
                .setMessage(message)
                .setPositiveButton(R.string.login_alert_ok_button_title,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .show();
    }

    public void startSignUpActivity(View view) {
        //TODO
    }
}
