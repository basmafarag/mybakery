package com.example.android.mybakery.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.mybakery.Model.Recipes;
import com.example.android.mybakery.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.android.mybakery.Recipe.MainActivity;
import com.squareup.picasso.Picasso;

import static android.provider.Settings.System.getString;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    public static List<Recipes> recipes;
    private static RecipesOnClickHandler recipesOnClickHandler;

    public RecipesAdapter( RecipesOnClickHandler recipesOnClickHandler) {
        RecipesAdapter.recipesOnClickHandler = recipesOnClickHandler;

    }


    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForMovieItem = R.layout.recipe_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;
        View itemView = layoutInflater.inflate(layoutIdForMovieItem, parent, shouldAttachToParentImmediately);
        return new RecipesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Recipes recipe = recipes.get(position);
        holder.recipeTitleTextView.setText(recipe.getName());
        String recipeImage = recipe.getImage().isEmpty() ? null : recipe.getImage();
        Picasso.get().load(recipeImage).placeholder(R.drawable.dessert)
                .error(R.drawable.dessert).into(holder.recipeImageView);
    }

    @Override
    public int getItemCount() {
        return recipes == null ?  0 : recipes.size();
    }

    public interface RecipesOnClickHandler {

        void onClick(Recipes recipe);
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final CardView recipeCardView;
        final TextView recipeTitleTextView;
        final ImageView recipeImageView;

        RecipesViewHolder(View itemView) {
            super(itemView);
            recipeTitleTextView = itemView.findViewById(R.id.tv_recipe_title);
            recipeCardView = itemView.findViewById(R.id.recipe_item);
            recipeImageView = itemView.findViewById(R.id.iv_recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipes recipe = recipes.get(adapterPosition);
            recipesOnClickHandler.onClick(recipe);
        }
    }


}
