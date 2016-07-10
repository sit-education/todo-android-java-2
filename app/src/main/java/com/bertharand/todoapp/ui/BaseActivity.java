package com.bertharand.todoapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import de.greenrobot.event.EventBus;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
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
}
