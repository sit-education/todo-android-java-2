package com.bertharand.todoapp.api;

import com.bertharand.todoapp.api.model.request.LoginRequest;
import com.bertharand.todoapp.api.model.request.SignUpRequest;
import com.bertharand.todoapp.api.model.response.SignResponse;

import retrofit.Call;

public class ToDoApiService extends BaseToDoApiService {
    private static volatile ToDoApiService mInstance;

    private ToDoApiService() {
    }

    public static ToDoApiService getInstance() {
        if (mInstance == null) {
            synchronized (ToDoApiService.class) {
                if (mInstance == null) {
                    mInstance = new ToDoApiService();
                }
            }
        }
        return mInstance;
    }

    public Call<SignResponse> login(String login, String password) {
        return getToDoApiInterface().login(new LoginRequest(login, password));
    }

    public Call<SignResponse> signUp(String email, String login, String firstName,
                                     String lastName, String password, String confirmPassword) {
        return getToDoApiInterface().signUp(
                new SignUpRequest(email, login, firstName, lastName, password, confirmPassword));
    }
}
