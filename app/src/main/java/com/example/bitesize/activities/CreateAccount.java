package com.example.bitesize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;
import com.example.bitesize.models.User;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtFirstname;
    private EditText txtSurname;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;

    private Button btnCreate;

    private UserApi userApi;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.createaccount);


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtFirstname =
                findViewById(R.id.txtFirstname);

        txtSurname =
                findViewById(R.id.txtSurname);

        txtEmail =
                findViewById(R.id.txtEmail);

        txtPassword =
                findViewById(R.id.txtPassword);

        txtConfirmPassword =
                findViewById(R.id.txtConfirmPassword);

        btnCreate =
                findViewById(R.id.btnCreate);


        // =====================================================
        // API
        // =====================================================

        userApi =
                ApiClient
                        .getClient()
                        .create(UserApi.class);


        // =====================================================
        // CREATE ACCOUNT
        // =====================================================

        btnCreate.setOnClickListener(
                view -> createUser()
        );
    }


    // =====================================================
    // CREATE USER
    // =====================================================

    private void createUser() {

        String firstname =
                txtFirstname
                        .getText()
                        .toString()
                        .trim();

        String surname =
                txtSurname
                        .getText()
                        .toString()
                        .trim();

        String email =
                txtEmail
                        .getText()
                        .toString()
                        .trim();

        String password =
                txtPassword
                        .getText()
                        .toString()
                        .trim();

        String confirmPassword =
                txtConfirmPassword
                        .getText()
                        .toString()
                        .trim();


        // =====================================================
        // FIRST NAME VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(firstname)) {

            txtFirstname.setError(
                    "First name is required"
            );

            txtFirstname.requestFocus();

            return;
        }


        // =====================================================
        // SURNAME VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(surname)) {

            txtSurname.setError(
                    "Surname is required"
            );

            txtSurname.requestFocus();

            return;
        }


        // =====================================================
        // EMAIL VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(email)) {

            txtEmail.setError(
                    "Email is required"
            );

            txtEmail.requestFocus();

            return;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            txtEmail.setError(
                    "Please enter a valid email"
            );

            txtEmail.requestFocus();

            return;
        }


        // =====================================================
        // PASSWORD VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(password)) {

            txtPassword.setError(
                    "Password is required"
            );

            txtPassword.requestFocus();

            return;
        }


        if (password.length() < 6) {

            txtPassword.setError(
                    "Password must be at least 6 characters"
            );

            txtPassword.requestFocus();

            return;
        }


        // =====================================================
        // CONFIRM PASSWORD
        // =====================================================

        if (TextUtils.isEmpty(confirmPassword)) {

            txtConfirmPassword.setError(
                    "Please confirm your password"
            );

            txtConfirmPassword.requestFocus();

            return;
        }


        if (!password.equals(confirmPassword)) {

            txtConfirmPassword.setError(
                    "Passwords do not match"
            );

            txtConfirmPassword.requestFocus();

            return;
        }


        // =====================================================
        // CREATE USER OBJECT
        // =====================================================

        User newUser =
                new User(
                        firstname,
                        surname,
                        email,
                        password
                );


        // =====================================================
        // CALL API
        // =====================================================

        btnCreate.setEnabled(false);


        userApi
                .createUser(newUser)
                .enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {


                        btnCreate.setEnabled(true);


                        // =====================================
                        // ACCOUNT CREATED
                        // =====================================

                        if (response.isSuccessful()
                                && response.body() != null) {


                            Toast.makeText(
                                    CreateAccount.this,
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                            // Return to login page

                            Intent intent =
                                    new Intent(
                                            CreateAccount.this,
                                            LoginPage.class
                                    );


                            // Prevent returning to
                            // registration with Back

                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            );


                            startActivity(intent);

                            finish();
                        }


                        // =====================================
                        // DUPLICATE / BAD REQUEST
                        // =====================================

                        else if (response.code() == 409) {

                            txtEmail.setError(
                                    "An account with this email already exists"
                            );

                            txtEmail.requestFocus();
                        }


                        // =====================================
                        // OTHER ERROR
                        // =====================================

                        else {

                            Toast.makeText(
                                    CreateAccount.this,
                                    "Unable to create account. Code: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable throwable) {


                        btnCreate.setEnabled(true);


                        Toast.makeText(
                                CreateAccount.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}