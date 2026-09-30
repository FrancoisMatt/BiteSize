package com.example.bitesize.network;

import com.example.bitesize.models.Recipe;
import com.example.bitesize.models.RecipeIngredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeApi {

    // =====================================================
    // GET ALL RECIPES
    // =====================================================

    @GET("api/recipes")
    Call<List<Recipe>> getAllRecipes();


    // =====================================================
    // GET RECIPE BY ID
    // =====================================================

    @GET("api/recipes/{id}")
    Call<Recipe> getRecipe(
            @Path("id") int recipeId
    );


    // =====================================================
    // GET SUGGESTED RECIPES
    // =====================================================

    @GET("api/recipes/suggested/{userId}")
    Call<List<Recipe>> getSuggestedRecipes(
            @Path("userId") int userId
    );

    @GET("api/recipes/{recipeId}/ingredients")
    Call<List<RecipeIngredient>> getRecipeIngredients(
            @Path("recipeId") int recipeId
    );


}