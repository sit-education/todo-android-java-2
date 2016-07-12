package com.bertharand.todoapp.api.command;

import android.os.Parcel;

import com.bertharand.todoapp.api.ToDoApiService;
import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.BaseResponse;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.TaskAddedEvent;
import com.bertharand.todoapp.utils.ErrorUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AddTaskCommand extends BaseCommand implements Callback<BaseResponse> {
    private String mTitle;
    private String mDescription;
    private String mToken;

    public static final Creator<AddTaskCommand> CREATOR = new Creator<AddTaskCommand>() {
        public AddTaskCommand createFromParcel(Parcel in) {
            return new AddTaskCommand(in);
        }

        public AddTaskCommand[] newArray(int size) {
            return new AddTaskCommand[size];
        }
    };

    public AddTaskCommand(String token, String title, String description) {
        mToken = token;
        mTitle = title;
        mDescription = description;
    }

    public AddTaskCommand(Parcel in) {
        mToken = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
    }

    @Override
    protected final void doExecute() {
        Call<BaseResponse> addTaskCall = ToDoApiService.getInstance().addTask(mToken, mTitle, mDescription);

        addTaskCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(new TaskAddedEvent());
        } else {
            ApiError error = ErrorUtils.parseError(response, retrofit);
            notifySubscribers(new ApiErrorEvent(error.getErrorMessage()));
        }
    }

    @Override
    public final void onFailure(Throwable t) {
        notifySubscribers(new NetworkErrorEvent());
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mToken);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
    }
}
