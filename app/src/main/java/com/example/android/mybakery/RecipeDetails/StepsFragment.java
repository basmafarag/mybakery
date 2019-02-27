package com.example.android.mybakery.RecipeDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mybakery.Adapter.StepsAdapter;
import com.example.android.mybakery.R;
import com.example.android.mybakery.Model.Recipe;

public class StepsFragment extends Fragment {

    Recipe recipe;
public boolean IsmasterDetail;
    public StepsFragment(){

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInsance){
        RecipeDetailsActivity recipeDetailsActivity=(RecipeDetailsActivity) getActivity();

        final View rootView=inflater.inflate(R.layout.fragment_steps,container,false);
        initRecyclerView(recipeDetailsActivity,rootView);
        if(rootView.findViewById(R.id.viewDivider)!=null){
            Log.d("ismasterdetailsss true", String.valueOf(IsmasterDetail));

            //IsmasterDetail=true;
            recipeDetailsActivity.isMasterDetails=true;
            Log.d("ismasterdetailssss true", String.valueOf(IsmasterDetail));

        }
        else {
            recipeDetailsActivity.isMasterDetails=false;

            Log.d("ismasterdetailss falsee", String.valueOf(IsmasterDetail));

        }
        return rootView;
    }

    private void initRecyclerView(RecipeDetailsActivity recipeDetailsActivity,View rootView){
        RecyclerView recyclerView=rootView.findViewById(R.id.rv_Steps_List);

        StepsAdapter stepsAdapter=new StepsAdapter(recipeDetailsActivity.mrecipe,recipeDetailsActivity,recipeDetailsActivity.mStepIndex);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(stepsAdapter);
        recyclerView.setHasFixedSize(false);

    }
    public interface OnStepClickListener {

        void onStepSelected(int  stepIndex);
    }
}
