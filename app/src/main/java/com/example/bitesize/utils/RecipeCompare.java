package com.example.bitesize.utils;

import com.example.bitesize.models.Ingredient;
import com.example.bitesize.models.Recipe;
import com.example.bitesize.models.RecipeIngredient;

import java.util.List;

public class RecipeCompare {

    // Checks if the user has ALL ingredients required for a recipe
    public static boolean canMakeRecipe(
            Recipe recipe,
            List<Ingredient> pantry) {

        // Recipe must contain required ingredients
        if (recipe.getIngredients() == null ||
                recipe.getIngredients().isEmpty()) {

            return false;
        }

        // Check every required ingredient
        for (RecipeIngredient required : recipe.getIngredients()) {

            boolean ingredientFound = false;

            // Search user's pantry
            for (Ingredient pantryItem : pantry) {

                // Check ingredient name
                if (ingredientNamesMatch(
                        required.getIngredientName(),
                        pantryItem.getName())) {

                    // Check quantity
                    if (hasEnoughQuantity(
                            pantryItem,
                            required)) {

                        ingredientFound = true;
                        break;
                    }
                }
            }

            // STRICT MATCHING RULE:
            // If even one ingredient is missing
            // or does not have enough quantity,
            // the recipe cannot be suggested.
            if (!ingredientFound) {
                return false;
            }
        }

        // Every required ingredient passed
        return true;
    }


    // Compare ingredient names
    private static boolean ingredientNamesMatch(
            String recipeName,
            String pantryName) {

        String required =
                normalizeIngredientName(recipeName);

        String available =
                normalizeIngredientName(pantryName);

        return required.equals(available);
    }


    // Normalise ingredient names
    // Example:
    // Tomato / Tomatoes
    // Egg / Eggs
    // Onion / Onions
    private static String normalizeIngredientName(String name) {

        if (name == null) {
            return "";
        }

        String normalized =
                name.toLowerCase().trim();


        // Tomatoes -> Tomato
        if (normalized.endsWith("tomatoes")) {

            return normalized.substring(
                    0,
                    normalized.length() - 2
            );
        }


        // Eggs -> Egg
        // Onions -> Onion
        // Carrots -> Carrot
        if (normalized.endsWith("s") &&
                !normalized.endsWith("ss")) {

            normalized = normalized.substring(
                    0,
                    normalized.length() - 1
            );
        }

        return normalized;
    }


    // Check whether pantry contains enough quantity
    private static boolean hasEnoughQuantity(
            Ingredient pantryItem,
            RecipeIngredient required) {

        // First make sure units are compatible
        String pantryUnitType =
                getUnitType(pantryItem.getUnit());

        String requiredUnitType =
                getUnitType(required.getUnit());


        if (!pantryUnitType.equals(requiredUnitType)) {
            return false;
        }


        // Convert quantities to common base units
        double pantryQuantity =
                convertToBaseUnit(
                        pantryItem.getQuantity(),
                        pantryItem.getUnit()
                );

        double requiredQuantity =
                convertToBaseUnit(
                        required.getQuantity(),
                        required.getUnit()
                );


        // Strict quantity check
        return pantryQuantity >= requiredQuantity;
    }


    // Convert units to common base unit
    private static double convertToBaseUnit(
            double quantity,
            String unit) {

        if (unit == null) {
            return quantity;
        }

        switch (unit.toLowerCase().trim()) {

            // Weight
            case "kg":
                return quantity * 1000;

            case "g":
                return quantity;


            // Volume
            case "l":
                return quantity * 1000;

            case "ml":
                return quantity;


            // Individual items
            case "unit":
            case "units":
                return quantity;


            default:
                return quantity;
        }
    }


    // Determine which unit category is being used
    private static String getUnitType(String unit) {

        if (unit == null) {
            return "unknown";
        }

        switch (unit.toLowerCase().trim()) {

            // Weight
            case "kg":
            case "g":
                return "weight";


            // Volume
            case "l":
            case "ml":
                return "volume";


            // Individual items
            case "unit":
            case "units":
                return "count";


            default:
                return "unknown";
        }
    }
}