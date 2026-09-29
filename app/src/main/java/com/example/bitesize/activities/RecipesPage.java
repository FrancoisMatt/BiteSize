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
import com.example.bitesize.adapters.RecipeAdapter;
import com.example.bitesize.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesPage extends AppCompatActivity {

    // Fields
    private EditText txtSearchRecipe;
    private TextView txtRecipeResults;
    private RecyclerView recyclerRecipes;

    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipespage);

        // Linking Java with XML
        txtSearchRecipe =
                findViewById(R.id.txtSearchRecipe);

        txtRecipeResults =
                findViewById(R.id.txtRecipeResults);

        recyclerRecipes =
                findViewById(R.id.recyclerRecipes);


        // Create Recipe List
        recipeList = new ArrayList<>();


        // Temporary Test Data
        recipeList.add(
                new Recipe(
                        1,
                        "Chicken Pasta",
                        "Creamy chicken pasta",
                        30
                )
        );

        recipeList.add(
                new Recipe(
                        2,
                        "Beef Stir Fry",
                        "Beef and vegetables served with rice",
                        25
                )
        );

        recipeList.add(
                new Recipe(
                        3,
                        "Tomato Pasta",
                        "Simple tomato and herb pasta",
                        20
                )
        );

        recipeList.add(
                new Recipe(
                        4,
                        "Chicken Salad",
                        "Fresh salad with grilled chicken",
                        15
                )
        );


        // RecyclerView Setup
        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recipeAdapter =
                new RecipeAdapter(
                        RecipesPage.this,
                        recipeList
                );

        recyclerRecipes.setAdapter(
                recipeAdapter
        );


        // Search Recipes
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

                        // Filter RecyclerView
                        recipeAdapter.filter(searchText);

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
}