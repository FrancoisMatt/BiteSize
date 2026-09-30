package com.example.bitesize.network;

import com.example.bitesize.models.LoginRequest;
import com.example.bitesize.models.ResetPasswordRequest;
import com.example.bitesize.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    // =====================================================
    // LOGIN
    // =====================================================

    @POST("api/users/login")
    Call<User> login(
            @Body LoginRequest loginRequest
    );


    // =====================================================
    // CREATE USER
    // =====================================================

    @POST("api/users")
    Call<User> createUser(
            @Body User user
    );


    // =====================================================
    // GET USER PROFILE
    // =====================================================

    @GET("api/users/{id}")
    Call<User> getUserById(
            @Path("id") int userId
    );


    // =====================================================
    // UPDATE USER PROFILE
    // =====================================================

    @PUT("api/users/{id}")
    Call<User> updateUser(
            @Path("id") int userId,
            @Body User user
    );


    // =====================================================
    // RESET PASSWORD
    // =====================================================

    @POST("api/users/reset-password")
    Call<User> resetPassword(
            @Body ResetPasswordRequest request
    );
}