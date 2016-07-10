package com.bertharand.todoapp.utils;

import com.bertharand.todoapp.api.model.response.ApiError;
import com.bertharand.todoapp.api.model.response.BaseApiError;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response, Retrofit retrofit) {
        Converter<ResponseBody, BaseApiError> converter =
                retrofit.responseConverter(BaseApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody()).getErrorList().get(0);
        } catch (IOException | JsonSyntaxException e){
            error = new ApiError();
        }

        return error;
    }
}