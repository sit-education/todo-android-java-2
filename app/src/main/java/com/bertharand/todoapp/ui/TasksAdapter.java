package com.bertharand.todoapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.api.model.response.Task;

import java.util.List;
import java.util.Random;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    private List<Task> mTaskList;
    private Listener mListener;
    private Context mContext;

    private View.OnClickListener mTaskOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof Task) {
                getTaskAndStartActivity((Task) tag);
            }
        }
    };

    public TasksAdapter(Context context) {
        mContext = context;
    }

    public void swapData(List<Task> tasks) {
        mTaskList = tasks;
        notifyDataSetChanged();
    }

    public final void setOnClickListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mTaskList != null) {
            count = mTaskList.size();
        }
        return count;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = mTaskList.get(position);

        String[] allColors = mContext.getResources().getStringArray(R.array.colors);
        ((GradientDrawable)holder.mCircleView.getBackground())
                .setColor(Color.parseColor(allColors[new Random().nextInt(allColors.length)]));

        holder.mTaskTitleTextView.setText(task.getTitle());
        holder.mTaskDescriptionTextView.setText(task.getDescription());

        holder.itemView.setTag(task);
        holder.itemView.setOnClickListener(mTaskOnClickListener);
    }

    private void getTaskAndStartActivity(Task tag) {
        if (mListener != null) {
            mListener.openTaskDetails(tag.getId(), tag.getTitle(), tag.getDescription());
        }
    }

    public interface Listener {
        void openTaskDetails(long taskId, String taskTitle, String taskDescription);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView mTaskTitleTextView;
        private TextView mTaskDescriptionTextView;
        private View mCircleView;

        public TaskViewHolder(View v) {
            super(v);

            mCircleView = v.findViewById(R.id.circle_view);
            mTaskTitleTextView = (TextView) v.findViewById(R.id.task_title_textview);
            mTaskDescriptionTextView = (TextView) v.findViewById(R.id.task_description_textview);
        }
    }
}
