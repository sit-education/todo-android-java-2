package com.bertharand.todoapp.ui;

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

public class AddTaskDialogFragment extends DialogFragment {
    private EditText mTaskTitleEditText;
    private EditText mTaskDescriptionEditText;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_task, null);

        mTaskTitleEditText = (EditText) rootView.findViewById(R.id.task_title_edittext);
        mTaskDescriptionEditText = (EditText) rootView.findViewById(R.id.task_description_edittext);

        builder.setView(rootView)
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addTask();
                    }
                });

        return builder.create();
    }

    private void addTask(){
        String taskTitle = mTaskTitleEditText.getText().toString();
        String taskDescription = mTaskDescriptionEditText.getText().toString();
        ToDoApiServiceHelper.getInstance(getContext()).addTask(taskTitle, taskDescription);
    }
}
