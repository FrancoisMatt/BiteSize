package com.example.bitesize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class ProfilePage extends AppCompatActivity {

    // Specific Fields
    private EditText txtFirstname;
    private EditText txtSurname;
    private EditText txtEmail;
    private EditText txtUsername;

    // Buttons
    private Button btnUpdate;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);

        // Linking Java with XML Layout
        txtFirstname = findViewById(R.id.txtFirstname);
        txtSurname = findViewById(R.id.txtSurname);
        txtEmail = findViewById(R.id.txtEmail);
        txtUsername = findViewById(R.id.txtUsername);

        // Linking Buttons
        btnUpdate = findViewById(R.id.btnUpdate);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        // Update Profile
        btnUpdate.setOnClickListener(view -> updateProfile());

        // Change Password
        btnChangePassword.setOnClickListener(view -> {

            Intent intent = new Intent(
                    ProfilePage.this,
                    ForgotPassword.class
            );

            startActivity(intent);
        });
    }

    // Update Profile
    private void updateProfile() {

        // Get values from fields
        String firstname = txtFirstname.getText().toString().trim();
        String surname = txtSurname.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();

        // First Name Validation
        if (TextUtils.isEmpty(firstname)) {
            txtFirstname.setError("First name is required");
            txtFirstname.requestFocus();
            return;
        }

        // Surname Validation
        if (TextUtils.isEmpty(surname)) {
            txtSurname.setError("Surname is required");
            txtSurname.requestFocus();
            return;
        }

        // Email Validation
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }

        // Basic Email Format Validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please enter a valid email address");
            txtEmail.requestFocus();
            return;
        }

        /*
         * API update will be added later.
         *
         * Eventually we will send:
         *
         * firstname
         * surname
         * email
         * username/user ID
         *
         * to the PostgreSQL API.
         */

        Toast.makeText(
                ProfilePage.this,
                "Profile updated successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}