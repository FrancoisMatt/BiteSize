package com.example.bitesize.network;

import com.example.bitesize.models.IngredientOption;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientApi {

    @GET("api/ingredients")
    Call<List<IngredientOption>> getAllIngredients();
}