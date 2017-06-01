package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import butterknife.BindView;

public class RecipesDetail extends AppCompatActivity{
    @BindView(R.id.playerView)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.back)
    Button mBack;
    @BindView(R.id.next)
    Button mNext;
    @BindView(R.id.viewlineTop)
    View mTop;
    @BindView(R.id.viewlineBottom)
    View mBottom;
    @BindView(R.id.text_description)
    TextView mDescription;

    private boolean mTwoPane;
    private String mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipes);
        // Intent from main Activity

        if (findViewById(R.id.description) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                DescriptionFragment descriptionFragment = new DescriptionFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.description, descriptionFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
            if (getIntent() == null) {
                return;
            }
            Intent intent = getIntent();
            ArrayList<?> ingredients = intent.getParcelableArrayListExtra("ingredients");
            ArrayList<?> steps = intent.getParcelableArrayListExtra("steps");
            mTitle = intent.getStringExtra("title");
            Bundle args = new Bundle();
            args.putString("ARGUMENT_TITLE", mTitle);
            args.putParcelableArrayList("ARGUMENT_INGREDIENTS", (ArrayList<? extends Parcelable>) ingredients);
            args.putParcelableArrayList("ARGUMENT_STEPS", (ArrayList<? extends Parcelable>) steps);
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().
                    add (R.id.detail_recipes_fragment, recipeDetailFragment)
                    .commit();
            Intent recipesDetails = getIntent();
            mTitle = recipesDetails.getStringExtra("title");
            if (mTitle != null) {
                setTitle(mTitle);
            }
        }



    public boolean ismTwoPane(){return mTwoPane;}





}







