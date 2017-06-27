package aleisamo.github.com.bakingapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.recipes_cardView)
    CardView mCardView;
    @BindView(R.id.recipe_card)
    TextView mTextView;
    @BindView(R.id.recipe_servings)
    TextView mServingText;

    private final OnItemClickListener clickListener;
    private final List<Recipe> recipes;

    public RecipesViewHolder(View itemView, OnItemClickListener clickListener, List<Recipe> recipes) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.clickListener = clickListener;
        this.recipes = recipes;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClick(v, getAdapterPosition(), recipes);
        }
    }
}
