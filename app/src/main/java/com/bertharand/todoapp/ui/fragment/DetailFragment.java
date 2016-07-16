package com.bertharand.todoapp.ui.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.ui.activity.MainActivity;

public class DetailFragment extends Fragment implements MainActivity.OnBackPressedListener{
    public static final String TAG = "Detail_Fragment";

    private long mTaskId;
    private String mTaskTitle;
    private String mTaskDescription;

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;

    public static DetailFragment newInstance(long taskId, String taskTitle, String taskDescription){
        DetailFragment taskDetailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong(MainFragment.TASK_ID_EXTRA, taskId);
        args.putString(MainFragment.TASK_TITLE_EXTRA, taskTitle);
        args.putString(MainFragment.TASK_DESCRIPTION_EXTRA, taskDescription);
        taskDetailFragment.setArguments(args);
        return taskDetailFragment;
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTaskId = getArguments().getLong(MainFragment.TASK_ID_EXTRA);
        mTaskTitle = getArguments().getString(MainFragment.TASK_TITLE_EXTRA);
        mTaskDescription = getArguments().getString(MainFragment.TASK_DESCRIPTION_EXTRA);

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        initializeScreen();
    }

    private void initializeScreen(){
        View view = getView();
        if(view != null) {
            setupToolbar(view);

            mTitleEditText = (EditText) view.findViewById(R.id.task_title_edit_text);
            mDescriptionEditText = (EditText) view.findViewById(R.id.task_description_edit_text);

            mTitleEditText.setText(mTaskTitle);
            mDescriptionEditText.setText(mTaskDescription);
        }
    }

    private void setupToolbar(View view) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity currentActivity = (AppCompatActivity) getActivity();
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            toolbar.setTitle("");

            currentActivity.setSupportActionBar(toolbar);
            ActionBar actionBar = currentActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        menu.removeItem(R.id.logout);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.delete:
                showDeleteDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog(){
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(R.string.dialog_ok_button_title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button_title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteTask(){
        ToDoApiServiceHelper.getInstance(getContext()).deleteTask(mTaskId);
        getActivity().onBackPressed();
    }

    @Override
    public final void onBackPressed() {
        saveTask();
        hideSoftKeyboard();
    }

    private void saveTask() {
        if(isTaskDataChanged()){
            ToDoApiServiceHelper.getInstance(getContext())
                    .changeTask(mTaskId,
                            mTitleEditText.getText().toString(),
                            mDescriptionEditText.getText().toString());
        }
    }

    private boolean isTaskDataChanged(){
        return !mTaskTitle.equals(mTitleEditText.getText().toString())
                || !mTaskDescription.equals(mDescriptionEditText.getText().toString());
    }

    private void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
