package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class RecipesDetail extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipes);
        if(savedInstanceState == null){
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detail_recipes,recipeDetailFragment)
                    .commit();
        }

        Intent recipesDetails = getIntent();
         String title = recipesDetails.getStringExtra("title");
        if (title !=null){
            setTitle(title);
        }
        }
}
