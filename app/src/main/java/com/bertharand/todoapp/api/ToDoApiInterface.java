package com.bertharand.todoapp.api;

import com.bertharand.todoapp.api.model.request.LoginRequest;
import com.bertharand.todoapp.api.model.request.RestorePasswordData;
import com.bertharand.todoapp.api.model.request.SignUpRequest;
import com.bertharand.todoapp.api.model.response.BaseResponse;
import com.bertharand.todoapp.api.model.response.SignResponse;
import com.bertharand.todoapp.api.model.response.TaskListResponse;
import com.bertharand.todoapp.api.model.response.Task;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

interface ToDoApiInterface {
    @POST("login")
    Call<SignResponse> login(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignResponse> signUp(@Body SignUpRequest signUpRequest);

    @GET("items")
    Call<TaskListResponse> getTaskList(@Header(BaseToDoApiService.TOKEN_HEADER) String token);

    @POST("item")
    Call<BaseResponse> addTask(@Header(BaseToDoApiService.TOKEN_HEADER) String token,
                               @Body Task data);

    @POST("restorePassword")
    Call<BaseResponse> restorePassword(@Body RestorePasswordData data);

    @DELETE("item/{itemId}")
    Call<BaseResponse> deleteTask(@Header(BaseToDoApiService.TOKEN_HEADER) String token,
                                  @Path("itemId") long itemId);

    @PUT("item/{itemId}")
    Call<BaseResponse> changeTask(@Header(BaseToDoApiService.TOKEN_HEADER) String token,
                                  @Path("itemId") long itemId,
                                  @Body Task data);
}
