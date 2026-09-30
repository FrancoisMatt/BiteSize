package com.example.bitesize.models;

import java.util.List;

public class Recipe {

    // =====================================================
    // MODEL FIELDS
    // =====================================================

    private Integer recipeId;

    private String name;

    private String description;

    private String instructions;

    private Integer prepTimeMinutes;

    private Integer servings;

    private String createdAt;

    private String updatedAt;

    private List<RecipeIngredient> ingredients;


    // =====================================================
    // EMPTY CONSTRUCTOR
    // REQUIRED FOR API / JSON MAPPING
    // =====================================================

    public Recipe() {
    }


    // =====================================================
    // GETTERS AND SETTERS
    // =====================================================

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
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


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public Integer getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }


    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
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


    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(
            List<RecipeIngredient> ingredients) {

        this.ingredients = ingredients;
    }
}