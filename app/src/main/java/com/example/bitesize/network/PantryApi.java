package com.example.bitesize.network;

import com.example.bitesize.models.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

public interface PantryApi {

    @GET("api/pantry/user/{userId}")
    Call<List<Ingredient>> getPantryByUser(
            @Path("userId") int userId
    );

    @POST("api/pantry")
    Call<Ingredient> addPantryItem(
            @Body Ingredient ingredient
    );

    @PUT("api/pantry/{id}")
    Call<Ingredient> updatePantryItem(
            @Path("id") int pantryItemId,
            @Body Ingredient ingredient
    );

    @DELETE("api/pantry/{id}")
    Call<Void> deletePantryItem(
            @Path("id") int pantryItemId
    );
}