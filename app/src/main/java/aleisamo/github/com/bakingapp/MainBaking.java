package aleisamo.github.com.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainBaking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baking);

        if (savedInstanceState == null) {

            // create recipe card fragment
            BakingFragment recipeCardFragment = new BakingFragment();

            // add fragment to its using Fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // transaction
            fragmentManager.beginTransaction()
                    .add(R.id.recipecardfragment, recipeCardFragment)
                    .commit();
        }


    }


}
