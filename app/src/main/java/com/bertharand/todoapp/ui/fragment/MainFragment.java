package com.bertharand.todoapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.ToDoApiServiceHelper;
import com.bertharand.todoapp.api.model.response.Task;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.TaskAddedEvent;
import com.bertharand.todoapp.event.TaskChangedEvent;
import com.bertharand.todoapp.event.TaskDeletedEvent;
import com.bertharand.todoapp.ui.activity.LoginActivity;
import com.bertharand.todoapp.utils.AppSettings;

import java.util.List;

public class MainFragment extends BaseFragment implements
        TasksAdapter.Listener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    public static final String TASK_ID_EXTRA = "task_id";
    public static final String TASK_TITLE_EXTRA = "task_title";
    public static final String TASK_DESCRIPTION_EXTRA = "task_description";

    private TasksAdapter mTasksAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog mProgressDialog;
    private TextView mEmptyTextView;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        initializeScreen();

        showProgressDialog();
        getTaskList();
    }

    @Override
    public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                showConfirmDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.dialog_logout_message)
                .setPositiveButton(R.string.dialog_ok_button_title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
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

    private void logout() {
        AppSettings.clearSettings(getContext());

        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void initializeScreen(){
        View view = getView();
        if(view != null) {
            setupToolbar(view);
            setupSwipeRefreshLayout(getView());
            setupRecyclerView(view);

            mEmptyTextView = (TextView) view.findViewById(R.id.empty_state_text_view);
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(this);
        }
    }

    private void setupToolbar(View view) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity currentActivity = (AppCompatActivity) getActivity();
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.activity_todos_title);
            currentActivity.setSupportActionBar(toolbar);
        }
    }

    private void setupSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mTasksAdapter = new TasksAdapter();
        recyclerView.setAdapter(mTasksAdapter);
        mTasksAdapter.setOnClickListener(this);
    }

    public final void onEvent(List<Task> mTaskList){
        hideProgressDialog();
        hideSwipeRefreshLayout();
        mTasksAdapter.swapData(mTaskList);
        hideShowEmptyState(mTaskList.isEmpty());
    }

    public final void onEvent(TaskAddedEvent taskAddedEvent){
        showSnackBar(getString(R.string.new_todo_message));
        getTaskList();
    }

    public final void onEvent(TaskChangedEvent taskChangedEvent){
        showSnackBar(getString(R.string.todo_edited_message));
        getTaskList();
    }

    public final void onEvent(TaskDeletedEvent taskDeletedEvent){
        showSnackBar(getString(R.string.todo_delete_message));
        mTasksAdapter.deleteTaskById(taskDeletedEvent.getTaskId());
        hideShowEmptyState(mTasksAdapter.getItemCount() == 0);
    }

    public final void onEvent(ApiErrorEvent apiErrorEvent){
        hideProgressDialog();
        hideSwipeRefreshLayout();
        showSnackBar(apiErrorEvent.getErrorMessage());
    }

    public final void onEvent(NetworkErrorEvent networkErrorEvent){
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

    private void hideShowEmptyState(boolean isShow){
        mEmptyTextView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public final void openTaskDetails(long taskId, String taskTitle, String taskDescription) {
        if(getActivity() instanceof OnTaskClickListener){
            ((OnTaskClickListener) getActivity()).onClick(taskId, taskTitle, taskDescription);
        }
    }

    @Override
    public void onClick(View view) {
        DialogFragment dialog = new AddTaskDialogFragment();
        dialog.show(getFragmentManager(), AddTaskDialogFragment.TAG);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getTaskList();
    }

    public interface OnTaskClickListener{
        void onClick(long taskId, String taskTitle, String taskDescription);
    }
}
