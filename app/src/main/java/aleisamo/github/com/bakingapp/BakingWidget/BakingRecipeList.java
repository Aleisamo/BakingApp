package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import aleisamo.github.com.bakingapp.R;
import aleisamo.github.com.bakingapp.Recipe;

public class BakingRecipeList implements RemoteViewsService.RemoteViewsFactory {
    private List<Recipe> recipes;
    private Context context;

    public BakingRecipeList(Context context, Intent intent, List<Recipe>recipes){
        this.context = context;
        this.recipes =recipes;
    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

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
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.layout.recipe);
        String recipeName = recipes.get(position).getName();
        remoteView.setTextViewText(R.id.recipeNameWidget,recipeName);
        // Todo fillintent here and setting on click listener
        return remoteView;

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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
