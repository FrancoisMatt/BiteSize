package com.example.bitesize.models;

public class IngredientOption {

    private int ingredientId;
    private String name;
    private String description;


    public IngredientOption() {
    }


    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Spinner will display the ingredient name
    @Override
    public String toString() {
        return name;
    }
}