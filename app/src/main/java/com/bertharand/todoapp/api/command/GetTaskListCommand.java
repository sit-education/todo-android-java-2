package com.bertharand.todoapp.api.command;

import android.os.Parcel;

import com.bertharand.todoapp.api.ToDoApiService;
import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.TaskListResponse;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.utils.ErrorUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GetTaskListCommand extends BaseCommand implements Callback<TaskListResponse> {
    private String mToken;

    public static final Creator<GetTaskListCommand> CREATOR = new Creator<GetTaskListCommand>() {
        public GetTaskListCommand createFromParcel(Parcel in) {
            return new GetTaskListCommand(in);
        }

        public GetTaskListCommand[] newArray(int size) {
            return new GetTaskListCommand[size];
        }
    };

    public GetTaskListCommand(String token) {
        mToken = token;
    }

    public GetTaskListCommand(Parcel in) {
        mToken = in.readString();
    }

    @Override
    protected final void doExecute() {
        Call<TaskListResponse> taskListCall = ToDoApiService.getInstance().getTaskList(mToken);

        taskListCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<TaskListResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(response.body().getTaskListContainer().getTaskList());
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
    }
}
