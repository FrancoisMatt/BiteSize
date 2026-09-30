package com.example.bitesize.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;
import com.example.bitesize.models.Ingredient;
import com.example.bitesize.models.IngredientOption;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.IngredientApi;
import com.example.bitesize.network.PantryApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientPage extends AppCompatActivity {

    // =====================================================
    // VIEWS
    // =====================================================

    private TextView txtIngredientHeader;

    private Spinner spinnerIngredient;
    private Spinner spinnerUnit;

    private EditText txtQuantity;
    private EditText txtExpiryDate;

    private Button btnSaveIngredient;
    private Button btnDeleteIngredient;


    // =====================================================
    // API
    // =====================================================

    private PantryApi pantryApi;
    private IngredientApi ingredientApi;


    // =====================================================
    // DATA
    // =====================================================

    private final List<IngredientOption> ingredientOptions =
            new ArrayList<>();


    // Temporary until login is connected
    private static final int USER_ID = 1;


    // Pantry row ID
    private int pantryItemId = -1;

    // Master ingredient ID
    private int ingredientId = -1;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.ingredientpage);


        // =====================================================
        // LINK VIEWS
        // =====================================================

        txtIngredientHeader =
                findViewById(R.id.txtIngredientHeader);

        spinnerIngredient =
                findViewById(R.id.spinnerIngredient);

        txtQuantity =
                findViewById(R.id.txtQuantity);

        spinnerUnit =
                findViewById(R.id.spinnerUnit);

        txtExpiryDate =
                findViewById(R.id.txtExpiryDate);

        btnSaveIngredient =
                findViewById(R.id.btnSaveIngredient);

        btnDeleteIngredient =
                findViewById(R.id.btnDeleteIngredient);


        // =====================================================
        // API SETUP
        // =====================================================

        pantryApi = ApiClient
                .getClient()
                .create(PantryApi.class);

        ingredientApi = ApiClient
                .getClient()
                .create(IngredientApi.class);


        // =====================================================
        // SETUP CONTROLS
        // =====================================================

        setupUnitSpinner();


        // =====================================================
        // GET EDIT DATA
        // =====================================================

        pantryItemId =
                getIntent().getIntExtra(
                        "PANTRY_ITEM_ID",
                        -1
                );

        ingredientId =
                getIntent().getIntExtra(
                        "INGREDIENT_ID",
                        -1
                );


        if (pantryItemId != -1) {

            setupEditMode();

        } else {

            setupAddMode();
        }


        // Load ingredient master list from PostgreSQL
        loadIngredients();


        // =====================================================
        // DATE PICKER
        // =====================================================

        txtExpiryDate.setOnClickListener(
                view -> showDatePicker()
        );


        // =====================================================
        // SAVE
        // =====================================================

        btnSaveIngredient.setOnClickListener(
                view -> saveIngredient()
        );


        // =====================================================
        // DELETE
        // =====================================================

        btnDeleteIngredient.setOnClickListener(
                view -> deleteIngredient()
        );
    }


    // =====================================================
    // ADD MODE
    // =====================================================

    private void setupAddMode() {

        txtIngredientHeader.setText(
                "Add Ingredient"
        );

        btnSaveIngredient.setText(
                "Save Ingredient"
        );

        btnDeleteIngredient.setVisibility(
                View.GONE
        );
    }


    // =====================================================
    // EDIT MODE
    // =====================================================

    private void setupEditMode() {

        txtIngredientHeader.setText(
                "Edit Ingredient"
        );

        btnSaveIngredient.setText(
                "Update Ingredient"
        );

        btnDeleteIngredient.setVisibility(
                View.VISIBLE
        );


        double quantity =
                getIntent().getDoubleExtra(
                        "QUANTITY",
                        0
                );

        String unit =
                getIntent().getStringExtra(
                        "UNIT"
                );

        String expiryDate =
                getIntent().getStringExtra(
                        "EXPIRY_DATE"
                );


        txtQuantity.setText(
                String.valueOf(quantity)
        );


        if (expiryDate != null) {

            txtExpiryDate.setText(
                    expiryDate
            );
        }


        // Select existing unit

        if (unit != null) {

            for (int i = 0;
                 i < spinnerUnit.getCount();
                 i++) {

                if (spinnerUnit
                        .getItemAtPosition(i)
                        .toString()
                        .equalsIgnoreCase(unit)) {

                    spinnerUnit.setSelection(i);

                    break;
                }
            }
        }
    }


    // =====================================================
    // LOAD INGREDIENTS FROM API
    // =====================================================

    private void loadIngredients() {

        ingredientApi
                .getAllIngredients()
                .enqueue(
                        new Callback<List<IngredientOption>>() {

                            @Override
                            public void onResponse(
                                    Call<List<IngredientOption>> call,
                                    Response<List<IngredientOption>> response) {


                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    ingredientOptions.clear();

                                    ingredientOptions.addAll(
                                            response.body()
                                    );


                                    ArrayAdapter<IngredientOption> adapter =
                                            new ArrayAdapter<>(
                                                    IngredientPage.this,
                                                    android.R.layout
                                                            .simple_spinner_item,
                                                    ingredientOptions
                                            );


                                    adapter.setDropDownViewResource(
                                            android.R.layout
                                                    .simple_spinner_dropdown_item
                                    );


                                    spinnerIngredient.setAdapter(
                                            adapter
                                    );


                                    // If editing, select existing ingredient
                                    if (ingredientId != -1) {

                                        selectIngredient(
                                                ingredientId
                                        );
                                    }

                                } else {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            "Unable to load ingredients",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<List<IngredientOption>> call,
                                    Throwable throwable) {


                                Toast.makeText(
                                        IngredientPage.this,
                                        "Ingredient API Error: "
                                                + throwable.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =====================================================
    // SELECT INGREDIENT WHEN EDITING
    // =====================================================

    private void selectIngredient(
            int selectedIngredientId) {

        for (int i = 0;
             i < ingredientOptions.size();
             i++) {

            if (ingredientOptions
                    .get(i)
                    .getIngredientId()
                    == selectedIngredientId) {

                spinnerIngredient.setSelection(i);

                break;
            }
        }
    }


    // =====================================================
    // UNIT SPINNER
    // =====================================================

    private void setupUnitSpinner() {

        String[] units = {
                "Select Unit",
                "Units",
                "g",
                "kg",
                "ml",
                "L"
        };


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );


        adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item
        );


        spinnerUnit.setAdapter(adapter);
    }


    // =====================================================
    // DATE PICKER
    // =====================================================

    private void showDatePicker() {

        Calendar calendar =
                Calendar.getInstance();


        int year =
                calendar.get(Calendar.YEAR);

        int month =
                calendar.get(Calendar.MONTH);

        int day =
                calendar.get(
                        Calendar.DAY_OF_MONTH
                );


        DatePickerDialog datePickerDialog =
                new DatePickerDialog(
                        this,

                        (view,
                         selectedYear,
                         selectedMonth,
                         selectedDay) -> {


                            String date =
                                    selectedYear
                                            + "-"
                                            + String.format(
                                            "%02d",
                                            selectedMonth + 1
                                    )
                                            + "-"
                                            + String.format(
                                            "%02d",
                                            selectedDay
                                    );


                            txtExpiryDate.setText(
                                    date
                            );
                        },

                        year,
                        month,
                        day
                );


        datePickerDialog.show();
    }


    // =====================================================
    // SAVE
    // =====================================================

    private void saveIngredient() {

        if (ingredientOptions.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please wait for ingredients to load",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        IngredientOption selectedIngredient =
                (IngredientOption)
                        spinnerIngredient
                                .getSelectedItem();


        if (selectedIngredient == null) {

            Toast.makeText(
                    this,
                    "Please select an ingredient",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        String quantityText =
                txtQuantity
                        .getText()
                        .toString()
                        .trim();


        String expiryDate =
                txtExpiryDate
                        .getText()
                        .toString()
                        .trim();


        String unit =
                spinnerUnit
                        .getSelectedItem()
                        .toString();


        // =====================================================
        // QUANTITY VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(
                quantityText)) {

            txtQuantity.setError(
                    "Quantity is required"
            );

            txtQuantity.requestFocus();

            return;
        }


        double quantity;


        try {

            quantity =
                    Double.parseDouble(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            txtQuantity.setError(
                    "Please enter a valid quantity"
            );

            return;
        }


        if (quantity <= 0) {

            txtQuantity.setError(
                    "Quantity must be greater than 0"
            );

            return;
        }


        // =====================================================
        // UNIT VALIDATION
        // =====================================================

        if (spinnerUnit
                .getSelectedItemPosition()
                == 0) {

            Toast.makeText(
                    this,
                    "Please select a unit",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =====================================================
        // EXPIRY VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(
                expiryDate)) {

            Toast.makeText(
                    this,
                    "Please select an expiry date",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // Get REAL IngredientId from PostgreSQL

        int selectedIngredientId =
                selectedIngredient
                        .getIngredientId();


        // =====================================================
        // CREATE
        // =====================================================

        if (pantryItemId == -1) {

            createPantryItem(
                    selectedIngredientId,
                    selectedIngredient.getName(),
                    quantity,
                    unit,
                    expiryDate
            );
        }


        // =====================================================
        // UPDATE
        // =====================================================

        else {

            updatePantryItem(
                    selectedIngredientId,
                    quantity,
                    unit,
                    expiryDate
            );
        }
    }


    // =====================================================
    // CREATE
    // =====================================================

    private void createPantryItem(
            int selectedIngredientId,
            String ingredientName,
            double quantity,
            String unit,
            String expiryDate) {


        Ingredient ingredient =
                new Ingredient(
                        USER_ID,
                        selectedIngredientId,
                        quantity,
                        unit,
                        expiryDate
                );


        pantryApi
                .addPantryItem(ingredient)
                .enqueue(
                        new Callback<Ingredient>() {

                            @Override
                            public void onResponse(
                                    Call<Ingredient> call,
                                    Response<Ingredient> response) {


                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            ingredientName
                                                    + " added successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();


                                    finish();

                                } else {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            "Unable to add ingredient. Code: "
                                                    + response.code(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<Ingredient> call,
                                    Throwable throwable) {


                                Toast.makeText(
                                        IngredientPage.this,
                                        "API Error: "
                                                + throwable.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =====================================================
    // UPDATE
    // =====================================================

    private void updatePantryItem(
            int selectedIngredientId,
            double quantity,
            String unit,
            String expiryDate) {


        Ingredient ingredient =
                new Ingredient(
                        USER_ID,
                        selectedIngredientId,
                        quantity,
                        unit,
                        expiryDate
                );


        pantryApi
                .updatePantryItem(
                        pantryItemId,
                        ingredient
                )
                .enqueue(
                        new Callback<Ingredient>() {

                            @Override
                            public void onResponse(
                                    Call<Ingredient> call,
                                    Response<Ingredient> response) {


                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            "Ingredient updated successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();


                                    finish();

                                } else {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            "Unable to update ingredient. Code: "
                                                    + response.code(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<Ingredient> call,
                                    Throwable throwable) {


                                Toast.makeText(
                                        IngredientPage.this,
                                        "API Error: "
                                                + throwable.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =====================================================
    // DELETE
    // =====================================================

    private void deleteIngredient() {

        if (pantryItemId == -1) {

            return;
        }


        pantryApi
                .deletePantryItem(
                        pantryItemId
                )
                .enqueue(
                        new Callback<Void>() {

                            @Override
                            public void onResponse(
                                    Call<Void> call,
                                    Response<Void> response) {


                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            "Ingredient deleted successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();


                                    finish();

                                } else {

                                    Toast.makeText(
                                            IngredientPage.this,
                                            "Unable to delete ingredient. Code: "
                                                    + response.code(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<Void> call,
                                    Throwable throwable) {


                                Toast.makeText(
                                        IngredientPage.this,
                                        "API Error: "
                                                + throwable.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }
}