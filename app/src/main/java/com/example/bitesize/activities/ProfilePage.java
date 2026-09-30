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

    // Fields
    private EditText txtFirstname;
    private EditText txtSurname;
    private EditText txtEmail;
    private EditText txtUsername;

    // Buttons
    private Button btnUpdate;
    private Button btnChangePassword;

    // API
    private UserApi userApi;

    // Logged-in user
    private int userId;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);


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
        // GET LOGGED-IN USER ID
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


        // Check whether user ID exists
        if (userId == -1) {

            Toast.makeText(
                    ProfilePage.this,
                    "No logged-in user found",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }


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


    // =========================================================
    // LOAD PROFILE FROM API
    // =========================================================

    private void loadProfile() {

        userApi
                .getUserById(userId)
                .enqueue(new Callback<User>() {


                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {


                        if (response.isSuccessful()
                                && response.body() != null) {


                            currentUser =
                                    response.body();


                            // First Name
                            txtFirstname.setText(
                                    currentUser.getFirstName()
                            );


                            // Last Name
                            txtSurname.setText(
                                    currentUser.getLastName()
                            );


                            // Email
                            txtEmail.setText(
                                    currentUser.getEmail()
                            );


                            /*
                             * Your database does not currently
                             * have a separate username field.
                             *
                             * The app currently uses email
                             * as the login username.
                             */
                            txtUsername.setText(
                                    currentUser.getEmail()
                            );


                        } else {


                            Toast.makeText(
                                    ProfilePage.this,
                                    "Unable to load profile",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable t) {


                        Toast.makeText(
                                ProfilePage.this,
                                "Unable to connect to server: "
                                        + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }


    // =========================================================
    // UPDATE PROFILE
    // =========================================================

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


        // =====================================================
        // MAKE SURE PROFILE WAS LOADED
        // =====================================================

        if (currentUser == null) {

            Toast.makeText(
                    ProfilePage.this,
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


                        if (response.isSuccessful()
                                && response.body() != null) {


                            currentUser =
                                    response.body();


                            // Update locally saved details
                            SharedPreferences preferences =
                                    getSharedPreferences(
                                            "BiteSizePrefs",
                                            MODE_PRIVATE
                                    );


                            preferences
                                    .edit()

                                    .putString(
                                            "USER_FIRST_NAME",
                                            currentUser.getFirstName()
                                    )

                                    .putString(
                                            "USER_LAST_NAME",
                                            currentUser.getLastName()
                                    )

                                    .putString(
                                            "USER_EMAIL",
                                            currentUser.getEmail()
                                    )

                                    .apply();


                            // Update username display
                            txtUsername.setText(
                                    currentUser.getEmail()
                            );


                            Toast.makeText(
                                    ProfilePage.this,
                                    "Profile updated successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                        } else {


                            Toast.makeText(
                                    ProfilePage.this,
                                    "Unable to update profile",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable t) {


                        Toast.makeText(
                                ProfilePage.this,
                                "Unable to connect to server: "
                                        + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}