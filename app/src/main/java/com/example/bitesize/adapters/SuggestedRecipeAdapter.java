package com.example.bitesize.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitesize.R;
import com.example.bitesize.activities.RecipeDetailSuggested;
import com.example.bitesize.models.Recipe;
import com.example.bitesize.models.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipeAdapter
        extends RecyclerView.Adapter<SuggestedRecipeAdapter.RecipeViewHolder> {

    // Context
    private final Context context;

    // Recipe Lists
    private final List<Recipe> recipeList;
    private final List<Recipe> recipeListFull;


    // Constructor
    public SuggestedRecipeAdapter(
            Context context,
            List<Recipe> recipeList) {

        this.context = context;
        this.recipeList = recipeList;

        // Keep original list for searching/filtering
        this.recipeListFull = new ArrayList<>(recipeList);
    }


    // Create RecyclerView Row
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.recipeitem,
                        parent,
                        false
                );

        return new RecipeViewHolder(view);
    }


    // Populate RecyclerView Row
    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe =
                recipeList.get(position);


        // Recipe Name
        holder.txtRecipeName.setText(
                recipe.getName()
        );


        // Preparation Time
        holder.txtRecipePrepTime.setText(
                "Preparation Time: "
                        + recipe.getPrepTime()
                        + " minutes"
        );


        // Instructions
        holder.txtRecipeInstructions.setText(
                recipe.getInstructions()
        );


        // =====================================================
        // SELECT RECIPE
        // =====================================================

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(
                    context,
                    RecipeDetailSuggested.class
            );


            // Send Recipe Name
            intent.putExtra(
                    "recipeName",
                    recipe.getName()
            );


            // Send Recipe ID
            // We will need this later for the API/database
            intent.putExtra(
                    "recipeId",
                    recipe.getId()
            );


            // Send Preparation Time
            intent.putExtra(
                    "prepTime",
                    recipe.getPrepTime()
            );


            // Send Instructions
            intent.putExtra(
                    "instructions",
                    recipe.getInstructions()
            );


            // =================================================
            // BUILD INGREDIENT DISPLAY
            // =================================================

            StringBuilder ingredients =
                    new StringBuilder();


            if (recipe.getIngredients() != null) {

                for (RecipeIngredient ingredient :
                        recipe.getIngredients()) {

                    ingredients
                            .append("• ")
                            .append(
                                    ingredient.getIngredientName()
                            )
                            .append(" - ")
                            .append(
                                    formatQuantity(
                                            ingredient.getQuantity()
                                    )
                            )
                            .append(" ")
                            .append(
                                    ingredient.getUnit()
                            )
                            .append("\n");
                }
            }


            // Send Ingredient Text
            intent.putExtra(
                    "ingredients",
                    ingredients.toString()
            );


            // Send Ingredient Count
            int ingredientCount = 0;

            if (recipe.getIngredients() != null) {

                ingredientCount =
                        recipe.getIngredients().size();
            }

            intent.putExtra(
                    "ingredientCount",
                    ingredientCount
            );


            // Open Suggested Recipe Detail
            context.startActivity(intent);
        });
    }


    // Number of Recipes
    @Override
    public int getItemCount() {

        return recipeList.size();
    }


    // =========================================================
    // SEARCH FILTER
    // =========================================================

    public void filter(String searchText) {

        recipeList.clear();


        // No search - show all suggested recipes
        if (searchText == null ||
                searchText.trim().isEmpty()) {

            recipeList.addAll(
                    recipeListFull
            );

        } else {

            String search =
                    searchText
                            .toLowerCase()
                            .trim();


            for (Recipe recipe :
                    recipeListFull) {

                if (recipe.getName() != null &&
                        recipe.getName()
                                .toLowerCase()
                                .contains(search)) {

                    recipeList.add(recipe);
                }
            }
        }


        // Refresh RecyclerView
        notifyDataSetChanged();
    }


    // =========================================================
    // FORMAT QUANTITY
    // =========================================================

    private String formatQuantity(
            double quantity) {

        // Prevent values like 500.0
        // Display 500 instead
        if (quantity ==
                Math.floor(quantity)) {

            return String.valueOf(
                    (int) quantity
            );
        }

        return String.valueOf(quantity);
    }


    // =========================================================
    // VIEW HOLDER
    // =========================================================

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        TextView txtRecipePrepTime;
        TextView txtRecipeInstructions;


        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);


            // Link RecyclerView Row XML
            txtRecipeName =
                    itemView.findViewById(
                            R.id.txtRecipeName
                    );


            txtRecipePrepTime =
                    itemView.findViewById(
                            R.id.txtRecipePrepTime
                    );


            txtRecipeInstructions =
                    itemView.findViewById(
                            R.id.txtRecipeInstructions
                    );
        }
    }
}