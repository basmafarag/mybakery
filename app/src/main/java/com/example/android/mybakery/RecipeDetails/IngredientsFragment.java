package com.example.android.mybakery.RecipeDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mybakery.Adapter.IngredientsAdapter;
import com.example.android.mybakery.Model.Ingredients;
import com.example.android.mybakery.Model.Recipes;
import com.example.android.mybakery.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {
        Recipes recipe;


    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecipeDetailsActivity RecipeDetailsActivity =(RecipeDetailsActivity) getActivity();

        final View rootView=inflater.inflate(R.layout.fragment_ingredients,container,false);
        initRecyclerView(RecipeDetailsActivity,rootView);
        return rootView;
    }

    private void initRecyclerView(RecipeDetailsActivity RecipeDetailsActivity, View rootView) {
        RecyclerView recyclerView=rootView.findViewById(R.id.rv_Ingredients_List);
       //ya5od el ingredients list

        IngredientsAdapter ingredientsAdapter=new IngredientsAdapter(RecipeDetailsActivity.mrecipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setHasFixedSize(false);

    }
}
