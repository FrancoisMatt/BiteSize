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
import com.example.bitesize.adapters.SuggestedRecipeAdapter;
import com.example.bitesize.models.Recipe;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.RecipeApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestedRecipesPage extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtSearchSuggestedRecipe;
    private TextView txtSuggestedResults;
    private RecyclerView recyclerSuggestedRecipes;

    private SuggestedRecipeAdapter adapter;

    private RecipeApi recipeApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.suggestedrecipepage);


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtSearchSuggestedRecipe =
                findViewById(R.id.txtSearchSuggestedRecipe);

        txtSuggestedResults =
                findViewById(R.id.txtSuggestedResults);

        recyclerSuggestedRecipes =
                findViewById(R.id.recyclerSuggestedRecipes);


        // =====================================================
        // RECYCLER VIEW SETUP
        // =====================================================

        recyclerSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );


        adapter =
                new SuggestedRecipeAdapter(
                        SuggestedRecipesPage.this,
                        new ArrayList<>()
                );


        recyclerSuggestedRecipes.setAdapter(
                adapter
        );


        // =====================================================
        // API SETUP
        // =====================================================

        recipeApi =
                ApiClient
                        .getClient()
                        .create(RecipeApi.class);


        // =====================================================
        // SEARCH SUGGESTED RECIPES
        // =====================================================

        txtSearchSuggestedRecipe.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {

                        // Nothing required
                    }


                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        String searchText =
                                s.toString().trim();


                        // Change heading depending on search
                        if (searchText.isEmpty()) {

                            txtSuggestedResults.setText(
                                    "Suggested for you"
                            );

                        } else {

                            txtSuggestedResults.setText(
                                    "Search Results"
                            );
                        }


                        // Filter recipes currently loaded
                        adapter.filter(
                                searchText
                        );
                    }


                    @Override
                    public void afterTextChanged(
                            Editable s) {

                        // Nothing required
                    }
                }
        );
    }


    // =====================================================
    // LOAD SUGGESTED RECIPES FROM API
    // =====================================================

    private void loadSuggestedRecipes() {


        // =====================================================
        // GET LOGGED-IN USER ID
        // =====================================================

        int userId =
                getSharedPreferences(
                        "BiteSizePrefs",
                        MODE_PRIVATE
                )
                        .getInt(
                                "USER_ID",
                                -1
                        );


        // =====================================================
        // CHECK USER
        // =====================================================

        if (userId == -1) {

            txtSuggestedResults.setText(
                    "Unable to load recipes"
            );


            Toast.makeText(
                    SuggestedRecipesPage.this,
                    "Unable to find logged-in user",
                    Toast.LENGTH_LONG
            ).show();


            return;
        }


        // =====================================================
        // CALL API
        // =====================================================

        Call<List<Recipe>> call =
                recipeApi.getSuggestedRecipes(
                        userId
                );


        call.enqueue(
                new Callback<List<Recipe>>() {

                    // =================================================
                    // API RESPONSE
                    // =================================================

                    @Override
                    public void onResponse(
                            Call<List<Recipe>> call,
                            Response<List<Recipe>> response) {


                        if (response.isSuccessful()
                                && response.body() != null) {


                            List<Recipe> recipes =
                                    response.body();


                            // Update RecyclerView
                            adapter.setRecipes(
                                    recipes
                            );


                            // =========================================
                            // DISPLAY RESULT COUNT
                            // =========================================

                            if (recipes.isEmpty()) {

                                txtSuggestedResults.setText(
                                        "No recipes available with your current pantry"
                                );

                            } else {

                                txtSuggestedResults.setText(
                                        "Suggested for you ("
                                                + recipes.size()
                                                + ")"
                                );
                            }


                        } else {


                            txtSuggestedResults.setText(
                                    "Unable to load recipes"
                            );


                            Toast.makeText(
                                    SuggestedRecipesPage.this,
                                    "Unable to load suggested recipes",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                    // =================================================
                    // API FAILURE
                    // =================================================

                    @Override
                    public void onFailure(
                            Call<List<Recipe>> call,
                            Throwable throwable) {


                        txtSuggestedResults.setText(
                                "Unable to load recipes"
                        );


                        Toast.makeText(
                                SuggestedRecipesPage.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }


    // =====================================================
    // REFRESH PAGE
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();


        /*
         * onResume is called after onCreate,
         * therefore this loads the recipes when
         * the page opens and also refreshes them
         * when returning from another page.
         */

        if (recipeApi != null) {

            loadSuggestedRecipes();
        }
    }
}