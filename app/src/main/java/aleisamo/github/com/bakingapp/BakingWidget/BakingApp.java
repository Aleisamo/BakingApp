package aleisamo.github.com.bakingapp.BakingWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingApp extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app);
        views.setImageViewResource(R.id.appwidget_image, R.drawable.confectionery);
        Intent intent = new Intent(context, MainBaking.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        // add a pending intent to launch baking app
        views.setOnClickPendingIntent(R.id.appwidget_image,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

