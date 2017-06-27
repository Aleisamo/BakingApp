package aleisamo.github.com.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListOfIngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    private List<Ingredient> ingredients;


    public ListOfIngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_ingredients, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        String quantity = ingredients.get(position).getQuantity();
        String measure = ingredients.get(position).getMeasure();
        String ingredient = ingredients.get(position).getIngredient();
        holder.mListOfIngredients.setText(ingredient + "\n" + quantity + measure);
        holder.mListOfIngredients.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
