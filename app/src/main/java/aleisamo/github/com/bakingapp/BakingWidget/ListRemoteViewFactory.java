package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import aleisamo.github.com.bakingapp.Ingredient;
import aleisamo.github.com.bakingapp.R;

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    List<Ingredient> mIngredients = new ArrayList<>();

    public ListRemoteViewFactory(Context applicationContext, Intent intent) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // get list of ingredients
        mIngredients = WidgetIngredients.getIngredients();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        // inflate remote Views for the items
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.list_widget_item);
        String ingredients = mIngredients.get(position).getIngredient();
        String quantity = mIngredients.get(position).getQuantity();
        String measure = mIngredients.get(position).getMeasure();
        rv.setTextViewText(R.id.ingredient_name, ingredients);
        rv.setTextViewText(R.id.quantity_measure, quantity + measure);
        return rv;
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
