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

public class RecipeDetailSuggested extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtPrepTime;
    private TextView txtIngredients;
    private TextView txtInstructions;

    private RecipeApi recipeApi;

    private int recipeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipesdetailpage);


        // =====================================================
        // LINK XML
        // =====================================================

        txtRecipeName =
                findViewById(R.id.txtRecipeName);

        txtPrepTime =
                findViewById(R.id.txtPrepTime);

        txtIngredients =
                findViewById(R.id.txtIngredients);

        txtInstructions =
                findViewById(R.id.txtInstructions);


        // =====================================================
        // GET DATA FROM ADAPTER
        // =====================================================

        recipeId =
                getIntent().getIntExtra(
                        "recipeId",
                        -1
                );


        String recipeName =
                getIntent().getStringExtra(
                        "recipeName"
                );


        Integer prepTime =
                getIntent().getIntExtra(
                        "prepTime",
                        0
                );


        String instructions =
                getIntent().getStringExtra(
                        "instructions"
                );


        // =====================================================
        // DISPLAY RECIPE DETAILS
        // =====================================================

        txtRecipeName.setText(
                recipeName != null
                        ? recipeName
                        : "Recipe"
        );


        txtPrepTime.setText(
                "Preparation time: "
                        + prepTime
                        + " minutes"
        );


        txtInstructions.setText(
                instructions != null
                        ? instructions
                        : "No instructions available"
        );


        txtIngredients.setText(
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

            txtIngredients.setText(
                    "No ingredients available"
            );
        }
    }


    // =====================================================
    // LOAD INGREDIENTS FROM API
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


                                if (!response.isSuccessful()
                                        || response.body() == null) {

                                    txtIngredients.setText(
                                            "Unable to load ingredients"
                                    );

                                    return;
                                }


                                List<RecipeIngredient> ingredients =
                                        response.body();


                                if (ingredients.isEmpty()) {

                                    txtIngredients.setText(
                                            "No ingredients available"
                                    );

                                    return;
                                }


                                StringBuilder text =
                                        new StringBuilder();


                                for (RecipeIngredient ingredient :
                                        ingredients) {


                                    text.append("• ");


                                    if (ingredient.getIngredientName() != null
                                            && !ingredient
                                            .getIngredientName()
                                            .isEmpty()) {

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


                                txtIngredients.setText(
                                        text.toString().trim()
                                );
                            }


                            @Override
                            public void onFailure(
                                    Call<List<RecipeIngredient>> call,
                                    Throwable throwable) {


                                txtIngredients.setText(
                                        "Unable to load ingredients"
                                );


                                Toast.makeText(
                                        RecipeDetailSuggested.this,
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

    private String formatQuantity(
            double quantity) {


        if (quantity ==
                Math.floor(quantity)) {

            return String.valueOf(
                    (int) quantity
            );
        }


        return String.valueOf(
                quantity
        );
    }
}