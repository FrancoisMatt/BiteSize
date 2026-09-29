package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class DetailRecipe extends AppCompatActivity {

    //views
    private TextView  tvRecipeName;
    private TextView tvPrepTime;
    private TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipesdetailpage);

        tvRecipeName = findViewById(R.id.txtRecipeName);
        tvPrepTime = findViewById(R.id.txtPrepTime);
        tvIngredients = findViewById(R.id.txtIngredients);

    }




}
