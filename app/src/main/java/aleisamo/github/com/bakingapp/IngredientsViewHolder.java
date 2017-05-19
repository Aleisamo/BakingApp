package aleisamo.github.com.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.recipes_ingredients)
    TextView mIngredientsTitle;
    @BindView(R.id.list_of_ingredients)
    TextView mListOfIngredients;
    public IngredientsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
