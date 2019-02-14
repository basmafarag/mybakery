package com.example.android.mybakery.RecipeDetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";

    static String SELECTED_RECIPES="Selected_Recipes";
    private ArrayList<Recipe> recipe;

    public Recipe mrecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        if(intent.hasExtra(getString(R.string.recipes_tag))){
            mrecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipes_tag));
            Log.d("lalala", String.valueOf(mrecipe));

        }
        setContentView(R.layout.activity_recipe_details);

        if(savedInstanceState ==null){


            IngredientsFragment fragment=new IngredientsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragment.recipe=mrecipe;
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_ingredients_fragment,fragment)
                    .commit();


    }

       }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}
