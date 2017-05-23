package aleisamo.github.com.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        if (savedInstanceState == null){
            //create fragment description
            DescriptionFragment descriptionFragment = new DescriptionFragment();

            // add fragment using fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            // transaction
            fragmentManager.beginTransaction()
                    .add(R.id.description,descriptionFragment)
                    .commit();
        }
    }
}
