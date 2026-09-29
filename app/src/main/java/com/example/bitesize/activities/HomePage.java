package com.example.bitesize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class HomePage extends AppCompatActivity {

    // Main dashboard options
    private LinearLayout btnPantry;
    private LinearLayout btnProfile;
    private LinearLayout btnRecipes;
    private LinearLayout btnSuggestedRecipes;

    // Header options
    private LinearLayout btnSettings;
    private LinearLayout btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Linking Java with XML components
        btnPantry = findViewById(R.id.btnPantry);
        btnProfile = findViewById(R.id.btnProfile);
        btnRecipes = findViewById(R.id.btnRecipes);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);

        btnSettings = findViewById(R.id.btnSettings);
        btnLogout = findViewById(R.id.btnLogout);

        // Pantry
        btnPantry.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, PantryPage.class);
            startActivity(intent);
        });

        // Profile
        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, ProfilePage.class);
            startActivity(intent);
        });

        // Recipes
        btnRecipes.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, RecipesPage.class);
            startActivity(intent);
        });

        // Suggested Recipes
        btnSuggestedRecipes.setOnClickListener(view -> {
            Intent intent = new Intent(
                    HomePage.this,
                    SuggestedRecipesPage.class
            );
            startActivity(intent);
        });

        // Settings
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, SettingsPage.class);
            startActivity(intent);
        });

        // Logout
        btnLogout.setOnClickListener(view -> logout());
    }

    private void logout() {

        /*
         * TODO:
         * Clear the user's login/session information here
         * once authentication has been implemented.
         */

        Toast.makeText(
                HomePage.this,
                "Logged out successfully",
                Toast.LENGTH_SHORT
        ).show();

        // Return user to Login Page
        Intent intent = new Intent(HomePage.this, LoginPage.class);

        // Prevent user from pressing Back and returning to Home
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }
}