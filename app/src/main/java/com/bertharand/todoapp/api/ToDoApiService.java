package com.bertharand.todoapp.api;

import com.bertharand.todoapp.api.model.request.LoginRequest;
import com.bertharand.todoapp.api.model.response.LoginResponse;

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

    public Call<LoginResponse> login(String login, String password) {
        return getToDoApiInterface().login(new LoginRequest(login, password));
    }
}
