package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class ForgotPassword extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtNewPassword;
    private EditText txtConfirmPassword;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        // Linking Java with XML components
        txtUsername = findViewById(R.id.txtUsername);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnSubmit = findViewById(R.id.btnSubmit);

        // Submit password change
        btnSubmit.setOnClickListener(view -> changePassword());
    }

    private void changePassword() {

        // Get values
        String username =
                txtUsername.getText().toString().trim();

        String newPassword =
                txtNewPassword.getText().toString().trim();

        String confirmPassword =
                txtConfirmPassword.getText().toString().trim();


        // Username validation
        if (TextUtils.isEmpty(username)) {

            txtUsername.setError("Username required");
            txtUsername.requestFocus();
            return;
        }


        // New Password validation
        if (TextUtils.isEmpty(newPassword)) {

            txtNewPassword.setError(
                    "Please enter a new password"
            );

            txtNewPassword.requestFocus();
            return;
        }


        // Confirm Password validation
        if (TextUtils.isEmpty(confirmPassword)) {

            txtConfirmPassword.setError(
                    "Please confirm your password"
            );

            txtConfirmPassword.requestFocus();
            return;
        }


        // Password Match
        if (!newPassword.equals(confirmPassword)) {

            txtConfirmPassword.setError(
                    "Passwords do not match"
            );

            txtConfirmPassword.requestFocus();
            return;
        }


        /*
         * API call will be added later.
         *
         * Later:
         *
         * Check username exists
         *      ↓
         * Update password
         *      ↓
         * PostgreSQL
         */


        // Temporary success message
        Toast.makeText(
                ForgotPassword.this,
                "Password validation successful",
                Toast.LENGTH_SHORT
        ).show();
    }
}