package com.example.bitesize.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;
import com.example.bitesize.models.LoginRequest;
import com.example.bitesize.models.User;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtEmail;
    private EditText txtPassword;

    private Button btnLogin;

    private TextView txtForgotPassword;
    private TextView txtCreateAccount;

    private UserApi userApi;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.pagelogin);


        // =====================================================
        // LINK JAVA TO XML
        // =====================================================

        txtEmail =
                findViewById(R.id.txtEmail);

        txtPassword =
                findViewById(R.id.txtPassword);

        btnLogin =
                findViewById(R.id.btnLogin);

        txtForgotPassword =
                findViewById(R.id.txtForgotPassword);

        txtCreateAccount =
                findViewById(R.id.txtCreateAccount);


        // =====================================================
        // API
        // =====================================================

        userApi = ApiClient
                .getClient()
                .create(UserApi.class);


        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        btnLogin.setOnClickListener(
                view -> validateLogin()
        );


        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        txtForgotPassword.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            LoginPage.this,
                            ForgotPassword.class
                    );

            startActivity(intent);
        });


        // =====================================================
        // CREATE ACCOUNT
        // =====================================================

        txtCreateAccount.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            LoginPage.this,
                            CreateAccount.class
                    );

            startActivity(intent);
        });
    }


    // =====================================================
    // VALIDATE LOGIN
    // =====================================================

    private void validateLogin() {

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


        // Email validation
        if (TextUtils.isEmpty(email)) {

            txtEmail.setError(
                    "Email is required"
            );

            txtEmail.requestFocus();

            return;
        }


        // Password validation
        if (TextUtils.isEmpty(password)) {

            txtPassword.setError(
                    "Password is required"
            );

            txtPassword.requestFocus();

            return;
        }


        // Call API
        loginUser(
                email,
                password
        );
    }


    // =====================================================
    // LOGIN USER THROUGH API
    // =====================================================

    private void loginUser(
            String email,
            String password) {


        LoginRequest loginRequest =
                new LoginRequest(
                        email,
                        password
                );


        // Prevent multiple login clicks
        btnLogin.setEnabled(false);


        userApi
                .login(loginRequest)
                .enqueue(new Callback<User>() {


                    // =================================================
                    // API RESPONSE
                    // =================================================

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {


                        btnLogin.setEnabled(true);


                        // =============================================
                        // LOGIN SUCCESS
                        // =============================================

                        if (response.isSuccessful()
                                && response.body() != null) {


                            User user =
                                    response.body();


                            // =========================================
                            // SAVE LOGGED-IN USER
                            // =========================================

                            SharedPreferences preferences =
                                    getSharedPreferences(
                                            "BiteSizePrefs",
                                            MODE_PRIVATE
                                    );


                            SharedPreferences.Editor editor =
                                    preferences.edit();


                            editor.putInt(
                                    "USER_ID",
                                    user.getUserId()
                            );


                            editor.putString(
                                    "USER_EMAIL",
                                    user.getEmail()
                            );


                            editor.putString(
                                    "USER_NAME",
                                    user.getFirstName()
                            );


                            editor.apply();


                            // =========================================
                            // SUCCESS MESSAGE
                            // =========================================

                            Toast.makeText(
                                    LoginPage.this,
                                    "Welcome "
                                            + user.getFirstName(),
                                    Toast.LENGTH_SHORT
                            ).show();


                            // =========================================
                            // OPEN HOME PAGE
                            // =========================================

                            Intent intent =
                                    new Intent(
                                            LoginPage.this,
                                            HomePage.class
                                    );


                            startActivity(intent);


                            // Prevent Back returning to Login
                            finish();
                        }


                        // =============================================
                        // INVALID LOGIN
                        // =============================================

                        else if (response.code() == 401) {


                            Toast.makeText(
                                    LoginPage.this,
                                    "Invalid email or password",
                                    Toast.LENGTH_LONG
                            ).show();
                        }


                        // =============================================
                        // OTHER API ERROR
                        // =============================================

                        else {


                            Toast.makeText(
                                    LoginPage.this,
                                    "Login failed. Code: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    // =================================================
                    // CONNECTION FAILURE
                    // =================================================

                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable throwable) {


                        btnLogin.setEnabled(true);


                        Toast.makeText(
                                LoginPage.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}