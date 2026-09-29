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
import com.example.bitesize.adapters.PantryAdapter;
import com.example.bitesize.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PantryPage extends AppCompatActivity {

    private EditText txtSearchIngredient;
    private RecyclerView recyclerPantry;
    private Button btnAddIngredient;

    private PantryAdapter pantryAdapter;
    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pantrypage);

        // Link Java with XML
        txtSearchIngredient =
                findViewById(R.id.txtSearchIngredient);

        recyclerPantry =
                findViewById(R.id.recyclerPantry);

        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);


        // Create Ingredient List
        ingredientList = new ArrayList<>();


        // Temporary Test Data
        ingredientList.add(
                new Ingredient(
                        1,
                        "Chicken",
                        500,
                        "g",
                        "2026-10-05"
                )
        );

        ingredientList.add(
                new Ingredient(
                        2,
                        "Milk",
                        2,
                        "L",
                        "2026-10-02"
                )
        );

        ingredientList.add(
                new Ingredient(
                        3,
                        "Eggs",
                        12,
                        "Units",
                        "2026-10-08"
                )
        );

        ingredientList.add(
                new Ingredient(
                        4,
                        "Tomatoes",
                        6,
                        "Units",
                        "2026-10-03"
                )
        );


        // RecyclerView
        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pantryAdapter =
                new PantryAdapter(
                        PantryPage.this,
                        ingredientList
                );

        recyclerPantry.setAdapter(
                pantryAdapter
        );


        // Add Ingredient
        btnAddIngredient.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
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
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        pantryAdapter.filter(
                                s.toString()
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );
    }
}