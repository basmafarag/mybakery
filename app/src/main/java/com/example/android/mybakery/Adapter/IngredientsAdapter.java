package com.example.android.mybakery.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybakery.Model.Ingredient;
import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    public static List<Ingredient> ingredients;
    private Context context;






    public IngredientsAdapter(Recipe recipe) {
        ingredients=recipe.getIngredientList();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context=viewGroup.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view= layoutInflater
                .inflate(R.layout.ingredient_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder viewHolder, int i) {
        String iTitle=ingredients.get(i).getIngredient();
        String  measure=ingredients.get(i).getMeasure();
        double quantity=ingredients.get(i).getQuantity();
        String amount;
       // amount = context.getString(R.string.recipes_tag,Double.toString(quantity),measure);

        viewHolder.ingredientTitle.setText(iTitle);
        viewHolder.ingredientAmount.setText(measure);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView ingredientTitle;
        final TextView ingredientAmount;

        ViewHolder(View itemView){
            super(itemView);
            ingredientTitle=itemView.findViewById(R.id.tv_ingredient);
            ingredientAmount=itemView.findViewById(R.id.tv_amount);

        }

    }

}
