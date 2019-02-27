package com.example.android.mybakery.RecipeDetails;

import android.net.Uri;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import com.example.android.mybakery.RecipeStepDetails.RecipeStepDetails;
import com.example.android.mybakery.RecipeStepDetails.RecipeStepDetailsFragment;
import com.example.android.mybakery.Widget.RecipeWidget;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener{

    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";

    static String SELECTED_RECIPES="Selected_Recipes";
    private ArrayList<Recipe> recipe;

    public static Recipe mrecipe;
    public  int mStepIndex;
    boolean isMasterDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StepsFragment stepsFragment=new StepsFragment();
        //isMasterDetails=stepsFragment.IsmasterDetail;

        if (savedInstanceState != null) {
            mStepIndex = savedInstanceState.getInt(getString(R.string.step_index_tag));
        }
        Intent intent=getIntent();
        if(intent.hasExtra(getString(R.string.recipes_tag))){
            mrecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipes_tag));
            Log.d("lalala", String.valueOf(mrecipe));
            sendRecipeToWidget();


        }
        setContentView(R.layout.activity_recipe_details);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(savedInstanceState ==null) {

                IngredientsFragment fragment = new IngredientsFragment();
            if(fragment !=null) {
                fragment.recipe = mrecipe;
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_ingredients_fragment, fragment)
                        .commit();
            }
                StepsFragment stepsFragment = new StepsFragment();


            stepsFragment.recipe = mrecipe;
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_steps_fragment, stepsFragment);

            Log.d("isMasterDetails Recipe", String.valueOf(isMasterDetails));


            }
        }





    @Override
    public void onStepSelected(int stepIndex) {
        this.mStepIndex = stepIndex;
        Log.d("clicklistnerrrrr", String.valueOf(mStepIndex));

        if (isMasterDetails) {
            //TODO:

            RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();

            Bundle arguments=new Bundle();

            if(recipe != null)
            {
                Log.d("heloo3", mrecipe.getName());
            }
            arguments.putInt("step_index",stepIndex);
            arguments.putSerializable("recipe",mrecipe);
            recipeStepDetailsFragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            //recipeStepDetailsFragment.recipe = recipe;
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe__fragment, recipeStepDetailsFragment)
                    .commit();
                    } else {
            Log.e(" basmaaaRecpieDetails", "index "+stepIndex);

            Intent intent = new Intent(this, RecipeStepDetails.class);
            intent.putExtra(getString(R.string.step_index_tag), stepIndex);
            intent.putExtra(getString(R.string.recipe_tag), mrecipe);
            startActivity(intent);

        }
    }
    private void sendRecipeToWidget() {
        Intent intent = new Intent(this, RecipeWidget.class);
        intent.putExtra(getString(R.string.recipe_tag), mrecipe);
        intent.setAction(getString(R.string.widget_intent_action));
        sendBroadcast(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.step_index_tag), mStepIndex);

    }


}
