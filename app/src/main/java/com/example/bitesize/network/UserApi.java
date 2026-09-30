package com.example.bitesize.network;

import com.example.bitesize.models.User;
import com.example.bitesize.models.LoginRequest;
import com.example.bitesize.models.ResetPasswordRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    @GET("api/users/{id}")
    Call<User> getUser(
            @Path("id") int userId
    );


    @PUT("api/users/{id}")
    Call<User> updateUser(
            @Path("id") int userId,
            @Body User user
    );

    @POST("api/users/login")
    Call<User> login(
            @Body LoginRequest loginRequest
    );

    @POST("api/users")
    Call<User> createUser(
            @Body User user
    );

    @POST("api/users/reset-password")
    Call<User> resetPassword(
            @Body ResetPasswordRequest request
    );
}