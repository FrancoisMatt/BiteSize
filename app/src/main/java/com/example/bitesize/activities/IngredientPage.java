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

import java.util.Calendar;

public class IngredientPage extends AppCompatActivity {

    // Fields
    private TextView txtIngredientHeader;

    private EditText txtIngredientName;
    private EditText txtQuantity;
    private EditText txtExpiryDate;

    private Spinner spinnerUnit;

    private Button btnSaveIngredient;
    private Button btnDeleteIngredient;

    // Used to determine whether we are adding or editing
    private int ingredientId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredientpage);

        // Link Java fields to XML
        txtIngredientHeader = findViewById(R.id.txtIngredientHeader);

        txtIngredientName = findViewById(R.id.txtIngredientName);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtExpiryDate = findViewById(R.id.txtExpiryDate);

        spinnerUnit = findViewById(R.id.spinnerUnit);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnDeleteIngredient = findViewById(R.id.btnDeleteIngredient);


        // Setup Unit dropdown
        setupUnitSpinner();


        // Check if an existing ingredient was selected
        ingredientId = getIntent().getIntExtra("INGREDIENT_ID", -1);

        if (ingredientId != -1) {

            // Edit mode
            txtIngredientHeader.setText("Edit Ingredient");
            btnSaveIngredient.setText("Update Ingredient");
            btnDeleteIngredient.setVisibility(View.VISIBLE);

            // Later:
            // loadIngredient(ingredientId);

        } else {

            // Add mode
            txtIngredientHeader.setText("Add Ingredient");
            btnSaveIngredient.setText("Save Ingredient");
            btnDeleteIngredient.setVisibility(View.GONE);
        }


        // Open Date Picker
        txtExpiryDate.setOnClickListener(view -> showDatePicker());


        // Save / Update Ingredient
        btnSaveIngredient.setOnClickListener(view -> saveIngredient());


        // Delete Ingredient
        btnDeleteIngredient.setOnClickListener(view -> deleteIngredient());
    }


    // Setup the Unit dropdown
    private void setupUnitSpinner() {

        String[] units = {
                "Select Unit",
                "Units",
                "g",
                "kg",
                "ml",
                "L"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(adapter);
    }


    // Open Date Picker
    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {

                    String date =
                            selectedYear + "-" +
                                    String.format("%02d", selectedMonth + 1) + "-" +
                                    String.format("%02d", selectedDay);

                    txtExpiryDate.setText(date);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }


    // Save or Update Ingredient
    private void saveIngredient() {

        String ingredientName =
                txtIngredientName.getText().toString().trim();

        String quantityText =
                txtQuantity.getText().toString().trim();

        String expiryDate =
                txtExpiryDate.getText().toString().trim();

        String unit =
                spinnerUnit.getSelectedItem().toString();


        // Ingredient validation
        if (TextUtils.isEmpty(ingredientName)) {

            txtIngredientName.setError("Ingredient name is required");
            txtIngredientName.requestFocus();
            return;
        }


        // Quantity validation
        if (TextUtils.isEmpty(quantityText)) {

            txtQuantity.setError("Quantity is required");
            txtQuantity.requestFocus();
            return;
        }


        // Convert quantity to decimal
        double quantity;

        try {

            quantity = Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            txtQuantity.setError("Please enter a valid quantity");
            txtQuantity.requestFocus();
            return;
        }


        // Quantity cannot be zero or negative
        if (quantity <= 0) {

            txtQuantity.setError("Quantity must be greater than 0");
            txtQuantity.requestFocus();
            return;
        }


        // Unit validation
        if (spinnerUnit.getSelectedItemPosition() == 0) {

            Toast.makeText(
                    this,
                    "Please select a unit",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // Expiry Date validation
        if (TextUtils.isEmpty(expiryDate)) {

            Toast.makeText(
                    this,
                    "Please select an expiry date",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // ADD MODE
        if (ingredientId == -1) {

            /*
             * API call will go here later:
             *
             * createIngredient(
             *      ingredientName,
             *      quantity,
             *      unit,
             *      expiryDate
             * );
             */

            Toast.makeText(
                    this,
                    "Ingredient added successfully",
                    Toast.LENGTH_SHORT
            ).show();

        }

        // EDIT MODE
        else {

            /*
             * API call will go here later:
             *
             * updateIngredient(
             *      ingredientId,
             *      ingredientName,
             *      quantity,
             *      unit,
             *      expiryDate
             * );
             */

            Toast.makeText(
                    this,
                    "Ingredient updated successfully",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // Delete Ingredient
    private void deleteIngredient() {

        if (ingredientId == -1) {
            return;
        }

        /*
         * API call will go here later:
         *
         * deleteIngredient(ingredientId);
         */

        Toast.makeText(
                this,
                "Ingredient deleted successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}