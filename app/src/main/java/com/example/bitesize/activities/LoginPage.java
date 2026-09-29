package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class LoginPage extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtForgotPassword;
    private TextView txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagelogin);

        //Linking Java with XML components
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);

        // Login button
        btnLogin.setOnClickListener(view -> validateLogin());

        // Forgot password
        txtForgotPassword.setOnClickListener(view -> {

            Intent intent = new Intent(
                    LoginPage.this,
                    ForgotPassword.class
            );

            startActivity(intent);
        });

        // Create account
        txtCreateAccount.setOnClickListener(view -> {

            Intent intent = new Intent(
                    LoginPage.this,
                    CreateAccount.class
            );

            startActivity(intent);
        });
    }

    private void validateLogin() {

        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Username is required");
            txtUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        // Database/API login will be added later
        Toast.makeText(
                LoginPage.this,
                "Login details entered successfully",
                Toast.LENGTH_SHORT
        ).show();

        // Open Home Page
        Intent intent = new Intent(
                LoginPage.this,
                HomePage.class
        );

        startActivity(intent);

// Prevent user from going back to Login using Back button
        finish();
    }
}