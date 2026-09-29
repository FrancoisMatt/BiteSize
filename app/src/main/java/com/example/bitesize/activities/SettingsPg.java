package com.example.bitesize.activities;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitesize.R;

public class SettingsPg extends AppCompatActivity {

    // Settings Fields
    private Switch switchNotifications;
    private Switch switchExpiryNotifications;
    private Switch switchDarkMode;

    private NumberPicker numberPickerDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);

        // Linking Java with XML Components
        switchNotifications = findViewById(R.id.switchNotifications);
        switchExpiryNotifications = findViewById(R.id.switchExpiryNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);

        numberPickerDays = findViewById(R.id.numberPickerDays);

        // Number Picker Setup
        numberPickerDays.setMinValue(1);
        numberPickerDays.setMaxValue(30);
        numberPickerDays.setValue(5);

        // Push Notifications
        switchNotifications.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    if (isChecked) {
                        Toast.makeText(
                                SettingsPg.this,
                                "Push notifications enabled",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                SettingsPg.this,
                                "Push notifications disabled",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    saveSettings();
                }
        );

        // Expiry Notifications
        switchExpiryNotifications.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    // Disable expiry days when notifications are disabled
                    numberPickerDays.setEnabled(isChecked);

                    if (isChecked) {

                        Toast.makeText(
                                SettingsPg.this,
                                "Expiry notifications enabled",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                SettingsPg.this,
                                "Expiry notifications disabled",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    saveSettings();
                }
        );

        // Expiry Notification Days
        numberPickerDays.setOnValueChangedListener(
                (picker, oldValue, newValue) -> {

                    saveSettings();
                }
        );

        // Dark Mode
        switchDarkMode.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    if (isChecked) {

                        Toast.makeText(
                                SettingsPg.this,
                                "Dark mode enabled",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                SettingsPg.this,
                                "Dark mode disabled",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    /*
                     * Actual theme switching can be
                     * implemented later.
                     */

                    saveSettings();
                }
        );
    }

    // Save Settings
    private void saveSettings() {

        boolean pushNotifications =
                switchNotifications.isChecked();

        boolean expiryNotifications =
                switchExpiryNotifications.isChecked();

        int notificationDays =
                numberPickerDays.getValue();

        boolean darkMode =
                switchDarkMode.isChecked();

        /*
         * API call will be added later.
         *
         * Values to send:
         *
         * pushNotifications
         * expiryNotifications
         * notificationDays
         * darkMode
         */

    }
}