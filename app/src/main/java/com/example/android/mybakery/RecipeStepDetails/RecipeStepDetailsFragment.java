package com.example.android.mybakery.RecipeStepDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mybakery.R;
import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.RecipeDetails.RecipeDetailsActivity;

public class RecipeStepDetailsFragment extends Fragment {
    static Recipe recipe;
    static int stepIndex;

    public RecipeStepDetailsFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            stepIndex=savedInstanceState.getInt("step_index");
            recipe=(Recipe) savedInstanceState.getSerializable("recipe");
        }
        else{
            if(getActivity() instanceof RecipeDetailsActivity){
                RecipeDetailsActivity activity=(RecipeDetailsActivity) getActivity();
                //stepIndex=activity.stepIndex;
                recipe=activity.mrecipe;
            }

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
    }


}
