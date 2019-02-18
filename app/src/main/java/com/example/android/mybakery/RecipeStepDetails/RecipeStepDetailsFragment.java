package com.example.android.mybakery.RecipeStepDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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
        View rootView=inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        mPlayerView=rootView.findViewById(R.id.player_view);

        if(savedInstanceState!=null){
            stepIndex=savedInstanceState.getInt("step_index");
            Log.d("heloo ", String.valueOf(stepIndex));

            recipe=(Recipe) savedInstanceState.getSerializable("recipe");
            exoPlayerPosition=savedInstanceState.getLong("exo_player_postion");
            exoPlayerPlayWhenReady=savedInstanceState.getBoolean("exo_player_play_when_ready");

        }
        else{
            if(getActivity() instanceof RecipeDetailsActivity){
                RecipeDetailsActivity activity=(RecipeDetailsActivity) getActivity();
                //stepIndex=activity.mStepIndex;
                stepIndex=getArguments().getInt("step_index");

                Log.d("heloo1", String.valueOf(stepIndex));

                recipe=(Recipe)getArguments().getSerializable("recipe");
                exoPlayerPlayWhenReady=true;
            }
                    }
        descriptionTextView=rootView.findViewById(R.id.tv_step_description);
        descriptionTextView.setText(recipe.getStepList().get(stepIndex).getDescription());
        Log.d("heloo2", String.valueOf(descriptionTextView));


        intialization();
                // Inflate the layout for this fragment
        return rootView;
    }
    private void intialization(){
        if(mExoplayer !=null) mExoplayer.stop();
        String URL=recipe.getStepList().get(stepIndex).getVideoURL();
        Log.d("heloo3", String.valueOf(stepIndex));

        if(URL==null || URL.isEmpty()){
            mPlayerView.setVisibility(View.GONE);
            Log.d("heloo4", String.valueOf(stepIndex));

        }
        else{
            mPlayerView.setVisibility(View.VISIBLE);
            Log.d("heloo5", String.valueOf(stepIndex));

            mExoplayer= ExoPlayerFactory.newSimpleInstance(getContext(),new DefaultTrackSelector(),new DefaultLoadControl());
            mPlayerView.setPlayer(mExoplayer);
            mExoplayer.prepare(getMediaSource(URL));
            mExoplayer.seekTo(exoPlayerPosition);
            mExoplayer.setPlayWhenReady(exoPlayerPlayWhenReady);
        }

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
       // outState.putSerializable(getString(R.string.recipe_tag), recipe);

        if (mExoplayer != null) {
         //   saveExoPlayerState(outState);
        }
    }

    private void saveExoPlayerState(@NonNull Bundle outState) {
        outState.putLong(getString(R.string.exo_player_position_tag), mExoplayer.getCurrentPosition());
        outState.putBoolean(getString(R.string.exo_player_play_when_ready_tag), mExoplayer.getPlayWhenReady());
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mExoplayer != null) {
            pauseExoPlayerState();
        }
    }

    private void pauseExoPlayerState() {
        exoPlayerPlayWhenReady = mExoplayer.getPlayWhenReady();
        exoPlayerPosition = mExoplayer.getCurrentPosition();
        mExoplayer.release();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoplayer!=null) {
            mExoplayer.stop();
            mExoplayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoplayer!=null) {
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoplayer!=null) {
            mExoplayer.stop();
            mExoplayer.release();
        }
    }



}
