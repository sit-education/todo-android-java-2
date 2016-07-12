package com.bertharand.todoapp.api.model.response;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("id")
    private long mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;

    public Task(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public final void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public final void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public final void setDescription(String description) {
        mDescription = description;
    }
}
