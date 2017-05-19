package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RecipesDetail extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent recipesDetails = getIntent();
         String title = recipesDetails.getStringExtra("title");
        if (title !=null){
            setTitle(title);
        }
        }
}
