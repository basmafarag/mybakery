package com.example.android.mybakery.RecipeStepDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mybakery.R;
import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.RecipeDetails.RecipeDetailsActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import  android.widget.TextView;
public class RecipeStepDetailsFragment extends Fragment {
    static Recipe recipe;
    static int stepIndex;

    TextView descriptionTextView;

    SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoplayer;
    private long exoPlayerPosition;
    private boolean exoPlayerPlayWhenReady;


    public RecipeStepDetailsFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            stepIndex=savedInstanceState.getInt("step_index");
            recipe=(Recipe) savedInstanceState.getSerializable("recipe");
            exoPlayerPosition=savedInstanceState.getLong("exo_player_postion");
            exoPlayerPlayWhenReady=savedInstanceState.getBoolean("exo_player_play_when_ready");

        }
        else{
            if(getActivity() instanceof RecipeDetailsActivity){
                RecipeDetailsActivity activity=(RecipeDetailsActivity) getActivity();
                stepIndex=activity.mStepIndex;
                recipe=activity.mrecipe;
                exoPlayerPlayWhenReady=true;
            }
            descriptionTextView.findViewById(R.id.tv_step_description);
            descriptionTextView.setText(recipe.getStepList().get(stepIndex).getDescription());
        }
        if(mExoplayer !=null) mExoplayer.stop();
        String URL=recipe.getStepList().get(stepIndex).getVideoURL();
        if(URL==null || URL.isEmpty()){
            mPlayerView.setVisibility(View.GONE);
        }
        else{
            mExoplayer= ExoPlayerFactory.newSimpleInstance(getContext(),new DefaultTrackSelector(),new DefaultLoadControl());
            mPlayerView.findViewById(R.id.player_view);
            mPlayerView.setPlayer(mExoplayer);
            mExoplayer.prepare(getMediaSource(URL));
            mExoplayer.seekTo(exoPlayerPosition);
            mExoplayer.setPlayWhenReady(exoPlayerPlayWhenReady);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
    }
    @NonNull
    private MediaSource getMediaSource(String videoURL) {
        String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
        Uri uri = Uri.parse(videoURL);
        return new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.step_index_tag), stepIndex);
        outState.putSerializable(getString(R.string.recipe_tag), recipe);

        if (mExoplayer != null) {
            saveExoPlayerState(outState);
        }
    }

    private void saveExoPlayerState(@NonNull Bundle outState) {
        outState.putLong(getString(R.string.exo_player_position_tag), mExoplayer.getCurrentPosition());
        outState.putBoolean(getString(R.string.exo_player_play_when_ready_tag), mExoplayer.getPlayWhenReady());
    }

}
