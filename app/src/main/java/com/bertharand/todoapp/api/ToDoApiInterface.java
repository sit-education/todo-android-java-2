package com.bertharand.todoapp.api;

import com.bertharand.todoapp.api.model.request.LoginRequest;
import com.bertharand.todoapp.api.model.request.SignUpRequest;
import com.bertharand.todoapp.api.model.response.SignResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ToDoApiInterface {
    @POST("login")
    Call<SignResponse> login(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignResponse> signUp(@Body SignUpRequest signUpRequest);
}
