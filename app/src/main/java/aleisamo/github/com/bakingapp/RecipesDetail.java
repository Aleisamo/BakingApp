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
        mTitle = intent.getStringExtra(getString(R.string.title));
        if (mTitle != null) {
            setTitle(mTitle);
        }
        ArrayList<Parcelable> ingredients = intent.getParcelableArrayListExtra(getString(R.string.ingredients));
        ArrayList<Parcelable> steps = intent.getParcelableArrayListExtra(getString(R.string.steps));
        createRecipeDetailFragment(ingredients, steps);
    }

    private void createRecipeDetailFragment(ArrayList<Parcelable> ingredients, ArrayList<Parcelable> steps) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(getString(R.string.ingredients), ingredients);
        args.putParcelableArrayList(getString(R.string.steps), steps);

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
        String imageThumbnail;

        if (mTwoPane) {
            Step step = (Step) list.get(position);
            description = step.getDescription();
            imageThumbnail = step.getThumbnailURL();
            video = step.getVideoUrl();

            Bundle args = new Bundle();
            args.putString(getString(R.string.arguments_description), description);
            args.putString(getString(R.string.arguments_video), video);
            args.putString(getString(R.string.arguments_image), imageThumbnail);
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            descriptionFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.description, descriptionFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, Description.class);
            intent.putExtra(getString(R.string.title), mTitle);
            intent.putExtra(getString(R.string.position), position);
            intent.putParcelableArrayListExtra(getString(R.string.steps), (ArrayList<? extends Parcelable>) list);
            startActivity(intent);
        }

    }
}







