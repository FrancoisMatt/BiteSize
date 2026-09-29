package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class RecipeDetailSuggested extends AppCompatActivity {

    //Views

    private TextView tvRecipeName;
    private TextView tvPrepTime;
    private TextView tvIngredient;

    // Buttons

    private Button btnConfirm;

    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestedrecipedetailpage);

        //Java to XML Controls

        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvPrepTime = findViewById(R.id.tvPrepTime);
        tvIngredient = findViewById(R.id.tvIngredient);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);

        btnConfirm.setOnClickListener(v -> {

            Toast.makeText(
                    RecipeDetailSuggested.this,
                    "Recipe confirmed",
                    Toast.LENGTH_SHORT
            ).show();

            // We will add the database/API logic here later
        });

        // Return to suggested recipes
        btnBack.setOnClickListener(v -> finish());
    }



}

