package com.bertharand.todoapp.api.command;

import android.os.Parcel;
import android.os.Parcelable;

import com.bertharand.todoapp.api.ToDoApiService;
import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.SignResponse;
import com.bertharand.todoapp.event.ApiErrorEvent;
import com.bertharand.todoapp.event.NetworkErrorEvent;
import com.bertharand.todoapp.event.SignSuccessEvent;
import com.bertharand.todoapp.utils.ErrorUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginCommand extends BaseCommand implements Callback<SignResponse> {
    private final String mLogin;
    private final String mPassword;

    public static final Parcelable.Creator<LoginCommand> CREATOR = new Parcelable.Creator<LoginCommand>() {
        public LoginCommand createFromParcel(Parcel in) {
            return new LoginCommand(in);
        }

        public LoginCommand[] newArray(int size) {
            return new LoginCommand[size];
        }
    };

    public LoginCommand(String login, String password) {
        mLogin = login;
        mPassword = password;
    }

    private LoginCommand(Parcel in) {
        mLogin = in.readString();
        mPassword = in.readString();
    }

    @Override
    protected final void doExecute() {
        Call<SignResponse> loginCall = ToDoApiService.getInstance().login(mLogin, mPassword);

        loginCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<SignResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(new SignSuccessEvent(response.body().getUser()));
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
        dest.writeString(mLogin);
        dest.writeString(mPassword);
    }
}
