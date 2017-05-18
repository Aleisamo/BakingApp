package aleisamo.github.com.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainBaking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baking);
        //TODO implement for tablets
        if (savedInstanceState == null){

            // create recipe card fragment
            RecipeCardFragment recipeCardFragment = new RecipeCardFragment();
            // add fragment to its using Fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            // transaction
            fragmentManager.beginTransaction()
                    .add(R.id.container,recipeCardFragment)
                    .commit();
        }



    }



}
//https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json