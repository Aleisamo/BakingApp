package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeCardFragment extends Fragment implements OnItemClickListener {

    private static final String API_BASE_URL = "https://go.udacity.com/";

    @BindView(R.id.recipe_recycleView)
    RecyclerView mRecycleRecipes;

    public RecipeCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        // bind the view
        ButterKnife.bind(this, rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecycleRecipes.setLayoutManager(llm);
        fetchRecipes(mRecycleRecipes, this);
        return rootView;
    }

    public void fetchRecipes(final RecyclerView listRecipes, final OnItemClickListener onItemClickListener) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // implementing Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        BakingClient client = retrofit.create(BakingClient.class);

        Call<List<Recipe>> call = client.recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                Log.v(MainBaking.class.getSimpleName(), "response" + recipes);
                RecipesListAdapter adapter = new RecipesListAdapter(recipes);
                listRecipes.setAdapter(adapter);
                adapter.setClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(MainBaking.class.getSimpleName(), "error");
            }
        });
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

