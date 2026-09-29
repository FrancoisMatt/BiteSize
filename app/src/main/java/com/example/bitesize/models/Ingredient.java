package com.example.bitesize.models;

public class Ingredient {

    // Fields
    private int pantryItemId;
    private int userId;
    private int ingredientId;

    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;


    // Empty constructor for Retrofit/Gson
    public Ingredient() {
    }


    // Existing constructor
    // Keeps your current test data / strict matcher working
    public Ingredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        this.ingredientId = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }


    // Pantry Item ID

    public int getPantryItemId() {
        return pantryItemId;
    }

    public void setPantryItemId(int pantryItemId) {
        this.pantryItemId = pantryItemId;
    }


    // User ID

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    // Ingredient ID

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }


    // Keep old getId/setId so existing code doesn't break

    public int getId() {
        return ingredientId;
    }

    public void setId(int id) {
        this.ingredientId = id;
    }


    // Name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Quantity

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    // Unit

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    // Expiry Date

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}