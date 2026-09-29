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

public class RecipesPage extends AppCompatActivity {

    // Fields
    private EditText txtSearchRecipe;
    private TextView txtRecipeResults;
    private RecyclerView recyclerRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipespage);

        // Linking Java with XML Components
        txtSearchRecipe = findViewById(R.id.txtSearchRecipe);
        txtRecipeResults = findViewById(R.id.txtRecipeResults);
        recyclerRecipes = findViewById(R.id.recyclerRecipes);

        // RecyclerView Setup
        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        /*
         * Adapter will be added later.
         *
         * Example:
         *
         * recipeAdapter = new RecipeAdapter(recipeList);
         * recyclerRecipes.setAdapter(recipeAdapter);
         *
         * Recipe data will eventually come from the API.
         */


        // Search Recipes
        txtSearchRecipe.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {

                        // Nothing required here
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        String searchText =
                                s.toString().trim();

                        if (searchText.isEmpty()) {

                            txtRecipeResults.setText("All Recipes");

                        } else {

                            txtRecipeResults.setText("Search Results");
                        }

                        /*
                         * Filtering will be added when
                         * RecipeAdapter is created.
                         *
                         * Example:
                         *
                         * recipeAdapter.filter(searchText);
                         */
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        // Nothing required here
                    }
                }
        );
    }
}