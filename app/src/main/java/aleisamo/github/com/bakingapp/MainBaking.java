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
            createFragment();
        }
    }

    private void createFragment() {
        // create widget_list_ingredients card fragment
        BakingFragment recipeCardFragment = new BakingFragment();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.recipecardfragment, recipeCardFragment)
                .commit();
    }

}
