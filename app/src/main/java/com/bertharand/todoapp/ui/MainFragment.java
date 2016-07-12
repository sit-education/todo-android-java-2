package com.bertharand.todoapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.api.model.response.Task;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.TaskAddedEvent;

import java.util.List;

public class MainFragment extends BaseFragment implements TasksAdapter.Listener{
    private TasksAdapter mTasksAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog mProgressDialog;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeScreen();

        showProgressDialog();
        getTaskList();
    }

    private void initializeScreen(){
        if(getView() != null) {
            Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler);
            mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.refresh);
            FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);

            toolbar.setTitle(R.string.activity_tasks_title);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dialog = new AddTaskDialogFragment();
                    dialog.show(getFragmentManager(), "AddListDialogFragment");
                }
            });

            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mSwipeRefreshLayout.setRefreshing(true);
                    getTaskList();
                }
            });


            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            mTasksAdapter = new TasksAdapter(getContext());
            recyclerView.setAdapter(mTasksAdapter);
            mTasksAdapter.setOnClickListener(this);
        }
    }

    public void onEvent(List<Task> mTaskList){
        hideProgressDialog();
        hideSwipeRefreshLayout();
        mTasksAdapter.swapData(mTaskList);
    }

    public void onEvent(TaskAddedEvent taskAddedEvent){
        showSnackBar(getString(R.string.new_task_message));
        getTaskList();
    }

    public void onEvent(ApiErrorEvent apiErrorEvent){
        hideProgressDialog();
        hideSwipeRefreshLayout();
        showSnackBar(apiErrorEvent.getErrorMessage());
    }

    public void onEvent(NetworkErrorEvent networkErrorEvent){
        hideProgressDialog();
        hideSwipeRefreshLayout();
        showSnackBar(getString(R.string.error_connection_failed));
    }

    private void getTaskList(){
        ToDoApiServiceHelper.getInstance(getContext()).getTaskList();
    }

    private void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(getContext(), null,
                getResources().getString(R.string.loading_dialog_message), true, true);
    }

    private void showSnackBar(String message){
        if(getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void hideSwipeRefreshLayout(){
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void openTaskDetails(long taskId, String taskTitle, String taskDescription) {
        //TODO
    }
}
