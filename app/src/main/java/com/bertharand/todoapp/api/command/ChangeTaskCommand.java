package com.bertharand.todoapp.api.command;

import android.os.Parcel;

import com.bertharand.todoapp.api.ToDoApiService;
import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.BaseResponse;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.TaskChangedEvent;
import com.bertharand.todoapp.utils.ErrorUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ChangeTaskCommand extends BaseCommand implements Callback<BaseResponse> {
    private String mToken;
    private long mId;
    private String mTitle;
    private String mDescription;

    public static final Creator<ChangeTaskCommand> CREATOR = new Creator<ChangeTaskCommand>() {
        public ChangeTaskCommand createFromParcel(Parcel in) {
            return new ChangeTaskCommand(in);
        }

        public ChangeTaskCommand[] newArray(int size) {
            return new ChangeTaskCommand[size];
        }
    };

    public ChangeTaskCommand(String token, long id, String title, String description) {
        mToken = token;
        mId = id;
        mTitle = title;
        mDescription = description;
    }

    public ChangeTaskCommand(Parcel in) {
        mToken = in.readString();
        mId = in.readLong();
        mTitle = in.readString();
        mDescription = in.readString();
    }

    @Override
    protected final void doExecute() {
        Call<BaseResponse> changeTaskCall
                = ToDoApiService.getInstance().changeTask(mToken, mId, mTitle, mDescription);

        changeTaskCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(new TaskChangedEvent());
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
        dest.writeString(mTitle);
        dest.writeString(mDescription);
    }
}
