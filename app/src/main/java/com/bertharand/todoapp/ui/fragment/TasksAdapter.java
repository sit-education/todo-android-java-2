package com.bertharand.todoapp.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.model.response.Task;

import java.util.List;
import java.util.ListIterator;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>
        implements View.OnClickListener{
    private List<Task> mTaskList;
    private Listener mListener;

    public final void swapData(List<Task> tasks) {
        mTaskList = tasks;
        notifyDataSetChanged();
    }

    public final void deleteTaskById(long id){
        ListIterator<Task> iterator = mTaskList.listIterator();
        while(iterator.hasNext()) {
            if (iterator.next().getId() == id){
                notifyItemRemoved(iterator.previousIndex());
                iterator.remove();
                break;
            }
        }
    }

    public final void setOnClickListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public final TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public final int getItemCount() {
        int count = 0;
        if (mTaskList != null) {
            count = mTaskList.size();
        }
        return count;
    }

    @Override
    public final void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = mTaskList.get(position);

        holder.mTaskTitleTextView.setText(task.getTitle());
        holder.mTaskDescriptionTextView.setText(task.getDescription());

        holder.itemView.setTag(task);
        holder.itemView.setOnClickListener(this);
    }

    private void getTaskAndStartActivity(Task tag) {
        if (mListener != null) {
            mListener.openTaskDetails(tag.getId(), tag.getTitle(), tag.getDescription());
        }
    }

    @Override
    public final void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Task) {
            getTaskAndStartActivity((Task) tag);
        }
    }

    public interface Listener {
        void openTaskDetails(long taskId, String taskTitle, String taskDescription);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        private TextView mTaskTitleTextView;
        private TextView mTaskDescriptionTextView;

        public TaskViewHolder(View v) {
            super(v);

            mTaskTitleTextView = (TextView) v.findViewById(R.id.task_title_text_view);
            mTaskDescriptionTextView = (TextView) v.findViewById(R.id.task_description_text_view);
        }
    }
}
