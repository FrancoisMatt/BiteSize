package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class ForgotPassword extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtNewPassword;
    private EditText txtConfirmPassword;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        //Linking Java with XML components
        txtUsername = findViewById(R.id.txtUsername);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        //Submit change to the backend and change password
        btnSubmit.setOnClickListener(view-> changePassword());
    }

    private void changePassword() {

        //Action and validation of password change
        String username = txtUsername.getText().toString().trim();
        String NewPassword = txtNewPassword.getText().toString().trim();
        String ConfirmPassword = txtConfirmPassword.getText().toString().trim();

        //username field is empty
        if(TextUtils.isEmpty(username)) {
            txtUsername.setError("Username required");
            txtUsername.requestFocus();
            return;
        }

        //Username not found in Database will need to be an API call
        if(!TextUtils.equals(Username)) {
            txtUsername.setError("Username doesn't exist!!");
            txtUsername.requestFocus();
            return;
        }

        //NewPassword field not completed
        if (NewPassword.isEmpty()) {
            txtNewPassword.setError("Please enter a new password");
            return;
        }

        //ConfirmPassword is empty
        if (ConfirmPassword.isEmpty()) {
            txtConfirmPassword.setError("Please confirm your password");
            return;
        }

        //ConfirmPassword doesn't match NewPassword
        if (!NewPassword.equals(ConfirmPassword)) {
            txtConfirmPassword.setError("Passwords do not match");
            return;
        }

        boolean passwordChanged = true; // Have to change this with API calls

        if (changePassword) {

            Toast.makeText(
                    this,
                    "Password changed successfully",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "An error occurred while changing your password",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
