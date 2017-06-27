package aleisamo.github.com.bakingapp.BakingWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.R;


/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    public static void updateAppWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingAppWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_ingredients);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        // set recipe name
        String recipeName = WidgetIngredients.getRecipeName();
        views.setTextViewText(R.id.recipe_name, recipeName);

        // intent to the list service
        Intent listIntent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.list_ingredients, listIntent);

        // handle and empty recipe list
        views.setEmptyView(R.id.list_ingredients, R.id.empty_view);

        // set intent to main activity
        views.setImageViewResource(R.id.change_recipe, R.drawable.confectionery);
        Intent intent = new Intent(context, MainBaking.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.change_recipe, pendingIntent);

        // update
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

