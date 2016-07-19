package com.bertharand.todoapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.api.model.response.User;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.PasswordRestoreEvent;
import com.bertharand.todoapp.event.SignSuccessEvent;
import com.bertharand.todoapp.ui.fragment.RestorePasswordDialogFragment;
import com.bertharand.todoapp.utils.AppSettings;

public class LoginActivity extends BaseActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private String mProgressMessage;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.login_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);

        mProgressMessage = getString(R.string.login_progress_dialog_message);
    }

    public final void attemptToLogin(View view) {
        String login = getTextFromEditText(mUsernameEditText);
        String password = getTextFromEditText(mPasswordEditText);

        if (login.isEmpty() && password.isEmpty()) {
            showMessage(R.string.login_alert_title, getString(R.string.error_empty_edit_text));
        } else {
            login(login, password);
        }
    }

    private void login(String login, String password){
        showProgressDialog(mProgressMessage);
        ToDoApiServiceHelper.getInstance(this).login(login, password);
    }

    public void startSignUpActivity(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public final void restorePassword(View view) {
        DialogFragment dialog = new RestorePasswordDialogFragment();
        dialog.show(getSupportFragmentManager(), RestorePasswordDialogFragment.TAG);
    }

    public final void onEvent(SignSuccessEvent signSuccessEvent){
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

    public final void onEvent(PasswordRestoreEvent passwordRestoreEvent){
        showMessage(R.string.dialog_restore_title, getString(R.string.dialog_restore_message));
    }

    public final void onEvent(ApiErrorEvent apiErrorEvent){
        hideProgressDialog();
        showMessage(R.string.login_alert_title, apiErrorEvent.getErrorMessage());
    }

    public final void onEvent(NetworkErrorEvent networkErrorEvent){
        hideProgressDialog();
        showMessage(R.string.login_alert_title, getString(R.string.error_connection_failed));
    }
}
