package com.bertharand.todoapp.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

public class BaseFragment extends Fragment{
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

    protected final void showSnackBar(String message){
        if(getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
