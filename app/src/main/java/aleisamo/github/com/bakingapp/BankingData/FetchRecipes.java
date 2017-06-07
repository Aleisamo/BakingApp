package aleisamo.github.com.bakingapp.BankingData;

import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import aleisamo.github.com.bakingapp.Recipe;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRecipes {
    private static final String API_BASE_URL = "https://go.udacity.com/";

    public FetchRecipes() {
    }

    public void fetchRecipes(Callback<List<Recipe>> callback) {
        recipesCall().enqueue(callback);
    }

    public List<Recipe> fetchRecipesSync() {
        try {
            return recipesCall().execute().body();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Failed to fetch recipes", e);
            return Collections.emptyList();
        }
    }

    private Call<List<Recipe>> recipesCall() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // implementing Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        BakingClient client = retrofit.create(BakingClient.class);

        return client.recipes();
    }

}
