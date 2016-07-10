package com.bertharand.todoapp.api;

import com.bertharand.todoapp.api.model.request.LoginRequest;
import com.bertharand.todoapp.api.model.response.LoginResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ToDoApiInterface {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
