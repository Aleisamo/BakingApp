package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecipesDetail extends AppCompatActivity implements OnItemClickListener {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipes);
        mTwoPane = findViewById(R.id.description) != null;

        if (mTwoPane) {
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.description, descriptionFragment)
                    .commit();
        }

        if (getIntent() == null) {
            return;
        }

        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        if (mTitle != null) {
            setTitle(mTitle);
        }
        ArrayList<Parcelable> ingredients = intent.getParcelableArrayListExtra("ingredients");
        ArrayList<Parcelable> steps = intent.getParcelableArrayListExtra("steps");
        createRecipeDetailFragment(ingredients, steps);

        //TODO call onItemSelected with position in savedInstanceState
    }

    private void createRecipeDetailFragment(ArrayList<Parcelable> ingredients, ArrayList<Parcelable> steps) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("ARGUMENT_INGREDIENTS", ingredients);
        args.putParcelableArrayList("ARGUMENT_STEPS", steps);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().
                add(R.id.detail_recipes_fragment, recipeDetailFragment)
                .commit();
    }

    @Override
    public void onClick(View view, int position, List<?> list) {
        String description;
        String video;
        if (mTwoPane) {
            Step step = (Step) list.get(position);
            description = step.getDescription();
            if (!step.getThumbnailURL().equals("")) {
                video = step.getThumbnailURL();
            } else if (!step.getVideoUrl().equals("")) {
                video = step.getVideoUrl();
            } else {
                video = null;
            }
            Bundle args = new Bundle();
            args.putString("ARGUMENTS_DESCRIPTION", description);
            args.putString("ARGUMENTS_VIDEO", video);
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            descriptionFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.description, descriptionFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, Description.class);
            intent.putExtra("title", mTitle);
            intent.putExtra("position", position);
            intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) list);
            startActivity(intent);
        }

    }
}







