package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitesize.R;
import com.example.bitesize.adapters.RecipeAdapter;
import com.example.bitesize.models.Recipe;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.RecipeApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesPage extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtSearchRecipe;
    private TextView txtRecipeResults;
    private RecyclerView recyclerRecipes;

    private RecipeAdapter recipeAdapter;

    private RecipeApi recipeApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipespage);


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtSearchRecipe =
                findViewById(R.id.txtSearchRecipe);

        txtRecipeResults =
                findViewById(R.id.txtRecipeResults);

        recyclerRecipes =
                findViewById(R.id.recyclerRecipes);


        // =====================================================
        // RECYCLER VIEW SETUP
        // =====================================================

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );


        recipeAdapter =
                new RecipeAdapter(
                        RecipesPage.this,
                        new ArrayList<>()
                );


        recyclerRecipes.setAdapter(
                recipeAdapter
        );


        // =====================================================
        // API SETUP
        // =====================================================

        recipeApi =
                ApiClient
                        .getClient()
                        .create(RecipeApi.class);


        // =====================================================
        // LOAD ALL RECIPES
        // =====================================================

        loadRecipes();


        // =====================================================
        // SEARCH RECIPES
        // =====================================================

        txtSearchRecipe.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }


                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        String searchText =
                                s.toString().trim();


                        recipeAdapter.filter(
                                searchText
                        );


                        if (searchText.isEmpty()) {

                            txtRecipeResults.setText(
                                    "All Recipes"
                            );

                        } else {

                            txtRecipeResults.setText(
                                    "Search Results"
                            );
                        }
                    }


                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );
    }


    // =====================================================
    // LOAD RECIPES FROM API
    // =====================================================

    private void loadRecipes() {

        Call<List<Recipe>> call =
                recipeApi.getAllRecipes();


        call.enqueue(
                new Callback<List<Recipe>>() {

                    @Override
                    public void onResponse(
                            Call<List<Recipe>> call,
                            Response<List<Recipe>> response) {


                        if (response.isSuccessful()
                                && response.body() != null) {


                            List<Recipe> recipes =
                                    response.body();


                            recipeAdapter.setRecipes(
                                    recipes
                            );


                            if (recipes.isEmpty()) {

                                txtRecipeResults.setText(
                                        "No recipes available"
                                );

                            } else {

                                txtRecipeResults.setText(
                                        "All Recipes ("
                                                + recipes.size()
                                                + ")"
                                );
                            }


                        } else {

                            Toast.makeText(
                                    RecipesPage.this,
                                    "Unable to load recipes",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<List<Recipe>> call,
                            Throwable throwable) {


                        Toast.makeText(
                                RecipesPage.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}