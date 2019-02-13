package com.example.android.mybakery.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.android.mybakery.Model.Recipes;

public interface ApiService {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipes>> getRecipes();

}
