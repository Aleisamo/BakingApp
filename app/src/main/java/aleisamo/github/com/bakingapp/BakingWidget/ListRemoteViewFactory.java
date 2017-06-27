package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import aleisamo.github.com.bakingapp.Ingredient;
import aleisamo.github.com.bakingapp.R;

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> ingredients = new ArrayList<>();

    public ListRemoteViewFactory(Context applicationContext) {
        this.mContext = applicationContext;
    }

    @Override
    public void onDataSetChanged() {
        // get list of ingredients
        ingredients = WidgetIngredients.getIngredients();
    }

    @Override
    public int getCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredients.get(position);
        String ingredients = ingredient.getIngredient();
        String quantity = ingredient.getQuantity();
        String measure = ingredient.getMeasure();

        // inflate remote Views for the items
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_widget_item);
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

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
