package com.example.bitesize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;
import com.example.bitesize.models.ResetPasswordRequest;
import com.example.bitesize.models.User;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    // =====================================================
    // FIELDS
    // =====================================================

    private EditText txtEmail;
    private EditText txtNewPassword;
    private EditText txtConfirmPassword;

    private Button btnSubmit;

    private UserApi userApi;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgotpassword);


        // =====================================================
        // LINK JAVA WITH XML
        // =====================================================

        txtEmail =
                findViewById(R.id.txtEmail);

        txtNewPassword =
                findViewById(R.id.txtNewPassword);

        txtConfirmPassword =
                findViewById(R.id.txtConfirmPassword);

        btnSubmit =
                findViewById(R.id.btnSubmit);


        // =====================================================
        // API
        // =====================================================

        userApi =
                ApiClient
                        .getClient()
                        .create(UserApi.class);


        // =====================================================
        // RESET PASSWORD
        // =====================================================

        btnSubmit.setOnClickListener(
                view -> changePassword()
        );
    }


    // =====================================================
    // CHANGE PASSWORD
    // =====================================================

    private void changePassword() {

        String email =
                txtEmail
                        .getText()
                        .toString()
                        .trim();


        String newPassword =
                txtNewPassword
                        .getText()
                        .toString()
                        .trim();


        String confirmPassword =
                txtConfirmPassword
                        .getText()
                        .toString()
                        .trim();


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

        if (TextUtils.isEmpty(newPassword)) {

            txtNewPassword.setError(
                    "Please enter a new password"
            );

            txtNewPassword.requestFocus();

            return;
        }


        if (newPassword.length() < 6) {

            txtNewPassword.setError(
                    "Password must be at least 6 characters"
            );

            txtNewPassword.requestFocus();

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


        if (!newPassword.equals(confirmPassword)) {

            txtConfirmPassword.setError(
                    "Passwords do not match"
            );

            txtConfirmPassword.requestFocus();

            return;
        }


        // =====================================================
        // CREATE REQUEST
        // =====================================================

        ResetPasswordRequest request =
                new ResetPasswordRequest(
                        email,
                        newPassword
                );


        // =====================================================
        // CALL API
        // =====================================================

        btnSubmit.setEnabled(false);


        userApi
                .resetPassword(request)
                .enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {


                        btnSubmit.setEnabled(true);


                        // =====================================
                        // SUCCESS
                        // =====================================

                        if (response.isSuccessful()) {


                            Toast.makeText(
                                    ForgotPassword.this,
                                    "Password reset successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                            // Return to Login

                            Intent intent =
                                    new Intent(
                                            ForgotPassword.this,
                                            LoginPage.class
                                    );


                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            );


                            startActivity(intent);

                            finish();
                        }


                        // =====================================
                        // EMAIL NOT FOUND
                        // =====================================

                        else if (response.code() == 404) {


                            txtEmail.setError(
                                    "No account found with this email"
                            );

                            txtEmail.requestFocus();
                        }


                        // =====================================
                        // OTHER ERROR
                        // =====================================

                        else {


                            Toast.makeText(
                                    ForgotPassword.this,
                                    "Unable to reset password. Code: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable throwable) {


                        btnSubmit.setEnabled(true);


                        Toast.makeText(
                                ForgotPassword.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}