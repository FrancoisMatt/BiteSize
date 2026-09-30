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

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtForgotPassword;
    private TextView txtCreateAccount;

    private UserApi userApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagelogin);


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtUsername =
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

        userApi =
                ApiClient
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


    // =========================================================
    // VALIDATE LOGIN
    // =========================================================

    private void validateLogin() {

        String username =
                txtUsername
                        .getText()
                        .toString()
                        .trim();

        String password =
                txtPassword
                        .getText()
                        .toString()
                        .trim();


        // Username / Email validation
        if (TextUtils.isEmpty(username)) {

            txtUsername.setError(
                    "Username is required"
            );

            txtUsername.requestFocus();

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
                username,
                password
        );
    }


    // =========================================================
    // LOGIN USER
    // =========================================================

    private void loginUser(
            String email,
            String password) {


        LoginRequest loginRequest =
                new LoginRequest(
                        email,
                        password
                );


        userApi.login(
                loginRequest
        ).enqueue(new Callback<User>() {


            @Override
            public void onResponse(
                    Call<User> call,
                    Response<User> response) {


                if (response.isSuccessful()
                        && response.body() != null) {


                    User user =
                            response.body();


                    // =================================================
                    // SAVE LOGGED-IN USER INFORMATION
                    // =================================================

                    SharedPreferences preferences =
                            getSharedPreferences(
                                    "BiteSizePrefs",
                                    MODE_PRIVATE
                            );


                    preferences
                            .edit()

                            .putInt(
                                    "USER_ID",
                                    user.getUserId()
                            )

                            .putString(
                                    "USER_FIRST_NAME",
                                    user.getFirstName()
                            )

                            .putString(
                                    "USER_LAST_NAME",
                                    user.getLastName()
                            )

                            .putString(
                                    "USER_EMAIL",
                                    user.getEmail()
                            )

                            .apply();


                    // =================================================
                    // SUCCESS
                    // =================================================

                    Toast.makeText(
                            LoginPage.this,
                            "Login successful",
                            Toast.LENGTH_SHORT
                    ).show();


                    // =================================================
                    // OPEN HOME PAGE
                    // =================================================

                    Intent intent =
                            new Intent(
                                    LoginPage.this,
                                    HomePage.class
                            );


                    startActivity(intent);


                    // Prevent user returning to login
                    finish();


                } else {


                    // =================================================
                    // INVALID LOGIN
                    // =================================================

                    Toast.makeText(
                            LoginPage.this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }


            @Override
            public void onFailure(
                    Call<User> call,
                    Throwable t) {


                // =====================================================
                // API CONNECTION FAILED
                // =====================================================

                Toast.makeText(
                        LoginPage.this,
                        "Unable to connect to server: "
                                + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}