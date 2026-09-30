package com.example.bitesize.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class ProfilePage extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtFirstname;
    private EditText txtSurname;
    private EditText txtEmail;
    private EditText txtUsername;

    private Button btnUpdate;
    private Button btnChangePassword;

    private UserApi userApi;

    private User currentUser;

    private int userId = -1;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.profilepage);


        // =====================================================
        // GET LOGGED-IN USER
        // =====================================================

        SharedPreferences preferences =
                getSharedPreferences(
                        "BiteSizePrefs",
                        MODE_PRIVATE
                );

        userId =
                preferences.getInt(
                        "USER_ID",
                        -1
                );


        if (userId == -1) {

            Toast.makeText(
                    this,
                    "Please login first",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent =
                    new Intent(
                            ProfilePage.this,
                            LoginPage.class
                    );

            startActivity(intent);

            finish();

            return;
        }


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtFirstname =
                findViewById(R.id.txtFirstname);

        txtSurname =
                findViewById(R.id.txtSurname);

        txtEmail =
                findViewById(R.id.txtEmail);

        txtUsername =
                findViewById(R.id.txtUsername);

        btnUpdate =
                findViewById(R.id.btnUpdate);

        btnChangePassword =
                findViewById(R.id.btnChangePassword);


        // =====================================================
        // API
        // =====================================================

        userApi =
                ApiClient
                        .getClient()
                        .create(UserApi.class);


        // =====================================================
        // LOAD PROFILE
        // =====================================================

        loadProfile();


        // =====================================================
        // UPDATE PROFILE
        // =====================================================

        btnUpdate.setOnClickListener(
                view -> updateProfile()
        );


        // =====================================================
        // CHANGE PASSWORD
        // =====================================================

        btnChangePassword.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            ProfilePage.this,
                            ForgotPassword.class
                    );

            startActivity(intent);
        });
    }


    // =====================================================
    // LOAD PROFILE
    // =====================================================

    private void loadProfile() {

        userApi
                .getUser(userId)
                .enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            currentUser =
                                    response.body();


                            txtFirstname.setText(
                                    currentUser.getFirstName()
                            );

                            txtSurname.setText(
                                    currentUser.getLastName()
                            );

                            txtEmail.setText(
                                    currentUser.getEmail()
                            );


                            // There is currently no username
                            // column in the users table.
                            txtUsername.setText(
                                    "User ID: "
                                            + currentUser.getUserId()
                            );

                        } else {

                            Toast.makeText(
                                    ProfilePage.this,
                                    "Unable to load profile. Code: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable throwable) {

                        Toast.makeText(
                                ProfilePage.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }


    // =====================================================
    // UPDATE PROFILE
    // =====================================================

    private void updateProfile() {

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


        // =====================================================
        // VALIDATION
        // =====================================================

        if (TextUtils.isEmpty(firstname)) {

            txtFirstname.setError(
                    "First name is required"
            );

            txtFirstname.requestFocus();

            return;
        }


        if (TextUtils.isEmpty(surname)) {

            txtSurname.setError(
                    "Surname is required"
            );

            txtSurname.requestFocus();

            return;
        }


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
                    "Please enter a valid email address"
            );

            txtEmail.requestFocus();

            return;
        }


        if (currentUser == null) {

            Toast.makeText(
                    this,
                    "Profile has not loaded yet",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =====================================================
        // UPDATE USER OBJECT
        // =====================================================

        currentUser.setFirstName(
                firstname
        );

        currentUser.setLastName(
                surname
        );

        currentUser.setEmail(
                email
        );


        // =====================================================
        // SEND UPDATE TO API
        // =====================================================

        btnUpdate.setEnabled(false);


        userApi
                .updateUser(
                        userId,
                        currentUser
                )
                .enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {

                        btnUpdate.setEnabled(true);


                        if (response.isSuccessful()
                                && response.body() != null) {

                            currentUser =
                                    response.body();


                            // Update locally stored login information

                            SharedPreferences preferences =
                                    getSharedPreferences(
                                            "BiteSizePrefs",
                                            MODE_PRIVATE
                                    );

                            preferences
                                    .edit()
                                    .putString(
                                            "USER_EMAIL",
                                            currentUser.getEmail()
                                    )
                                    .putString(
                                            "USER_NAME",
                                            currentUser.getFirstName()
                                    )
                                    .apply();


                            Toast.makeText(
                                    ProfilePage.this,
                                    "Profile updated successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            Toast.makeText(
                                    ProfilePage.this,
                                    "Unable to update profile. Code: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable throwable) {

                        btnUpdate.setEnabled(true);


                        Toast.makeText(
                                ProfilePage.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}