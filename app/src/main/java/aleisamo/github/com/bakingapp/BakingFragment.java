package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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

    private static final String LAYOUT_MANAGER = "layoutManager";
    LinearLayoutManager llm;
    GridLayoutManager glm;


    @BindView(R.id.recipe_recycleView)
    RecyclerView mRecycleRecipes;


    public BakingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        // bind the view
        ButterKnife.bind(this, rootView);

        Callback<List<Recipe>> callback = listCallback(this);
        FetchRecipes fetchRecipes = new FetchRecipes();
        fetchRecipes.fetchRecipes(callback);


        if (isTwoPane()) {
            int numberOfColumns = 2;
            glm = new GridLayoutManager(getContext(), numberOfColumns);
            mRecycleRecipes.setLayoutManager(glm);
        } else {
            llm = new LinearLayoutManager(getContext());
            mRecycleRecipes.setLayoutManager(llm);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (isTwoPane()) {
            Parcelable currentState = glm.onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER, currentState);
        } else {
            Parcelable currentState = llm.onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER, currentState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            return;
        }

        if (isTwoPane()) {
            glm.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_MANAGER));

        } else {
            llm.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_MANAGER));
        }
    }

    private boolean isTwoPane() {
        return getResources().getBoolean(R.bool.isTwoPane);
    }

    @Override
    public void onClick(View view, int position, List<?> list) {
        Recipe recipe = (Recipe) list.get(position);
        List<Ingredient> ingredients = recipe.getIngredients();

        Intent intent = new Intent(getContext(), RecipesDetail.class);
        intent.putExtra(getString(R.string.title), recipe.getName());
        intent.putParcelableArrayListExtra(getString(R.string.ingredients), (ArrayList<? extends Parcelable>) recipe.getIngredients());
        intent.putParcelableArrayListExtra(getString(R.string.steps), (ArrayList<? extends Parcelable>) recipe.getSteps());
        getContext().startActivity(intent);

        // use singleton to populate list of recipes and set recipe name Widget
        WidgetIngredients.setIngredients(ingredients);
        WidgetIngredients.setRecipeName(recipe.getName());
        BakingAppWidget.updateAppWidgets(getContext());
    }


    private Callback<List<Recipe>> listCallback(final OnItemClickListener onItemClickListener) {
        return new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                RecipesListAdapter adapter = new RecipesListAdapter(recipes, getContext());
                mRecycleRecipes.setAdapter(adapter);
                adapter.setClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(BakingFragment.class.getSimpleName(), getString(R.string.check_connection), t);
            }
        };
    }


}

