package com.bertharand.todoapp.api.command;

import android.content.Context;
import android.os.Parcel;

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

public class SignUpCommand extends BaseCommand implements Callback<SignResponse> {
    private final String mEmail;
    private final String mLogin;
    private final String mFirstName;
    private final String mLastName;
    private final String mPassword;
    private final String mConfirmPassword;

    public static final Creator<SignUpCommand> CREATOR = new Creator<SignUpCommand>() {
        public SignUpCommand createFromParcel(Parcel in) {
            return new SignUpCommand(in);
        }

        public SignUpCommand[] newArray(int size) {
            return new SignUpCommand[size];
        }
    };

    public SignUpCommand(String email, String login, String firstName,
                         String lastName, String password, String confirmPassword) {
        mEmail = email;
        mLogin = login;
        mFirstName = firstName;
        mLastName = lastName;
        mPassword = password;
        mConfirmPassword = confirmPassword;
    }

    private SignUpCommand(Parcel in) {
        mEmail = in.readString();
        mLogin = in.readString();
        mFirstName = in.readString();
        mLastName = in.readString();
        mPassword = in.readString();
        mConfirmPassword = in.readString();
    }

    @Override
    protected final void doExecute(Context context) {
        Call<SignResponse> loginCall = ToDoApiService.getInstance()
                .signUp(mEmail, mLogin, mFirstName, mLastName, mPassword, mConfirmPassword);

        loginCall.enqueue(this);
    }

    @Override
    public final void onResponse(Response<SignResponse> response, Retrofit retrofit) {
        if(response.isSuccess() && response.body()!= null) {
            notifySubscribers(new SignSuccessEvent());
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
        dest.writeString(mLogin);
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mPassword);
        dest.writeString(mConfirmPassword);
    }
}
