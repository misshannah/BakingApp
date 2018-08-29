
package com.olukoye.hannah.bakingapp.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.olukoye.hannah.bakingapp.Adapters.RecipeAdapter;
import com.olukoye.hannah.bakingapp.Pojos.RecipeObjects;
import com.olukoye.hannah.bakingapp.R;
import com.olukoye.hannah.bakingapp.RecipesInterfaceApi;
import com.olukoye.hannah.bakingapp.SimpleIdlingResource;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BakingAppActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener{

    static String ALL_RECIPES="All_Recipes";
    static String SELECTED_RECIPES="Selected_Recipes";
    static String SELECTED_STEPS="Selected_Steps";
    static String SELECTED_INDEX="Selected_Index";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_baking_app);


       Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       setSupportActionBar(myToolbar);
       getSupportActionBar().setHomeButtonEnabled(false);
       getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       getSupportActionBar().setTitle("Baking Time");


// Get the IdlingResource instance
        getIdlingResource();

    }

    @Override
    public void onListItemClick(RecipeObjects selectedItemIndex) {

        Bundle selectedRecipeBundle = new Bundle();
        ArrayList<RecipeObjects> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(selectedItemIndex);
        selectedRecipeBundle.putParcelableArrayList(SELECTED_RECIPES,selectedRecipe);

        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(selectedRecipeBundle);
        startActivity(intent);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
    }


}
