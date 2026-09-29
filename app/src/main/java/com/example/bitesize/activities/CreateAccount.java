package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class CreateAccount extends AppCompatActivity {

    // Fields
    private EditText txtFirstname;
    private EditText txtSurname;
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtPassword;

    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        // Link Java with XML
        txtFirstname = findViewById(R.id.txtFirstname);
        txtSurname = findViewById(R.id.txtSurname);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        // IMPORTANT - link the button
        btnCreate = findViewById(R.id.btnCreate);

        // Create Account
        btnCreate.setOnClickListener(view -> createUser());
    }

    private void createUser() {

        String firstname =
                txtFirstname.getText().toString().trim();

        String surname =
                txtSurname.getText().toString().trim();

        String username =
                txtUsername.getText().toString().trim();

        String email =
                txtEmail.getText().toString().trim();

        String password =
                txtPassword.getText().toString().trim();


        // First Name
        if (TextUtils.isEmpty(firstname)) {
            txtFirstname.setError("First name is required");
            txtFirstname.requestFocus();
            return;
        }


        // Surname
        if (TextUtils.isEmpty(surname)) {
            txtSurname.setError("Surname is required");
            txtSurname.requestFocus();
            return;
        }


        // Email
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }


        // Email Format
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            txtEmail.setError("Please enter a valid email");
            txtEmail.requestFocus();
            return;
        }


        // Username
        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Username is required");
            txtUsername.requestFocus();
            return;
        }


        // Password
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }


        /*
         * API call will be added later.
         *
         * POST /users
         *
         * firstname
         * surname
         * email
         * username
         * password
         */


        Toast.makeText(
                CreateAccount.this,
                "Account details validated successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}