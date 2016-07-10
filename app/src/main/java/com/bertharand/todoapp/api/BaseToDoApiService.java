package com.bertharand.todoapp.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public abstract class BaseToDoApiService {
    private static final String BASE_URL = "https://sit-todo-test.appspot.com/api/v1/";

    final ToDoApiInterface getToDoApiInterface() {
        return getRestAdapter().create(ToDoApiInterface.class);
    }

    private Retrofit getRestAdapter() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
