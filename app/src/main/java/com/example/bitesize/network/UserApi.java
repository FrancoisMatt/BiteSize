package com.example.bitesize.network;

import com.example.bitesize.models.LoginRequest;
import com.example.bitesize.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    // Login
    @POST("api/users/login")
    Call<User> login(
            @Body LoginRequest loginRequest
    );


    // Get logged-in user's profile
    @GET("api/users/{id}")
    Call<User> getUserById(
            @Path("id") int userId
    );


    // Update profile
    @PUT("api/users/{id}")
    Call<User> updateUser(
            @Path("id") int userId,
            @Body User user
    );
}