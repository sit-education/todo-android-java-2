package com.bertharand.todoapp.api.command;

import android.os.Parcel;

import com.bertharand.todoapp.api.ToDoApiService;
import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.BaseResponse;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.TaskDeletedEvent;
import com.bertharand.todoapp.utils.ErrorUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DeleteTaskCommand extends BaseCommand implements Callback<BaseResponse> {
    private long mId;
    private String mToken;

    public static final Creator<DeleteTaskCommand> CREATOR = new Creator<DeleteTaskCommand>() {
        public DeleteTaskCommand createFromParcel(Parcel in) {
            return new DeleteTaskCommand(in);
        }

        public DeleteTaskCommand[] newArray(int size) {
            return new DeleteTaskCommand[size];
        }
    };

    public DeleteTaskCommand(String token, long id) {
        mToken = token;
        mId = id;
    }

    public DeleteTaskCommand(Parcel in) {
        mToken = in.readString();
        mId = in.readLong();
    }

    @Override
    protected final void doExecute() {
        Call<BaseResponse> deleteTaskCall = ToDoApiService.getInstance().deleteTask(mToken, mId);

        deleteTaskCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(new TaskDeletedEvent(mId));
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
        dest.writeLong(mId);
    }
}
