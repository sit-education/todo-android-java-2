package com.bertharand.todoapp.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.bertharand.todoapp.R;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    public final void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public final void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    protected final void showProgressDialog(String message) {
        mProgressDialog = ProgressDialog.show(this, null, message, true);
    }

    protected final void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected String getTextFromEditText(EditText editText){
        return editText.getText().toString().replaceAll("\\s+","");
    }

    protected final void showMessage(int titleId, String message){
        showMessage(titleId, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    protected final void showMessage(int titleId, String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok_button_title, onClickListener)
                .show();
    }
}
