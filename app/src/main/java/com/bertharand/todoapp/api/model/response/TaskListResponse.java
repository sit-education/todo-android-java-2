package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class TaskListResponse {
    @SerializedName("data")
    private TaskListContainer mTaskListContainer;

    public final TaskListContainer getTaskListContainer() {
        return mTaskListContainer;
    }

    public final void setTaskListContainer(TaskListContainer taskListContainer) {
        mTaskListContainer = taskListContainer;
    }
}
