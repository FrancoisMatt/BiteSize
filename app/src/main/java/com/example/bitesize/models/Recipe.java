package com.example.bitesize.models;

import java.util.List;

public class Recipe {

    // Model fields
    private int id;
    private String name;
    private String instructions;
    private int prepTime;

    // Required ingredients for strict recipe matching
    private List<RecipeIngredient> ingredients;


    // Constructor used by normal Recipes page
    public Recipe(
            int id,
            String name,
            String instructions,
            int prepTime) {

        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.prepTime = prepTime;
    }


    // Constructor used by Suggested Recipes
    public Recipe(
            int id,
            String name,
            String instructions,
            int prepTime,
            List<RecipeIngredient> ingredients) {

        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.prepTime = prepTime;
        this.ingredients = ingredients;
    }


    // Getters && Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }


    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(
            List<RecipeIngredient> ingredients) {

        this.ingredients = ingredients;
    }
}