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

    private final Context context;

    private final List<Recipe> recipeList;
    private final List<Recipe> recipeListFull;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public SuggestedRecipeAdapter(
            Context context,
            List<Recipe> recipeList) {

        this.context = context;

        this.recipeList =
                recipeList;

        this.recipeListFull =
                new ArrayList<>(recipeList);
    }


    // =====================================================
    // CREATE RECYCLER VIEW ROW
    // =====================================================

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.recipeitem,
                                parent,
                                false
                        );

        return new RecipeViewHolder(view);
    }


    // =====================================================
    // POPULATE RECYCLER VIEW ROW
    // =====================================================

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
                        + recipe.getPrepTimeMinutes()
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

            Intent intent =
                    new Intent(
                            context,
                            RecipeDetailSuggested.class
                    );


            // Recipe ID
            intent.putExtra(
                    "recipeId",
                    recipe.getRecipeId()
            );


            // Recipe Name
            intent.putExtra(
                    "recipeName",
                    recipe.getName()
            );


            // Preparation Time
            intent.putExtra(
                    "prepTime",
                    recipe.getPrepTimeMinutes()
            );


            // Instructions
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


            intent.putExtra(
                    "ingredients",
                    ingredients.toString()
            );


            int ingredientCount = 0;

            if (recipe.getIngredients() != null) {

                ingredientCount =
                        recipe.getIngredients().size();
            }


            intent.putExtra(
                    "ingredientCount",
                    ingredientCount
            );


            context.startActivity(intent);
        });
    }


    // =====================================================
    // NUMBER OF RECIPES
    // =====================================================

    @Override
    public int getItemCount() {

        return recipeList.size();
    }


    // =====================================================
    // SET RECIPES FROM API
    // =====================================================

    public void setRecipes(
            List<Recipe> recipes) {

        /*
         * recipeList is the list currently displayed
         * by the RecyclerView.
         */
        recipeList.clear();

        recipeList.addAll(
                recipes
        );


        /*
         * recipeListFull stores the complete API result
         * so searching can restore/filter the original list.
         */
        recipeListFull.clear();

        recipeListFull.addAll(
                recipes
        );


        notifyDataSetChanged();
    }


    // =====================================================
    // SEARCH FILTER
    // =====================================================

    public void filter(
            String searchText) {

        recipeList.clear();


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

                    recipeList.add(
                            recipe
                    );
                }
            }
        }


        notifyDataSetChanged();
    }


    // =====================================================
    // FORMAT QUANTITY
    // =====================================================

    private String formatQuantity(
            double quantity) {

        if (quantity ==
                Math.floor(quantity)) {

            return String.valueOf(
                    (int) quantity
            );
        }


        return String.valueOf(
                quantity
        );
    }


    // =====================================================
    // VIEW HOLDER
    // =====================================================

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        TextView txtRecipePrepTime;
        TextView txtRecipeInstructions;


        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);


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