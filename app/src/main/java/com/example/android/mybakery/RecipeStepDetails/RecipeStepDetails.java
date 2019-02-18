package com.example.android.mybakery.RecipeStepDetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import android.content.Intent;
import com.example.android.mybakery.Model.Step;
import com.example.android.mybakery.RecipeDetails.IngredientsFragment;

public class RecipeStepDetails extends AppCompatActivity {

    public int stepIndex;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.step_index_tag))) {
            stepIndex = (int) intent.getSerializableExtra("step_index");
            recipe = (Recipe) intent.getSerializableExtra("recipe");
            Step step = recipe.getStepList().get(stepIndex);


        }
        if (savedInstanceState == null) {


            RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
            Bundle arguments=new Bundle();

            arguments.putInt("step_index",stepIndex);
            arguments.putSerializable("recipe",recipe);
            recipeStepDetailsFragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            recipeStepDetailsFragment.recipe = recipe;
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe__fragment, recipeStepDetailsFragment)
                    .commit();
            setContentView(R.layout.activity_recipe_step_details);
        }
    }
}
