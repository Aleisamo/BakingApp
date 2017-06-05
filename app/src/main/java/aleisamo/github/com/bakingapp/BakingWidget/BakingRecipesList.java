package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import aleisamo.github.com.bakingapp.BankingData.BakingClient;
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
    //private List<String> recipesName;

    private Context context;

    public BakingRecipesList(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        //recipesName = Arrays.asList("Brownie","cup cake","apple pie");
        //recipes();
        callRecipes();


    }



    @Override
    public void onDataSetChanged() {
       // recipesName = Arrays.asList("Brownie","cup cake","apple pie");
       // recipes();
        callRecipes();

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int getCount() {

        int recipeSize =getRecipes((Callback<List<Recipe>>) recipes).size();
        return recipeSize;

    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_name);
        //String recipeName = recipesName.get(position);
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
    public BakingClient client() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // implementing Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        BakingClient client = retrofit.create(BakingClient.class);
        return client;
    }


    private void callRecipes() {
        getRecipes(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("LOG", "check Connection");

            }
        });

    }

    public List<Recipe> getRecipes(Callback<List<Recipe>> callback) {
        List<Recipe>test = null;
        retrofit2.Call<List<Recipe>>call = client().recipes();
        call.enqueue(callback);
        return test;

    }

}







