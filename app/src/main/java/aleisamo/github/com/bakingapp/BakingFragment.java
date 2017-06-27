package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aleisamo.github.com.bakingapp.BakingWidget.BakingAppWidget;
import aleisamo.github.com.bakingapp.BakingWidget.WidgetIngredients;
import aleisamo.github.com.bakingapp.BankingData.FetchRecipes;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingFragment extends Fragment implements OnItemClickListener {

    LinearLayoutManager llm;
    GridLayoutManager glm;

    @BindView(R.id.recipe_recycleView)
    RecyclerView mRecycleRecipes;
    List<Recipe>mRecipes;

    public BakingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        // bind the view
        ButterKnife.bind(this, rootView);
        Callback<List<Recipe>>callback = listCallback(this);

        boolean twoPane = getResources().getBoolean(R.bool.isTwoPane);
        if (twoPane) {
            int numberOfColumns = 2;
            glm = new GridLayoutManager(getContext(), numberOfColumns);
            mRecycleRecipes.setLayoutManager(glm);
            FetchRecipes fetchRecipes = new FetchRecipes();
            fetchRecipes.fetchRecipes(callback);
        } else {
            llm = new LinearLayoutManager(getContext());
            mRecycleRecipes.setLayoutManager(llm);
            FetchRecipes fetchRecipes = new FetchRecipes();
            fetchRecipes.fetchRecipes(callback);
        }

        return rootView;
    }

    @Override
    public void onClick(View view, int position, List<?> list) {
        Recipe recipe = (Recipe) list.get(position);
        List<Ingredient> ingredients = recipe.getIngredients();
        //Log.v("ingredients", String.valueOf(ingredients));
        Intent intent = new Intent(getContext(), RecipesDetail.class);
        intent.putExtra(getString(R.string.title), recipe.getName());
        intent.putParcelableArrayListExtra(getString(R.string.ingredients), (ArrayList<? extends Parcelable>) recipe.getIngredients());
        intent.putParcelableArrayListExtra(getString(R.string.steps), (ArrayList<? extends Parcelable>) recipe.getSteps());
        getContext().startActivity(intent);

        // use singleton to populate list of recipes and set recipe name Widget
        WidgetIngredients.INSTANCE.setIngredients(ingredients);
        WidgetIngredients.INSTANCE.setRecipeName(recipe.getName());
        BakingAppWidget.updateAppWidgets(getContext());
    }

    private Callback<List<Recipe>> listCallback (final OnItemClickListener onItemClickListener){
        return new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe>recipes = response.body();
                mRecipes= recipes;
                RecipesListAdapter adapter = new RecipesListAdapter(recipes,getContext());
                mRecycleRecipes.setAdapter(adapter);
                adapter.setClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(BakingFragment.class.getSimpleName(),getString(R.string.check_connection));

            }
        };


    }
}

