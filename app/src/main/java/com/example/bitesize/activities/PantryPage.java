package com.example.bitesize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitesize.R;

public class PantryPage extends AppCompatActivity {

    // Fields
    private EditText txtSearchIngredient;
    private RecyclerView recyclerPantry;
    private Button btnAddIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantrypage);

        // Linking Java with XML components
        txtSearchIngredient = findViewById(R.id.txtSearchIngredient);
        recyclerPantry = findViewById(R.id.recyclerPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        // RecyclerView setup
        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        /*
         * Adapter will be added later when we connect
         * the Pantry to the API/database.
         *
         * Example:
         *
         * pantryAdapter = new PantryAdapter(ingredientList);
         * recyclerPantry.setAdapter(pantryAdapter);
         */


        // Add Ingredient
        btnAddIngredient.setOnClickListener(view -> {

            Intent intent = new Intent(
                    PantryPage.this,
                    IngredientPage.class
            );

            startActivity(intent);
        });


        // Search Ingredients
        txtSearchIngredient.addTextChangedListener(
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

                        /*
                         * Search/filter logic will be added
                         * when the RecyclerView Adapter is created.
                         *
                         * Example:
                         *
                         * pantryAdapter.filter(searchText);
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