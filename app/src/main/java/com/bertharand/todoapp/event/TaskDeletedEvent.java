package com.bertharand.todoapp.event;

public class TaskDeletedEvent {
    private long mTaskId;

    public TaskDeletedEvent(long taskId) {
        mTaskId = taskId;
    }

    public final long getTaskId() {
        return mTaskId;
    }

    public final void setTaskId(long taskId) {
        mTaskId = taskId;
    }
}
