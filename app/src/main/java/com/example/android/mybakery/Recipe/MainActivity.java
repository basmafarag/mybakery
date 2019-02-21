package com.example.android.mybakery.Recipe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ProgressBar;

import com.example.android.mybakery.RecipeDetails.RecipeDetailsActivity;
import com.example.android.mybakery.retrofit.ApiService;
import com.example.android.mybakery.retrofit.RetroClient;
import com.example.android.mybakery.Model.Recipe;

import retrofit2.Callback;
import retrofit2.Response;
import com.example.android.mybakery.R;
import com.example.android.mybakery.Adapter.RecipesAdapter;

import java.io.Serializable;
import java.util.List;
import retrofit2.Call;
import com.example.android.mybakery.UItest.SimpleIdlingResources;
public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesOnClickHandler {
    RecyclerView mRecyclerView ;
    RecipesAdapter mRecipesAdapter;
    ProgressBar mProgressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeContainer;
    private List<Recipe> recipes;
    @Nullable
    private SimpleIdlingResources mIdlingResource;
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResources();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView= findViewById(R.id.recipe_list);
        mProgressBar= findViewById(R.id.pb_loading_indicator);
        initSwipeContainerRefreshListener();

        //----------------initRecyclerView-----------------------
        mRecipesAdapter = new RecipesAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecipesAdapter);
        mRecyclerView.setHasFixedSize(true);
        //----------------initRecyclerView-----------------------

        getIdlingResource();

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.recipes_tag))) {
            mProgressBar.setVisibility(View.VISIBLE);
            //----------------------getRecipes------------------------------
            mIdlingResource.setIdleState(false);

            ApiService apiService = RetroClient.getApiService();
            Call<List<Recipe>> call = apiService.getRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    recipes = response.body();
                    Log.d("recipesss", String.valueOf(recipes));

                    RecipesAdapter.recipes = recipes;
                    updateRecipesViewsOnSuccess();
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    Log.e(this.getClass().getSimpleName(), t.toString());
                    updateRecipesViewsOnFailure();
                }
            });
            //------------------------getRecipes-----------------------------
        } else {
            recipes = (List<Recipe>) savedInstanceState.getSerializable(getString(R.string.recipes_tag));

        }

    }

    private void initSwipeContainerRefreshListener() {
        swipeContainer=findViewById(R.id.swipe_refresh_layout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecipes();
            }
        });
    }
    private void getRecipes() {
        ApiService apiService = RetroClient.getApiService();
        Call<List<Recipe>> call = apiService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                recipes = response.body();
                RecipesAdapter.recipes = recipes;
                //Log.d("recipesss", String.valueOf(recipes));

                updateRecipesViewsOnSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e(this.getClass().getSimpleName(), t.toString());
                updateRecipesViewsOnFailure();
            }
        });
    }
    @Override
    public void onClick(Recipe recipe) {
        Log.d("recipeeee", String.valueOf(recipe));
        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.recipes_tag), recipe);
        startActivity(intent);
    }

    private void updateRecipesViewsOnFailure() {

        mProgressBar.setVisibility(View.INVISIBLE);
        swipeContainer.setRefreshing(false);

    }

    private void updateRecipesViewsOnSuccess() {

        mRecipesAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.INVISIBLE);
        swipeContainer.setRefreshing(false);

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (recipes != null) {
            outState.putSerializable(getString(R.string.recipes_tag), (Serializable) recipes);
        }
        super.onSaveInstanceState(outState);
    }
}
