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
import com.example.bitesize.activities.DetailRecipe;
import com.example.bitesize.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;

    private final List<Recipe> recipeList;
    private final List<Recipe> recipeListFull;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public RecipeAdapter(
            Context context,
            List<Recipe> recipeList) {

        this.context = context;
        this.recipeList = recipeList;

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
    // POPULATE RECIPE ROW
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
        if (recipe.getPrepTimeMinutes() != null) {

            holder.txtRecipePrepTime.setText(
                    "Preparation Time: "
                            + recipe.getPrepTimeMinutes()
                            + " minutes"
            );

        } else {

            holder.txtRecipePrepTime.setText(
                    "Preparation Time: Not specified"
            );
        }


        // Description
        if (recipe.getDescription() != null
                && !recipe.getDescription().isEmpty()) {

            holder.txtRecipeInstructions.setText(
                    recipe.getDescription()
            );

        } else {

            holder.txtRecipeInstructions.setText(
                    recipe.getInstructions()
            );
        }


        // =====================================================
        // OPEN DETAIL PAGE
        // =====================================================

        holder.itemView.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            context,
                            DetailRecipe.class
                    );


            intent.putExtra(
                    "RECIPE_ID",
                    recipe.getRecipeId()
            );


            intent.putExtra(
                    "RECIPE_NAME",
                    recipe.getName()
            );


            intent.putExtra(
                    "RECIPE_DESCRIPTION",
                    recipe.getDescription()
            );


            intent.putExtra(
                    "RECIPE_INSTRUCTIONS",
                    recipe.getInstructions()
            );


            if (recipe.getPrepTimeMinutes() != null) {

                intent.putExtra(
                        "PREP_TIME",
                        recipe.getPrepTimeMinutes()
                );
            }


            if (recipe.getServings() != null) {

                intent.putExtra(
                        "SERVINGS",
                        recipe.getServings()
                );
            }


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

        recipeList.clear();
        recipeListFull.clear();


        if (recipes != null) {

            recipeList.addAll(recipes);

            recipeListFull.addAll(recipes);
        }


        notifyDataSetChanged();
    }


    // =====================================================
    // SEARCH
    // =====================================================

    public void filter(
            String searchText) {

        recipeList.clear();


        if (searchText == null
                || searchText.trim().isEmpty()) {

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

                if (recipe.getName() != null
                        && recipe.getName()
                        .toLowerCase()
                        .contains(search)) {

                    recipeList.add(recipe);
                }
            }
        }


        notifyDataSetChanged();
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