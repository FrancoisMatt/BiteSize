package com.example.bitesize.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class RecipeDetailSuggested extends AppCompatActivity {

    // Views
    private TextView tvRecipeName;
    private TextView tvPrepTime;
    private TextView tvIngredient;
    private TextView txtIngredientMatch;

    // Buttons
    private Button btnConfirm;
    private Button btnBack;

    // Selected Recipe
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.suggestedrecipedetailpage
        );


        // =====================================================
        // LINK JAVA TO XML
        // =====================================================

        tvRecipeName =
                findViewById(R.id.tvRecipeName);

        tvPrepTime =
                findViewById(R.id.tvPrepTime);

        tvIngredient =
                findViewById(R.id.tvIngredient);

        txtIngredientMatch =
                findViewById(R.id.txtIngredientMatch);

        btnConfirm =
                findViewById(R.id.btnConfirm);

        btnBack =
                findViewById(R.id.btnBack);


        // =====================================================
        // GET SELECTED RECIPE
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

        int prepTime =
                getIntent().getIntExtra(
                        "prepTime",
                        0
                );

        String ingredients =
                getIntent().getStringExtra(
                        "ingredients"
                );

        int ingredientCount =
                getIntent().getIntExtra(
                        "ingredientCount",
                        0
                );


        // =====================================================
        // DISPLAY RECIPE
        // =====================================================

        tvRecipeName.setText(
                recipeName
        );

        tvPrepTime.setText(
                "Preparation time: "
                        + prepTime
                        + " minutes"
        );

        tvIngredient.setText(
                ingredients
        );

        txtIngredientMatch.setText(
                "You have "
                        + ingredientCount
                        + " of "
                        + ingredientCount
                        + " required ingredients"
        );


        // =====================================================
        // CONFIRM RECIPE
        // =====================================================

        btnConfirm.setOnClickListener(v -> {

            Toast.makeText(
                    RecipeDetailSuggested.this,
                    "Recipe confirmed",
                    Toast.LENGTH_SHORT
            ).show();


            /*
             * API LOGIC WILL GO HERE
             *
             * Eventually:
             *
             * confirmRecipe(recipeId);
             *
             * The API will:
             *
             * 1. Receive Recipe ID
             * 2. Get required recipe ingredients
             * 3. Re-check user's pantry
             * 4. Deduct required quantities
             * 5. Update pantry
             */

        });


        // =====================================================
        // BACK
        // =====================================================

        btnBack.setOnClickListener(
                v -> finish()
        );
    }
}