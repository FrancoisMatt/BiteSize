package com.example.bitesize.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitesize.R;
import com.example.bitesize.adapters.PantryAdapter;
import com.example.bitesize.models.Ingredient;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.PantryApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantryPage extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtSearchIngredient;
    private RecyclerView recyclerPantry;
    private Button btnAddIngredient;

    private PantryAdapter pantryAdapter;
    private List<Ingredient> ingredientList;

    private PantryApi pantryApi;

    // Logged-in user
    private int userId = -1;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.pantrypage);


        // =====================================================
        // GET LOGGED-IN USER
        // =====================================================

        SharedPreferences preferences =
                getSharedPreferences(
                        "BiteSizePrefs",
                        MODE_PRIVATE
                );

        userId =
                preferences.getInt(
                        "USER_ID",
                        -1
                );


        // User has not logged in
        if (userId == -1) {

            Toast.makeText(
                    this,
                    "Please login first",
                    Toast.LENGTH_SHORT
            ).show();


            Intent intent =
                    new Intent(
                            PantryPage.this,
                            LoginPage.class
                    );

            startActivity(intent);

            finish();

            return;
        }


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtSearchIngredient =
                findViewById(
                        R.id.txtSearchIngredient
                );

        recyclerPantry =
                findViewById(
                        R.id.recyclerPantry
                );

        btnAddIngredient =
                findViewById(
                        R.id.btnAddIngredient
                );


        // =====================================================
        // INGREDIENT LIST
        // =====================================================

        ingredientList =
                new ArrayList<>();


        // =====================================================
        // RECYCLER VIEW
        // =====================================================

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


        // =====================================================
        // API
        // =====================================================

        pantryApi =
                ApiClient
                        .getClient()
                        .create(PantryApi.class);


        // =====================================================
        // ADD INGREDIENT
        // =====================================================

        btnAddIngredient.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            PantryPage.this,
                            IngredientPage.class
                    );

            startActivity(intent);
        });


        // =====================================================
        // SEARCH
        // =====================================================

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


    // =====================================================
    // LOAD PANTRY FROM API
    // =====================================================

    private void loadPantry() {

        // Important:
        // Uses logged-in user instead of USER_ID = 1

        Call<List<Ingredient>> call =
                pantryApi.getPantryByUser(
                        userId
                );


        call.enqueue(
                new Callback<List<Ingredient>>() {

                    @Override
                    public void onResponse(
                            Call<List<Ingredient>> call,
                            Response<List<Ingredient>> response) {


                        if (response.isSuccessful()
                                && response.body() != null) {


                            /*
                             * updateData updates both the displayed
                             * list and the full search/filter list.
                             */
                            pantryAdapter.updateData(
                                    response.body()
                            );


                            if (response.body().isEmpty()) {

                                Toast.makeText(
                                        PantryPage.this,
                                        "Your pantry is empty",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                        } else {

                            Toast.makeText(
                                    PantryPage.this,
                                    "Unable to load pantry",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<List<Ingredient>> call,
                            Throwable throwable) {


                        Toast.makeText(
                                PantryPage.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }


    // =====================================================
    // REFRESH WHEN RETURNING TO PANTRY
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();


        if (pantryApi != null
                && userId != -1) {

            loadPantry();
        }
    }
}