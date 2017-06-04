package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import java.util.List;

import aleisamo.github.com.bakingapp.BankingData.BakingClient;
import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.Recipe;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingRemoteViewService extends RemoteViewsService {
    private static final String API_BASE_URL = "https://go.udacity.com/";

    private List<Recipe>recipes;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new BakingRecipeList(this.getApplicationContext(),intent,recipes));

    }

    public void recipes() {
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
                ///
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(MainBaking.class.getSimpleName(), "error");
            }
        });


    }

}
