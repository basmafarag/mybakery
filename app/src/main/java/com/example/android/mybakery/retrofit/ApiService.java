package com.example.android.mybakery.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.android.mybakery.Model.Recipe;

public interface ApiService {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
