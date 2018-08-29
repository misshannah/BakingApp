
package com.olukoye.hannah.bakingapp.UserInterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olukoye.hannah.bakingapp.Adapters.RecipeDetailAdapter;
import com.olukoye.hannah.bakingapp.Pojos.IngredientsObject;
import com.olukoye.hannah.bakingapp.Pojos.RecipeObjects;
import com.olukoye.hannah.bakingapp.R;
import com.olukoye.hannah.bakingapp.Widgets.UpdateBakingService;

import java.util.ArrayList;
import java.util.List;

import static com.olukoye.hannah.bakingapp.UserInterface.RecipeDetailActivity.SELECTED_RECIPES;


/**
 * Project "Baking App" - Created by Nikos Vaggalis as part of the Udacity Android Developer Nanodegree on 13/6/2017.
 */

public class RecipeDetailFragment extends Fragment {

    ArrayList<RecipeObjects> recipe;
    String recipeName;

    public RecipeDetailFragment() {

    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;

        recipe = new ArrayList<>();


        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);

        }
        else {
            recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        List<IngredientsObject> ingredients = recipe.get(0).getIngredients();
        recipeName=recipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);
        textView = (TextView)rootView.findViewById(R.id.recipe_detail_text);

        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();


        ingredients.forEach((a) ->
            {
                textView.append("\u2022 "+ a.getIngredient()+"\n");
                textView.append("\t\t\t Quantity: "+a.getQuantity().toString()+"\n");
                textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");

                recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                        "Quantity: "+a.getQuantity().toString()+"\n"+
                        "Measure: "+a.getMeasure()+"\n");
            });

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recipe_detail_recycler);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        RecipeDetailAdapter mRecipeDetailAdapter =new RecipeDetailAdapter((RecipeDetailActivity)getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(recipe,getContext());

        //update widget
        UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipe);
        currentState.putString("Title",recipeName);
    }


}


