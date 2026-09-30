package com.example.bitesize.models;

public class Ingredient {

    // =====================================================
    // FIELDS
    // =====================================================

    // Pantry record ID
    // Used for updating and deleting a pantry item
    private int pantryItemId;

    // User who owns the pantry item
    private int userId;

    // Ingredient master record ID
    // Used for recipe matching
    private int ingredientId;

    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;


    // =====================================================
    // EMPTY CONSTRUCTOR
    // Required for Retrofit / Gson
    // =====================================================

    public Ingredient() {
    }


    // =====================================================
    // EXISTING CONSTRUCTOR
    // Used by current recipe matching / temporary data
    // =====================================================

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


    // =====================================================
    // API CONSTRUCTOR
    // Used when creating/updating pantry items
    // =====================================================

    public Ingredient(
            int userId,
            int ingredientId,
            double quantity,
            String unit,
            String expiryDate) {

        this.userId = userId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }


    // =====================================================
    // PANTRY ITEM ID
    // =====================================================

    public int getPantryItemId() {
        return pantryItemId;
    }

    public void setPantryItemId(int pantryItemId) {
        this.pantryItemId = pantryItemId;
    }


    // =====================================================
    // USER ID
    // =====================================================

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    // =====================================================
    // INGREDIENT ID
    // =====================================================

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }


    // =====================================================
    // OLD ID METHODS
    // Keep these so existing code does not break
    // =====================================================

    public int getId() {
        return ingredientId;
    }

    public void setId(int id) {
        this.ingredientId = id;
    }


    // =====================================================
    // NAME
    // =====================================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // =====================================================
    // QUANTITY
    // =====================================================

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    // =====================================================
    // UNIT
    // =====================================================

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    // =====================================================
    // EXPIRY DATE
    // =====================================================

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}