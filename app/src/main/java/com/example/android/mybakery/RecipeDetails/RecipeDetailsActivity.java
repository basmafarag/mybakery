package com.example.android.mybakery.RecipeDetails;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import com.example.android.mybakery.RecipeStepDetails.RecipeStepDetails;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener{

    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";

    static String SELECTED_RECIPES="Selected_Recipes";
    private ArrayList<Recipe> recipe;

    public  Recipe mrecipe;
    public  int mStepIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mStepIndex = savedInstanceState.getInt(getString(R.string.step_index_tag));
        }

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

            StepsFragment stepsFragment=new StepsFragment();

            stepsFragment.recipe=mrecipe;
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_steps_fragment,stepsFragment);

    }

       }



    @Override
    public void onStepSelected(int stepIndex) {
        this.mStepIndex=stepIndex;
        Log.d("clicklistnerrrrr", String.valueOf(mStepIndex));

        Intent intent=new Intent(this, RecipeStepDetails.class);
        intent.putExtra(getString(R.string.step_index_tag), stepIndex);
        intent.putExtra(getString(R.string.recipe_tag), mrecipe);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.step_index_tag), mStepIndex);

    }


}
