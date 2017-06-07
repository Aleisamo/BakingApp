package aleisamo.github.com.bakingapp.BakingWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import aleisamo.github.com.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    static final String ACTION_SHOW_LIST_INGREDIENTS = "com.bakingapp.BakingWidget.ACTION_SHOW_LIST_INGREDIENTS";
    private RemoteViews mRemoteViews;

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals("ACTION_SHOW_LIST_INGREDIENTS")){

            /*int[] appWidgetIds = appWidgetManager.getAppWidgetIds((new ComponentName(context, BakingWidgetProvider.class)));
            this.onUpdate(context,appWidgetManager,appWidgetIds);*/
        }
        super.onReceive(context, intent);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_baking_app);

        //set Adapter
        Intent intent = new Intent(context, BakingRecipesViewService.class);
        mRemoteViews.setRemoteAdapter(R.id.list_of_recipes, intent);
        //List<Ingredient> ingredients = new Intent().getParcelableArrayListExtra("List of ingredients");
       // Ingredient listOfIngredients = (Ingredient)ingredients;
        //mRemoteViews.setTextViewText(R.id.list_of_ingredients,listOfIngredients.toString());
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }




}

