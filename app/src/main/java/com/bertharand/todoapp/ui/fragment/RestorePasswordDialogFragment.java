package com.bertharand.todoapp.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;

public class RestorePasswordDialogFragment extends DialogFragment {
    private EditText mEmailEditText;

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_restore_password, null);

        mEmailEditText = (EditText) rootView.findViewById(R.id.restore_email_edit_text);
        
        builder.setView(rootView)
                .setTitle(R.string.dialog_restore_title)
                .setPositiveButton(R.string.positive_button_restore, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        restorePassword();
                        dismiss();
                    }
                });

        return builder.create();
    }

    private void restorePassword() {
        String email = mEmailEditText.getText().toString();
        if(isValidEmail(email)){
            ToDoApiServiceHelper.getInstance(getContext()).restorePassword(email);
        } else{
            mEmailEditText.setError(getString(R.string.invalid_email_error));
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
