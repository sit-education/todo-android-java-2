package com.bertharand.todoapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.SignSuccessEvent;

public class SignUpActivity extends BaseActivity {
    private EditText mLoginEditText;
    private EditText mEmailEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;

    private String mProgressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmailEditText = (EditText) findViewById(R.id.email_edittext);
        mLoginEditText = (EditText) findViewById(R.id.login_edittext);
        mFirstNameEditText = (EditText) findViewById(R.id.first_name_edittext);
        mLastNameEditText = (EditText) findViewById(R.id.last_name_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.confirm_password_edittext);

        mProgressMessage = getResources().getString(R.string.sign_up_progress_dialog_message);
    }

    public void attemptToSignUp(View view) {
        String email = getTextFromEditText(mEmailEditText);
        String firstName = getTextFromEditText(mFirstNameEditText);
        String lastName = getTextFromEditText(mLastNameEditText);
        String login = getTextFromEditText(mLoginEditText);
        String password = getTextFromEditText(mPasswordEditText);
        String confirmPassword = getTextFromEditText(mConfirmPasswordEditText);

        if(!isValidEmail(email) && TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName)
                && TextUtils.isEmpty(login)){
            showMessage(R.string.sign_up_error, getString(R.string.error_empty_edittext));
        } else if(!isPasswordsEqual(password, confirmPassword)){
            showMessage(R.string.sign_up_error, getString(R.string.error_passwords_equal));
        } else {
            signUp(email, login, firstName, lastName, password, confirmPassword);
        }
    }

    private String getTextFromEditText(EditText editText){
        return editText.getText().toString().replaceAll("\\s+","");
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isPasswordsEqual(String password, String confirmPassword){
        boolean isEqual = false;
        if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){
            isEqual = password.equals(confirmPassword);
        }
        return isEqual;
    }

    private void signUp(String email, String login, String firstName,
                        String lastName, String password, String confirmPassword){
        showProgressDialog(mProgressMessage);
        ToDoApiServiceHelper.getInstance(this)
                .signUp(email, login, firstName, lastName, password, confirmPassword);
    }

    public void onEvent(SignSuccessEvent signSuccessEvent){
        hideProgressDialog();
        showMessage(R.string.sign_up_title, getString(R.string.email_confirmation_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
    }

    public void onEvent(ApiErrorEvent apiErrorEvent){
        hideProgressDialog();
        showMessage(R.string.sign_up_error, apiErrorEvent.getErrorMessage());
    }

    public void onEvent(NetworkErrorEvent networkErrorEvent){
        hideProgressDialog();
        showMessage(R.string.sign_up_error, getString(R.string.error_connection_failed));
    }
}
