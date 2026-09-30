package com.example.bitesize.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;
import com.example.bitesize.models.RecipeIngredient;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.RecipeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRecipe extends AppCompatActivity {

    private TextView tvRecipeName;
    private TextView tvPrepTime;
    private TextView tvIngredients;
    private TextView tvInstructions;

    private RecipeApi recipeApi;

    private int recipeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipesdetailpage);


        // =====================================================
        // LINK XML
        // =====================================================

        tvRecipeName =
                findViewById(R.id.txtRecipeName);

        tvPrepTime =
                findViewById(R.id.txtPrepTime);

        tvIngredients =
                findViewById(R.id.txtIngredients);

        tvInstructions =
                findViewById(R.id.txtInstructions);


        // =====================================================
        // GET RECIPE FROM INTENT
        // =====================================================

        recipeId =
                getIntent().getIntExtra(
                        "RECIPE_ID",
                        -1
                );

        String recipeName =
                getIntent().getStringExtra(
                        "RECIPE_NAME"
                );

        String instructions =
                getIntent().getStringExtra(
                        "RECIPE_DESCRIPTION"
                );

        int prepTime =
                getIntent().getIntExtra(
                        "PREP_TIME",
                        0
                );


        // =====================================================
        // DISPLAY RECIPE
        // =====================================================

        tvRecipeName.setText(
                recipeName != null
                        ? recipeName
                        : "Recipe"
        );

        tvPrepTime.setText(
                "Preparation time: "
                        + prepTime
                        + " minutes"
        );

        tvInstructions.setText(
                instructions != null
                        ? instructions
                        : "No instructions available"
        );

        tvIngredients.setText(
                "Loading ingredients..."
        );


        // =====================================================
        // API
        // =====================================================

        recipeApi =
                ApiClient
                        .getClient()
                        .create(RecipeApi.class);


        // =====================================================
        // LOAD INGREDIENTS
        // =====================================================

        if (recipeId > 0) {

            loadIngredients();

        } else {

            tvIngredients.setText(
                    "Recipe ingredients unavailable"
            );

            Toast.makeText(
                    this,
                    "Invalid recipe ID",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // =====================================================
    // LOAD INGREDIENTS
    // =====================================================

    private void loadIngredients() {

        recipeApi
                .getRecipeIngredients(recipeId)
                .enqueue(
                        new Callback<List<RecipeIngredient>>() {

                            @Override
                            public void onResponse(
                                    Call<List<RecipeIngredient>> call,
                                    Response<List<RecipeIngredient>> response) {

                                if (!response.isSuccessful()) {

                                    tvIngredients.setText(
                                            "Unable to load ingredients"
                                    );

                                    return;
                                }


                                List<RecipeIngredient> ingredients =
                                        response.body();


                                if (ingredients == null
                                        || ingredients.isEmpty()) {

                                    tvIngredients.setText(
                                            "No ingredients available"
                                    );

                                    return;
                                }


                                StringBuilder text =
                                        new StringBuilder();


                                for (RecipeIngredient ingredient
                                        : ingredients) {

                                    text.append("• ");

                                    if (ingredient.getIngredientName() != null
                                            && !ingredient.getIngredientName().isEmpty()) {

                                        text.append(
                                                ingredient.getIngredientName()
                                        );

                                    } else {

                                        text.append(
                                                "Ingredient"
                                        );
                                    }


                                    text.append(" - ")
                                            .append(
                                                    formatQuantity(
                                                            ingredient.getQuantity()
                                                    )
                                            );


                                    if (ingredient.getUnit() != null) {

                                        text.append(" ")
                                                .append(
                                                        ingredient.getUnit()
                                                );
                                    }


                                    text.append("\n");
                                }


                                tvIngredients.setText(
                                        text.toString().trim()
                                );
                            }


                            @Override
                            public void onFailure(
                                    Call<List<RecipeIngredient>> call,
                                    Throwable throwable) {

                                tvIngredients.setText(
                                        "Unable to load ingredients"
                                );

                                Toast.makeText(
                                        DetailRecipe.this,
                                        "API Error: "
                                                + throwable.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =====================================================
    // FORMAT QUANTITY
    // =====================================================

    private String formatQuantity(double quantity) {

        if (quantity == Math.floor(quantity)) {

            return String.valueOf(
                    (int) quantity
            );
        }

        return String.valueOf(quantity);
    }
}