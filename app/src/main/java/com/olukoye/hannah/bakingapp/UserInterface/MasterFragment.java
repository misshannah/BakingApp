
package com.olukoye.hannah.bakingapp.UserInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.olukoye.hannah.bakingapp.Adapters.RecipeAdapter;
import com.olukoye.hannah.bakingapp.Pojos.RecipeObjects;
import com.olukoye.hannah.bakingapp.R;
import com.olukoye.hannah.bakingapp.RecipesInterfaceApi;
import com.olukoye.hannah.bakingapp.RetrofitBuilder;
import com.olukoye.hannah.bakingapp.SimpleIdlingResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.olukoye.hannah.bakingapp.UserInterface.BakingAppActivity.ALL_RECIPES;


public class MasterFragment extends Fragment {



    public MasterFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;

        View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

        recyclerView=(RecyclerView)  rootView.findViewById(R.id.recipe_recycler);
        final RecipeAdapter recipesAdapter =new RecipeAdapter((BakingAppActivity)getActivity());
        recyclerView.setAdapter(recipesAdapter);



        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        RecipesInterfaceApi iRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<RecipeObjects>> recipe = iRecipe.getRecipe();

        final SimpleIdlingResource idlingResource = (SimpleIdlingResource)((BakingAppActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }


        recipe.enqueue(new Callback<ArrayList<RecipeObjects>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeObjects>> call, Response<ArrayList<RecipeObjects>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                ArrayList<RecipeObjects> recipes = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                recipesAdapter.setRecipeData(recipes,getContext());
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<RecipeObjects>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }


}
