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

    private List<Recipe> recipeList;
    private final List<Recipe> recipeListFull;

    public RecipeAdapter(
            Context context,
            List<Recipe> recipeList) {

        this.context = context;
        this.recipeList = recipeList;

        // Keep original list for searching
        this.recipeListFull =
                new ArrayList<>(recipeList);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.recipesdetailpage,
                        parent,
                        false
                );

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe = recipeList.get(position);

        // Recipe Name
        holder.txtRecipeName.setText(
                recipe.getName()
        );

        // Description
        holder.txtInstructions.setText(
                recipe.getInstructions()
        );

        // Preparation Time
        holder.txtRecipePrepTime.setText(
                "Preparation Time: "
                        + recipe.getPrepTime()
                        + " minutes"
        );

        // Open selected Recipe
        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(
                    context,
                    DetailRecipe.class
            );

            intent.putExtra(
                    "RECIPE_ID",
                    recipe.getId()
            );

            intent.putExtra(
                    "RECIPE_NAME",
                    recipe.getName()
            );

            intent.putExtra(
                    "RECIPE_DESCRIPTION",
                    recipe.getInstructions()
            );

            intent.putExtra(
                    "PREP_TIME",
                    recipe.getPrepTime()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Search Recipes
    public void filter(String searchText) {

        recipeList.clear();

        if (searchText.isEmpty()) {

            recipeList.addAll(recipeListFull);

        } else {

            String search =
                    searchText.toLowerCase().trim();

            for (Recipe recipe : recipeListFull) {

                if (recipe
                        .getName()
                        .toLowerCase()
                        .contains(search)) {

                    recipeList.add(recipe);
                }
            }
        }

        notifyDataSetChanged();
    }

    // ViewHolder
    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        TextView txtInstructions;
        TextView txtRecipePrepTime;

        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtRecipeName =
                    itemView.findViewById(
                            R.id.txtRecipeName
                    );

            txtInstructions =
                    itemView.findViewById(
                            R.id.txtInstructions
                    );

            txtRecipePrepTime =
                    itemView.findViewById(
                            R.id.txtPrepTime
                    );
        }
    }
}