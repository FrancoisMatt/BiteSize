package com.example.bitesize.network;

import com.example.bitesize.models.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PantryApi {

    @GET("api/pantry/user/{userId}")
    Call<List<Ingredient>> getPantryByUser(
            @Path("userId") int userId
    );
}