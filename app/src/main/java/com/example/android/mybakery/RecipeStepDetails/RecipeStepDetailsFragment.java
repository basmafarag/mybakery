package com.example.android.mybakery.RecipeStepDetails;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import com.example.android.mybakery.Model.Step;
import com.squareup.picasso.Picasso;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import  android.widget.TextView;
public class RecipeStepDetailsFragment extends Fragment {
    public  static Recipe recipe;
    public static int stepIndex;
    public static boolean isLargeScreen = false;
    TextView descriptionTextView;

    SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoplayer;
    private long exoPlayerPosition;
    private boolean exoPlayerPlayWhenReady;
    Button prev;
    Button next;
    ImageView mThumbnailImage;
    TextView noVideoText;


    public RecipeStepDetailsFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        mPlayerView=rootView.findViewById(R.id.player_view);
        prev=rootView.findViewById(R.id.prev_button);
        next=rootView.findViewById(R.id.next_button);
        mThumbnailImage=rootView.findViewById(R.id.iv_thumbnail_image);
        noVideoText=rootView.findViewById(R.id.tv_no_video_available);

        if(savedInstanceState!=null){
            //-------------restore savedInstance------------------
            stepIndex=savedInstanceState.getInt("step_index");
            Log.d("heloo ", String.valueOf(stepIndex));

            recipe=(Recipe) savedInstanceState.getSerializable("recipe");
            exoPlayerPosition=savedInstanceState.getLong("exo_player_postion");
            exoPlayerPlayWhenReady=savedInstanceState.getBoolean("exo_player_play_when_ready");

        }
        else{
                //--------------get Params---------------
                stepIndex=getArguments().getInt("step_index");

                Log.d("heloo1", String.valueOf(stepIndex));

                recipe=(Recipe)getArguments().getSerializable("recipe");
                exoPlayerPlayWhenReady=true;

                    }
        descriptionTextView=rootView.findViewById(R.id.tv_step_description);
        if(recipe!=null && recipe.getStepList()!=null && recipe.getStepList().get(stepIndex)!=null) {
            descriptionTextView.setText(recipe.getStepList().get(stepIndex).getDescription());
        }
        Log.d("heloo2", String.valueOf(descriptionTextView));


        intialization();
        if (isLargeScreen) {
            next.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);

        }
        PrevButton();
        nextButton();
        setFullscreenVideoConfigurationIfLandscapeAndSmallScreen();

                // Inflate the layout for this fragment
        return rootView;
    }

    private void PrevButton() {
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepIndex > 0) stepIndex--;
                Step step = recipe.getStepList().get(stepIndex);
                descriptionTextView.setText(step.getDescription());
                intialization();
                if (stepIndex <= 0) {
                    prev.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                }else
                    prev.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);

            }
        });
    }
        private void nextButton(){
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepIndex++;
                    Step step=recipe.getStepList().get(stepIndex);
                    descriptionTextView.setText(step.getDescription());
                    intialization();
                    if(stepIndex>=recipe.getStepList().size()-1){
                        next.setVisibility(View.GONE);
                        prev.setVisibility(View.VISIBLE);
                    }else{
                        next.setVisibility(View.VISIBLE);
                        prev.setVisibility(View.VISIBLE);
                    }

                }
            });


        }
    private void intialization(){
        if(mExoplayer !=null) mExoplayer.stop();
        if(recipe!=null && recipe.getStepList()!=null && recipe.getStepList().get(stepIndex)!=null) {

            String URL = recipe.getStepList().get(stepIndex).getVideoURL();

        Log.d("heloo3", String.valueOf(stepIndex));

        if(URL==null || URL.isEmpty()){
            initializeThumbnailImage();
        }
        else{
            //-----------initialize exoPlayer-----------
            noVideoText.setVisibility(View.GONE);
            mThumbnailImage.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);

            mPlayerView.setVisibility(View.VISIBLE);
            Log.d("heloo5", String.valueOf(stepIndex));

            mExoplayer= ExoPlayerFactory.newSimpleInstance(getContext(),new DefaultTrackSelector(),new DefaultLoadControl());
            mPlayerView.setPlayer(mExoplayer);
            mExoplayer.prepare(getMediaSource(URL));
            mExoplayer.seekTo(exoPlayerPosition);
            mExoplayer.setPlayWhenReady(exoPlayerPlayWhenReady);
        }

    }}
    private void initializeThumbnailImage() {
        String recipeThumbnailUrl = recipe.getStepList().get(stepIndex).getThumbnailURL();
        if (recipeThumbnailUrl == null || recipeThumbnailUrl.isEmpty()) {
            mThumbnailImage.setVisibility(View.GONE);
            noVideoText.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        } else {
            displayThumbnail(recipeThumbnailUrl);
        }
    }

    private void displayThumbnail(String recipeThumbnailUrl) {
        Picasso.get().load(recipeThumbnailUrl).placeholder(R.drawable.invalid_thumbnail)
                .error(R.drawable.invalid_thumbnail).into(mThumbnailImage);
        mThumbnailImage.setVisibility(View.VISIBLE);
        mPlayerView.setVisibility(View.GONE);
    }

    private void setFullscreenVideoConfigurationIfLandscapeAndSmallScreen() {
        boolean isLandscapeMode = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscapeMode && !isLargeScreen) {
            setPlayerFullHeight();
            hideNavigationButtons();
        }
    }

    private void hideNavigationButtons() {
        next.setVisibility(View.GONE);
        prev.setVisibility(View.GONE);
    }

    private void setPlayerFullHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        mPlayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
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
      outState.putSerializable("Recipe",recipe);

       // outState.putSerializable(getString(R.string.recipe_tag), recipe);

        if (mExoplayer != null) {
           saveExoPlayerState(outState);
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
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            intialization();

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        intialization();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoplayer != null) {
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer = null;
        }
    }
}




