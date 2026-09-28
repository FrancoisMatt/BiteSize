package com.example.bitesize.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class CreateAccount extends AppCompatActivity {

    //Fields specific for Account Creation
    private EditText txtFirstname;
    private EditText txtSurname;
    private EditText txtUsername;

    private EditText txtEmail;

    private EditText txtPassword;

    private Button btnCreate;

    //Override to use the XML specific layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);


    //Linking Java with XML Components
    txtFirstname = findViewById(R.id.txtFirstname);
    txtSurname = findViewById(R.id.txtSurname);
    txtUsername = findViewById(R.id.txtUsername);
    txtEmail = findViewById(R.id.txtEmail);
    txtPassword = findViewById(R.id.txtPassword);

    //Button
    btnCreate.setOnClickListener(view -> CreateUser());

}

private void CreateUser() {

    //String Inputs
    String Firstname = txtFirstname.getText().toString().trim();
    String Surname = txtSurname.getText().toString().trim();
    String Email = txtEmail.getText().toString().trim();
    String Username = txtUsername.getText().toString().trim();
    String Password = txtPassword.getText().toString().trim();
    ;

    //String Validation frontend
    if (TextUtils.isEmpty(Firstname)) {
        txtUsername.setError("First Name is required");
        txtUsername.requestFocus();
        return;
    }

    if (TextUtils.isEmpty(Surname)) {
        txtPassword.setError("Surname is required");
        txtPassword.requestFocus();
        return;
    }

    if (TextUtils.isEmpty(Username)) {
        txtUsername.setError("Username is required");
        txtUsername.requestFocus();
        return;
    }

    if (TextUtils.isEmpty(Email)) {
        txtPassword.setError("Email is required");
        txtPassword.requestFocus();
        return;
    }

    if (TextUtils.isEmpty(Password)) {
        txtPassword.setError("Password is required");
        txtPassword.requestFocus();
        return;
    }

    // Database/API login will be added later
    Toast.makeText(
            CreateAccount.this,
            "Login details entered successfully",
            Toast.LENGTH_SHORT
    ).show();
}

}
