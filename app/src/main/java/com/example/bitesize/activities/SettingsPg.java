package com.example.bitesize.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.bitesize.R;
import com.example.bitesize.models.User;
import com.example.bitesize.network.ApiClient;
import com.example.bitesize.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsPg extends AppCompatActivity {

    // =====================================================
    // VIEWS
    // =====================================================

    private Switch switchNotifications;
    private Switch switchExpiryNotifications;
    private Switch switchDarkMode;

    private NumberPicker numberPickerDays;


    // =====================================================
    // API
    // =====================================================

    private UserApi userApi;

    private int userId = -1;


    // =====================================================
    // CURRENT USER
    // =====================================================

    private User currentUser;


    /*
     * Prevents API updates while the initial values
     * are being loaded into the controls.
     */
    private boolean settingsLoaded = false;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.settingspage);


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
                            SettingsPg.this,
                            LoginPage.class
                    );


            startActivity(intent);

            finish();

            return;
        }


        // =====================================================
        // LINK VIEWS
        // =====================================================

        switchNotifications =
                findViewById(
                        R.id.switchNotifications
                );


        switchExpiryNotifications =
                findViewById(
                        R.id.switchExpiryNotifications
                );


        switchDarkMode =
                findViewById(
                        R.id.switchDarkMode
                );


        numberPickerDays =
                findViewById(
                        R.id.numberPickerDays
                );


        // =====================================================
        // NUMBER PICKER
        // =====================================================

        numberPickerDays.setMinValue(1);

        numberPickerDays.setMaxValue(30);


        // =====================================================
        // API
        // =====================================================

        userApi =
                ApiClient
                        .getClient()
                        .create(UserApi.class);


        // =====================================================
        // LISTENERS
        // =====================================================

        setupListeners();


        // =====================================================
        // LOAD SETTINGS FROM DATABASE
        // =====================================================

        loadUserSettings();
    }


    // =====================================================
    // LOAD USER SETTINGS
    // =====================================================

    private void loadUserSettings() {

        settingsLoaded = false;


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


                            // =============================================
                            // PUSH NOTIFICATIONS
                            // =============================================

                            switchNotifications.setChecked(
                                    currentUser
                                            .isPushNotifications()
                            );


                            // =============================================
                            // EXPIRY NOTIFICATIONS
                            // =============================================

                            switchExpiryNotifications.setChecked(
                                    currentUser
                                            .isExpiryNotifications()
                            );


                            // =============================================
                            // EXPIRY DAYS
                            // =============================================

                            int days =
                                    currentUser
                                            .getExpiryNotificationDays();


                            if (days < 1) {

                                days = 1;
                            }


                            if (days > 30) {

                                days = 30;
                            }


                            numberPickerDays.setValue(
                                    days
                            );


                            numberPickerDays.setEnabled(
                                    currentUser
                                            .isExpiryNotifications()
                            );


                            // =============================================
                            // DARK MODE SWITCH
                            // =============================================

                            switchDarkMode.setChecked(
                                    currentUser.isDarkMode()
                            );


                            // =============================================
                            // APPLY SAVED DARK MODE
                            // =============================================

                            boolean darkModeEnabled =
                                    currentUser.isDarkMode();


                            int requiredMode;

                            if (darkModeEnabled) {

                                requiredMode =
                                        AppCompatDelegate
                                                .MODE_NIGHT_YES;

                            } else {

                                requiredMode =
                                        AppCompatDelegate
                                                .MODE_NIGHT_NO;
                            }


                            /*
                             * Only change the mode when necessary.
                             * This helps prevent unnecessary
                             * Activity recreation.
                             */

                            if (AppCompatDelegate
                                    .getDefaultNightMode()
                                    != requiredMode) {


                                AppCompatDelegate
                                        .setDefaultNightMode(
                                                requiredMode
                                        );
                            }


                            settingsLoaded = true;


                        } else {


                            Toast.makeText(
                                    SettingsPg.this,
                                    "Unable to load settings. Code: "
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
                                SettingsPg.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }


    // =====================================================
    // LISTENERS
    // =====================================================

    private void setupListeners() {


        // =================================================
        // PUSH NOTIFICATIONS
        // =================================================

        switchNotifications
                .setOnCheckedChangeListener(
                        (buttonView, isChecked) -> {


                            if (!settingsLoaded) {

                                return;
                            }


                            saveSettings();
                        }
                );


        // =================================================
        // EXPIRY NOTIFICATIONS
        // =================================================

        switchExpiryNotifications
                .setOnCheckedChangeListener(
                        (buttonView, isChecked) -> {


                            numberPickerDays.setEnabled(
                                    isChecked
                            );


                            if (!settingsLoaded) {

                                return;
                            }


                            saveSettings();
                        }
                );


        // =================================================
        // EXPIRY DAYS
        // =================================================

        numberPickerDays
                .setOnValueChangedListener(
                        (picker,
                         oldValue,
                         newValue) -> {


                            if (!settingsLoaded) {

                                return;
                            }


                            saveSettings();
                        }
                );


        // =================================================
        // DARK MODE
        // =================================================

        switchDarkMode
                .setOnCheckedChangeListener(
                        (buttonView, isChecked) -> {


                            if (!settingsLoaded) {

                                return;
                            }


                            // =============================================
                            // SAVE SETTING
                            // =============================================

                            saveSettings();


                            // =============================================
                            // CHANGE APP THEME
                            // =============================================

                            if (isChecked) {


                                AppCompatDelegate
                                        .setDefaultNightMode(
                                                AppCompatDelegate
                                                        .MODE_NIGHT_YES
                                        );


                            } else {


                                AppCompatDelegate
                                        .setDefaultNightMode(
                                                AppCompatDelegate
                                                        .MODE_NIGHT_NO
                                        );
                            }
                        }
                );
    }


    // =====================================================
    // SAVE SETTINGS
    // =====================================================

    private void saveSettings() {


        if (currentUser == null) {

            return;
        }


        // =================================================
        // UPDATE CURRENT USER OBJECT
        // =================================================

        currentUser.setPushNotifications(
                switchNotifications.isChecked()
        );


        currentUser.setExpiryNotifications(
                switchExpiryNotifications.isChecked()
        );


        currentUser.setExpiryNotificationDays(
                numberPickerDays.getValue()
        );


        currentUser.setDarkMode(
                switchDarkMode.isChecked()
        );


        // =================================================
        // UPDATE USER THROUGH API
        // =================================================

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


                            Toast.makeText(
                                    SettingsPg.this,
                                    "Settings saved",
                                    Toast.LENGTH_SHORT
                            ).show();


                        } else {


                            Toast.makeText(
                                    SettingsPg.this,
                                    "Unable to save settings. Code: "
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
                                SettingsPg.this,
                                "API Error: "
                                        + throwable.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}