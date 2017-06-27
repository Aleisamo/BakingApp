package aleisamo.github.com.bakingapp.BankingData;

import java.util.List;

import aleisamo.github.com.bakingapp.Recipe;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRecipes {

    private static final String API_BASE_URL = "https://go.udacity.com/";

    public void fetchRecipes(Callback<List<Recipe>> callback) {
        recipesCall().enqueue(callback);
    }

    private Call<List<Recipe>> recipesCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
        return retrofit
                .create(BakingClient.class)
                .recipes();
    }

}
