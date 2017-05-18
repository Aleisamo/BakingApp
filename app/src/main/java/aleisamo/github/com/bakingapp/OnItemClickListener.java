package aleisamo.github.com.bakingapp;

import android.view.View;

import java.util.List;

public interface OnItemClickListener {
    public void onClick(View view, int position, List<Recipe> recipes);
}
