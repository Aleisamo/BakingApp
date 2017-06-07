package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import aleisamo.github.com.bakingapp.BankingData.FetchRecipes;
import aleisamo.github.com.bakingapp.Ingredient;
import aleisamo.github.com.bakingapp.R;
import aleisamo.github.com.bakingapp.Recipe;

public class BakingRecipeViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;

    private List<Recipe> recipes = new ArrayList<>();


    public BakingRecipeViewFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        FetchRecipes fetchRecipes = new FetchRecipes();
        recipes = fetchRecipes.fetchRecipesSync();
    }

    @Override
    public void onDestroy() {
        recipes.clear();
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String recipeName = recipes.get(position).getName();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_name);
        remoteViews.setTextViewText(R.id.recipeNameWidget, recipeName);
        List<Ingredient> listIngredients = recipes.get(position).getIngredients();
        for (int i = 0; i <listIngredients.size() ; i++) {
            RemoteViews nestedViewIngredients = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredient);
            String listOfIngredientForRecipe =listIngredients.get(i).toString();
            nestedViewIngredients.setTextViewText(R.id.listOfIngredient,listOfIngredientForRecipe);
            remoteViews.addView(R.id.ingredientListLayout,nestedViewIngredients);
        }
       /* Bundle extra =new Bundle();
        extra.putInt(BakingWidgetProvider.ACTION_SHOW_LIST_INGREDIENTS,position);
        Intent fillIntent = new Intent();
        //intent.putParcelableArrayListExtra("List of ingredients", (ArrayList<? extends Parcelable>) ingredients);
        fillIntent.putExtras(extra);
        remoteViews.setOnClickFillInIntent(R.id.recipeNameWidget,fillIntent);*/


        //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        return remoteViews;
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
}







