package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitesize.R;
import com.example.bitesize.adapters.SuggestedRecipeAdapter;
import com.example.bitesize.models.Ingredient;
import com.example.bitesize.models.Recipe;
import com.example.bitesize.models.RecipeIngredient;
import com.example.bitesize.utils.RecipeCompare;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesPage extends AppCompatActivity {

    // Fields
    private EditText txtSearchSuggestedRecipe;
    private TextView txtSuggestedResults;
    private RecyclerView recyclerSuggestedRecipes;

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


        // =====================================================
        // TEMPORARY USER PANTRY
        // API DATA WILL REPLACE THIS LATER
        // =====================================================

        List<Ingredient> pantry = new ArrayList<>();

        pantry.add(
                new Ingredient(
                        1,
                        "Chicken",
                        1,
                        "kg",
                        "2026-10-10"
                )
        );

        pantry.add(
                new Ingredient(
                        2,
                        "Tomatoes",
                        5,
                        "units",
                        "2026-10-05"
                )
        );

        pantry.add(
                new Ingredient(
                        3,
                        "Rice",
                        1,
                        "kg",
                        "2027-01-01"
                )
        );

        pantry.add(
                new Ingredient(
                        4,
                        "Onions",
                        3,
                        "units",
                        "2026-10-08"
                )
        );


        // =====================================================
        // RECIPE 1 - SHOULD PASS
        // =====================================================

        List<RecipeIngredient> chickenRiceIngredients =
                new ArrayList<>();


        // Chicken - Pantry has 1kg
        // Recipe requires 500g
        chickenRiceIngredients.add(
                new RecipeIngredient(
                        1,
                        1,
                        "Chicken",
                        500,
                        "g"
                )
        );


        // Tomato - Pantry has "Tomatoes"
        // Tests singular/plural matching
        chickenRiceIngredients.add(
                new RecipeIngredient(
                        2,
                        1,
                        "Tomato",
                        2,
                        "units"
                )
        );


        // Rice - Pantry has 1kg
        // Recipe requires 250g
        chickenRiceIngredients.add(
                new RecipeIngredient(
                        3,
                        1,
                        "Rice",
                        250,
                        "g"
                )
        );


        Recipe chickenRice = new Recipe(
                1,
                "Chicken and Rice",
                "Cook chicken, rice and tomatoes together.",
                30,
                chickenRiceIngredients
        );


        // =====================================================
        // RECIPE 2 - SHOULD FAIL
        // USER DOES NOT HAVE BEEF
        // =====================================================

        List<RecipeIngredient> beefRiceIngredients =
                new ArrayList<>();


        beefRiceIngredients.add(
                new RecipeIngredient(
                        4,
                        2,
                        "Beef",
                        500,
                        "g"
                )
        );


        beefRiceIngredients.add(
                new RecipeIngredient(
                        5,
                        2,
                        "Rice",
                        250,
                        "g"
                )
        );


        Recipe beefRice = new Recipe(
                2,
                "Beef and Rice",
                "Cook beef and rice together.",
                25,
                beefRiceIngredients
        );


        // =====================================================
        // ALL RECIPES
        // =====================================================

        List<Recipe> allRecipes = new ArrayList<>();

        allRecipes.add(chickenRice);
        allRecipes.add(beefRice);


        // =====================================================
        // STRICT RECIPE MATCHING
        // =====================================================

        List<Recipe> suggestedRecipes =
                new ArrayList<>();


        for (Recipe recipe : allRecipes) {

            if (RecipeCompare.canMakeRecipe(
                    recipe,
                    pantry)) {

                // Recipe passed ALL requirements
                suggestedRecipes.add(recipe);
            }
        }


        // =====================================================
        // CONNECT SUGGESTED RECIPES TO RECYCLER VIEW
        // =====================================================

        SuggestedRecipeAdapter adapter =
                new SuggestedRecipeAdapter(
                        SuggestedRecipesPage.this,
                        suggestedRecipes
                );

        recyclerSuggestedRecipes.setAdapter(adapter);


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


                        // Filter suggested recipes
                        adapter.filter(searchText);
                    }


                    @Override
                    public void afterTextChanged(
                            Editable s) {

                        // Nothing required
                    }
                }
        );
    }
}