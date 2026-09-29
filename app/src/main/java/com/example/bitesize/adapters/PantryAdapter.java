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
import com.example.bitesize.activities.IngredientPage;
import com.example.bitesize.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.IngredientViewHolder> {

    private final Context context;

    private List<Ingredient> ingredientList;
    private List<Ingredient> ingredientListFull;

    public PantryAdapter(
            Context context,
            List<Ingredient> ingredientList) {

        this.context = context;
        this.ingredientList = ingredientList;
        this.ingredientListFull = new ArrayList<>(ingredientList);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.itemingredient,
                        parent,
                        false
                );

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder,
            int position) {

        Ingredient ingredient = ingredientList.get(position);

        // Ingredient Name
        holder.txtIngredientName.setText(
                ingredient.getName()
        );

        // Quantity
        holder.txtIngredientQuantity.setText(
                "Quantity: "
                        + ingredient.getQuantity()
                        + " "
                        + ingredient.getUnit()
        );

        // Expiry Date
        holder.txtIngredientExpiry.setText(
                "Expiry: " + ingredient.getExpiryDate()
        );

        // Ingredient selected
        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(
                    context,
                    IngredientPage.class
            );

            intent.putExtra(
                    "INGREDIENT_ID",
                    ingredient.getId()
            );

            intent.putExtra(
                    "INGREDIENT_NAME",
                    ingredient.getName()
            );

            intent.putExtra(
                    "QUANTITY",
                    ingredient.getQuantity()
            );

            intent.putExtra(
                    "UNIT",
                    ingredient.getUnit()
            );

            intent.putExtra(
                    "EXPIRY_DATE",
                    ingredient.getExpiryDate()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    // Search / Filter
    public void filter(String searchText) {

        ingredientList.clear();

        if (searchText.isEmpty()) {

            ingredientList.addAll(ingredientListFull);

        } else {

            String search =
                    searchText.toLowerCase().trim();

            for (Ingredient ingredient : ingredientListFull) {

                if (ingredient
                        .getName()
                        .toLowerCase()
                        .contains(search)) {

                    ingredientList.add(ingredient);
                }
            }
        }

        notifyDataSetChanged();
    }

    // ViewHolder
    public static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtIngredientName;
        TextView txtIngredientQuantity;
        TextView txtIngredientExpiry;

        public IngredientViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtIngredientName =
                    itemView.findViewById(
                            R.id.txtIngredientName
                    );

            txtIngredientQuantity =
                    itemView.findViewById(
                            R.id.txtIngredientQuantity
                    );

            txtIngredientExpiry =
                    itemView.findViewById(
                            R.id.txtIngredientExpiry
                    );
        }
    }
}