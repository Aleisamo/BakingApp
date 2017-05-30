package aleisamo.github.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import aleisamo.github.com.bakingapp.MainBaking;
import aleisamo.github.com.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BakingWidgetsProviderConfigureActivity BakingWidgetsProviderConfigureActivity}
 */
public class BakingWidgetsProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = BakingWidgetsProviderConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widgets_provider);

        // create  an Intent to launch Baking main
        Intent widgetBakingIntent = new Intent(context,MainBaking.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,widgetBakingIntent,0);

        // allows click from the widgets using pending intent
        views.setOnClickPendingIntent(R.id.appwidget_image,pendingIntent);




        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int i  = 0; i< appWidgetIds.length; i++) {
            Intent intent = new Intent(context,BakingRecipeService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widgets_provider);
            remoteViews.setRemoteAdapter(appWidgetIds[i],R.id.stackView,intent);
            remoteViews.setEmptyView(R.id.stackView, R.id.empty_view);

            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);

        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            BakingWidgetsProviderConfigureActivity.deleteTitlePref(context, appWidgetId);
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

