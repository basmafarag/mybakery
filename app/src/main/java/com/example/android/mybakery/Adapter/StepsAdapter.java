package com.example.android.mybakery.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.Model.Step;
import com.example.android.mybakery.R;
import com.example.android.mybakery.RecipeDetails.RecipeDetailsActivity;
import com.example.android.mybakery.RecipeDetails.StepsFragment;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {


    private static List<Step> steps;
    private static StepsFragment.OnStepClickListener onStepClickListener;
    private Context context;
    private int stepIndex;

    public StepsAdapter(Recipe recipe,StepsFragment.OnStepClickListener onStepClickListener,int stepIndex) {
        steps = recipe.getStepList();
        StepsAdapter.onStepClickListener=onStepClickListener;
        this.stepIndex=stepIndex;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater
                .inflate(R.layout.step_item, viewGroup, shouldAttachToParentImmediately);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        int index = steps.get(i).getId();
        String stepIndex=Integer.toString(index)+"- ";
        String stepTitle = steps.get(i).getShortDescription();

        viewHolder.stepIndex.setText(stepIndex);
        viewHolder.stepTitle.setText(stepTitle);

    }

    @Override
    public int getItemCount() {
        if(steps==null){
            return 0;
        }
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView stepIndex;
        final TextView stepTitle;

            ViewHolder(View itemView) {
                super(itemView);
                stepIndex = itemView.findViewById(R.id.tv_step_num);
                stepTitle = itemView.findViewById(R.id.tv_step);
            }
        @Override
        public void onClick(View view) {
            int adapterPosition= getAdapterPosition();
            Step step= RecipeDetailsActivity.mrecipe.getStepList().get(adapterPosition);
            onStepClickListener.onStepSelected(adapterPosition);

        }



    }
    }

