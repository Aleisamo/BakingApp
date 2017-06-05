package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import aleisamo.github.com.bakingapp.BankingData.BakingClient;
import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.R;
import aleisamo.github.com.bakingapp.Recipe;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingRecipesList implements RemoteViewsService.RemoteViewsFactory {
    private static final String API_BASE_URL = "https://go.udacity.com/";

    private List<Recipe> recipes;
    private Context context;

    public BakingRecipesList(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        recipes();
    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_name);
        String recipeName = recipes.get(position).getName();
        remoteView.setTextViewText(R.id.recipeNameWidget,recipeName);
        // Todo fill intent here and setting on click listener
        return remoteView;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // List of Recipes using retrofit
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
                recipes = response.body();
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(MainBaking.class.getSimpleName(), "error");
            }
        });

    }



}

