package com.bertharand.todoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.api.model.response.User;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.SignSuccessEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.utils.AppSettings;

public class LoginActivity extends BaseActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private String mProgressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.login_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);

        mProgressMessage = getString(R.string.login_progress_dialog_message);
    }

    public void attemptToLogin(View view) {
        String login = mUsernameEditText.getText().toString().replaceAll("\\s+","");
        String password = mPasswordEditText.getText().toString().replaceAll("\\s+","");

        if (login.isEmpty() && password.isEmpty()) {
            showMessage(R.string.login_alert_title, getString(R.string.error_empty_edittext));
        } else {
            login(login, password);
        }
    }

    private void login(String login, String password){
        showProgressDialog(mProgressMessage);
        ToDoApiServiceHelper.getInstance(this).login(login, password);
    }

    public void onEvent(SignSuccessEvent signSuccessEvent){
        hideProgressDialog();
        saveUserData(signSuccessEvent.getUser());
        startToDoListActivity();
    }

    private void saveUserData(User user){
        AppSettings.setToken(this, user.getTokenKey());
        AppSettings.setTokenExpired(this, user.getTokenExpired());
    }

    private void startToDoListActivity(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    public void onEvent(ApiErrorEvent apiErrorEvent){
        hideProgressDialog();
        showMessage(R.string.login_alert_title, apiErrorEvent.getErrorMessage());
    }

    public void onEvent(NetworkErrorEvent networkErrorEvent){
        hideProgressDialog();
        showMessage(R.string.login_alert_title, getString(R.string.error_connection_failed));
    }

    public void startSignUpActivity(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
