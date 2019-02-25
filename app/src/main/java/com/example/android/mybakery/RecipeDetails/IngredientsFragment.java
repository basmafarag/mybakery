package com.example.android.mybakery.RecipeDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mybakery.Adapter.IngredientsAdapter;
import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import com.example.android.mybakery.Widget.RecipeWidget;

public class IngredientsFragment extends Fragment {
        Recipe recipe;
        TextView IngredientsWidget;

    public IngredientsFragment() {
    }
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecipeDetailsActivity RecipeDetailsActivity =(RecipeDetailsActivity) getActivity();

        final View rootView=inflater.inflate(R.layout.fragment_ingredients,container,false);
        IngredientsWidget=rootView.findViewById(R.id.widget);
        initRecyclerView(RecipeDetailsActivity,rootView);
        initIngredientsTextViewOnClickListener();

        return rootView;
    }
    private void initIngredientsTextViewOnClickListener() {
        IngredientsWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), getString(R.string.widget), Toast.LENGTH_LONG).show();
               // sendRecipeToWidget();

            }
        });
    }
    private void sendRecipeToWidget() {
      //  Intent intent = new Intent(this, RecipeWidget.class);
       // intent.putExtra(getString(R.string.recipe_tag), recipe);
        //intent.setAction(getString(R.string.widget_intent_action));
        //sendBroadcast(intent);
    }

    private void initRecyclerView(RecipeDetailsActivity RecipeDetailsActivity, View rootView) {
        RecyclerView recyclerView=rootView.findViewById(R.id.rv_Ingredients_List);
       //ya5od el ingredients list
        Log.d("ingredientsss", String.valueOf(RecipeDetailsActivity.mrecipe));

        IngredientsAdapter ingredientsAdapter=new IngredientsAdapter(RecipeDetailsActivity.mrecipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setHasFixedSize(false);

    }
}
