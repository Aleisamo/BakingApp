package aleisamo.github.com.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class RecipesListAdapter extends RecyclerView.Adapter<RecipesViewHolder> {

    private final List<Recipe> recipes;
    private OnItemClickListener clickListener;

    public RecipesListAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recipe, parent, false);
        RecipesViewHolder recipesViewHolder = new RecipesViewHolder(v, clickListener, recipes);
        return recipesViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        String recipeName = recipes.get(position).getName();
        holder.mTextView.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
