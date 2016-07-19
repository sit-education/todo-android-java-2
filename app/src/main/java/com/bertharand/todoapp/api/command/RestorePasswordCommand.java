package com.bertharand.todoapp.api.command;

import android.os.Parcel;

import com.bertharand.todoapp.api.ToDoApiService;
import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.BaseResponse;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.PasswordRestoreEvent;
import com.bertharand.todoapp.utils.ErrorUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RestorePasswordCommand extends BaseCommand implements Callback<BaseResponse> {
    private String mEmail;

    public static final Creator<RestorePasswordCommand> CREATOR = new Creator<RestorePasswordCommand>() {
        public RestorePasswordCommand createFromParcel(Parcel in) {
            return new RestorePasswordCommand(in);
        }

        public RestorePasswordCommand[] newArray(int size) {
            return new RestorePasswordCommand[size];
        }
    };

    public RestorePasswordCommand(String email) {
        mEmail = email;
    }

    public RestorePasswordCommand(Parcel in) {
        mEmail = in.readString();
    }

    @Override
    protected final void doExecute() {
        Call<BaseResponse> restorePasswordCall = ToDoApiService.getInstance().restorePassword(mEmail);

        restorePasswordCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(new PasswordRestoreEvent());
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
        dest.writeString(mEmail);
    }
}
