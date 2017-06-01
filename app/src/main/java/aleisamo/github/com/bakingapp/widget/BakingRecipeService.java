package aleisamo.github.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import aleisamo.github.com.bakingapp.BankingData.BakingClient;
import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.R;
import aleisamo.github.com.bakingapp.Recipe;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingRecipeService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewFactory(this.getApplicationContext(),intent);
    }

    private class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        private int mAppWidgetId;
        private static final String API_BASE_URL = "https://go.udacity.com/";
        private List<Recipe> mRecipeItemWidget;



        public StackRemoteViewFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
            mAppWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            fetchWidgetList(mRecipeItemWidget);



            }

        public void fetchWidgetList(final List<Recipe> recipeItemWidget){
            // set up connection with the data source
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // implementing retrofit
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.client(httpClient.build()).build();
            BakingClient client = retrofit.create(BakingClient.class);

            retrofit2.Call<List<Recipe>> call = client.recipes();
            call.enqueue(new retrofit2.Callback<List<Recipe>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {List<Recipe> recipes = response.body();
                    Log.v(MainBaking.class.getSimpleName(), "response" + recipes);
                        recipeItemWidget.add((Recipe) new Recipe().getIngredients());
                   }

                @Override
                public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                    Log.v(MainBaking.class.getSimpleName(), "error");


                }
            });

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }
        @Override
        public RemoteViews getViewAt(int position) {
            Recipe ingredients = mRecipeItemWidget.get(position);
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.baking_widgets_single_item);
            String listIngredients = ingredients.getName();
            rv.setTextViewText(R.id.singleItem,listIngredients );
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
