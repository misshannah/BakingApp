package com.olukoye.hannah.bakingapp;


import com.olukoye.hannah.bakingapp.Pojos.RecipeObjects;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit2.Call;


public interface RecipesInterfaceApi {
    @GET("/baking.json")
    Call<ArrayList<RecipeObjects>> getRecipe();

}