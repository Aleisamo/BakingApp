package aleisamo.github.com.bakingapp.BankingData;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.OnItemClickListener;
import aleisamo.github.com.bakingapp.Recipe;
import aleisamo.github.com.bakingapp.RecipesListAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRecipes {
    private static final String API_BASE_URL = "https://go.udacity.com/";

    RecyclerView mRecyclerView;
    OnItemClickListener onItemClickListener;

    public FetchRecipes(RecyclerView mRecyclerView,OnItemClickListener onItemClickListener){
        this.mRecyclerView = mRecyclerView;
        this.onItemClickListener = onItemClickListener;

    }

    public void fetchRecipes() {
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
                ///Log.v(MainBaking.class.getSimpleName(), "response" + recipes);
                RecipesListAdapter adapter = new RecipesListAdapter(recipes);
                mRecyclerView.setAdapter(adapter);
                adapter.setClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(MainBaking.class.getSimpleName(), "error");
            }
        });


    }

   }
