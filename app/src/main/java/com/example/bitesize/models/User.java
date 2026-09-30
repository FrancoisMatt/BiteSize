package com.example.bitesize.models;

public class User {

    private int userId;

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;

    private boolean pushNotifications;
    private boolean expiryNotifications;
    private int expiryNotificationDays;
    private boolean darkMode;

    private String createdAt;
    private String updatedAt;


    public User() {
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    public boolean isPushNotifications() {
        return pushNotifications;
    }

    public void setPushNotifications(boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }


    public boolean isExpiryNotifications() {
        return expiryNotifications;
    }

    public void setExpiryNotifications(boolean expiryNotifications) {
        this.expiryNotifications = expiryNotifications;
    }


    public int getExpiryNotificationDays() {
        return expiryNotificationDays;
    }

    public void setExpiryNotificationDays(int expiryNotificationDays) {
        this.expiryNotificationDays = expiryNotificationDays;
    }


    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUpdatedAt() {

        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}