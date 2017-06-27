package aleisamo.github.com.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesViewHolder> {

    private final List<Recipe> recipes;
    private OnItemClickListener clickListener;
    private Context context;

    public RecipesListAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recipe, parent, false);
        return new RecipesViewHolder(v, clickListener, recipes);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        String recipeName = recipes.get(position).getName();
        String recipeServings = recipes.get(position).getServings();
        String serving = context.getString(R.string.servings);
        holder.mTextView.setText(recipeName);
        holder.mServingText.setText(serving + recipeServings);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
