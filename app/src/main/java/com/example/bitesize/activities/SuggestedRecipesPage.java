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

public class SuggestedRecipesPage extends AppCompatActivity {

    // Fields
    private EditText txtSearchSuggestedRecipe;
    private TextView txtSuggestedResults;
    private RecyclerView recyclerSuggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.suggestedrecipepage);

        // Link Java with XML
        txtSearchSuggestedRecipe =
                findViewById(R.id.txtSearchSuggestedRecipe);

        txtSuggestedResults =
                findViewById(R.id.txtSuggestedResults);

        recyclerSuggestedRecipes =
                findViewById(R.id.recyclerSuggestedRecipes);


        // RecyclerView Setup
        recyclerSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );


        /*
         * Adapter and API data will be added later.
         *
         * Eventually:
         *
         * SuggestedRecipeAdapter adapter =
         *      new SuggestedRecipeAdapter(recipeList);
         *
         * recyclerSuggestedRecipes.setAdapter(adapter);
         */


        // Search Suggested Recipes
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

                        if (searchText.isEmpty()) {

                            txtSuggestedResults.setText(
                                    "Suggested for you"
                            );

                        } else {

                            txtSuggestedResults.setText(
                                    "Search Results"
                            );
                        }

                        /*
                         * Adapter filtering will be
                         * added later.
                         *
                         * adapter.filter(searchText);
                         */
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        // Nothing required
                    }
                }
        );
    }
}