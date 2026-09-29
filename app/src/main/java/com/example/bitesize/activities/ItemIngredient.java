package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class ItemIngredient extends AppCompatActivity {

    //Page fields

    private TextView tvIngredientName;
    private TextView txtIngredientQuantity;
    private TextView txtIngredientExpiry ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemingredient);

        tvIngredientName = findViewById(R.id.tvIngredientName);
        txtIngredientQuantity = findViewById(R.id.txtIngredientQuantity);
        txtIngredientExpiry = findViewById(R.id.txtIngredientExpiry);


    }
}
