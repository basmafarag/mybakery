package com.example.android.mybakery.RecipeStepDetails;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import android.content.Intent;
import android.view.WindowManager;

import com.example.android.mybakery.Model.Step;
import com.example.android.mybakery.RecipeDetails.IngredientsFragment;

public class RecipeStepDetails extends AppCompatActivity {

    public int stepIndex;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateActionBarVisibility();

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.step_index_tag))) {
            stepIndex = (int) intent.getSerializableExtra("step_index");
            recipe = (Recipe) intent.getSerializableExtra("recipe");
            Step step = recipe.getStepList().get(stepIndex);


        }
        //if (savedInstanceState == null) {


            RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
            Bundle arguments=new Bundle();

            arguments.putInt("step_index",stepIndex);
            arguments.putSerializable("recipe",recipe);
            recipeStepDetailsFragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            //recipeStepDetailsFragment.recipe = recipe;
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe__fragment, recipeStepDetailsFragment)
                    .commit();
            setContentView(R.layout.activity_recipe_step_details);

    }
    private void updateActionBarVisibility() {
        // https://stackoverflow.com/questions/11856886/hiding-title-bar-notification-bar-when-device-is-oriented-to-landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

}
