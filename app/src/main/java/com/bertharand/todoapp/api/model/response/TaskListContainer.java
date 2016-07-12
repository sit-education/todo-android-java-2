package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskListContainer {
    @SerializedName("todoData")
    private List<Task> mTaskList;

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public final void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }
}
