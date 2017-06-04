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

import aleisamo.github.com.bakingapp.BankingData.FetchRecipes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingFragment extends Fragment implements OnItemClickListener {

    private static final String API_BASE_URL = "https://go.udacity.com/";

    Bundle currentState;
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
        boolean twoPane = getResources().getBoolean(R.bool.isTwoPane);
        if (twoPane) {
            int numberOfColumns = 2;
            glm = new GridLayoutManager(getContext(), numberOfColumns);
            mRecycleRecipes.setLayoutManager(glm);
            FetchRecipes fetchRecipes = new FetchRecipes(mRecycleRecipes, this);
            fetchRecipes.fetchRecipes();
        } else {
            llm = new LinearLayoutManager(getContext());
            mRecycleRecipes.setLayoutManager(llm);
            FetchRecipes fetchRecipes = new FetchRecipes(mRecycleRecipes, this);
            fetchRecipes.fetchRecipes();
        }
        return rootView;
    }

    @Override
    public void onClick(View view, int position, List<?> list) {
        Recipe recipe = (Recipe) list.get(position);
        List<Ingredient> ingredients = recipe.getIngredients();
        Log.v("ingredients", String.valueOf(ingredients));
        Intent intent = new Intent(getContext(), RecipesDetail.class);
        intent.putExtra("title", recipe.getName());
        intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) recipe.getIngredients());
        intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) recipe.getSteps());
        getContext().startActivity(intent);
    }
}

