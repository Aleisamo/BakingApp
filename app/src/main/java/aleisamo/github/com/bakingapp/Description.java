package aleisamo.github.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Description extends AppCompatActivity {

    private static final String CURRENT_POSITION = "current_position";

    @BindView(R.id.back)
    Button mBack;

    @BindView(R.id.next)
    Button mNext;

    private ArrayList<?> steps;

    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);

        if (getIntent() == null) {
            return;
        }

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(getString(R.string.title)));
        steps = intent.getParcelableArrayListExtra(getString(R.string.steps));
        int position = intent.getIntExtra(getString(R.string.position), 0);
        currentPosition = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE)
                .getInt(CURRENT_POSITION, position);
        // restore state from share preferences
        display(currentPosition);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display(--currentPosition);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display(++currentPosition);
            }
        });
    }

    private void display(int position) {
        // save position when phone is rotated and avoid lose currently activity
        getSharedPreferences(getClass().getSimpleName(), MODE_PRIVATE)
                .edit()
                .putInt(CURRENT_POSITION, position)
                .apply();
        mBack.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        mNext.setVisibility(position == steps.size() - 1 ? View.GONE : View.VISIBLE);
        Step step = (Step) steps.get(position);
        String description = step.getDescription();
        String video = step.getVideoUrl();
        String imageThumbnail = step.getThumbnailURL();

        Bundle args = new Bundle();
        args.putString(getString(R.string.arguments_description), description);
        args.putString(getString(R.string.arguments_video), video);
        args.putString(getString(R.string.arguments_image), imageThumbnail);

        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.description, descriptionFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanPreferences();
    }

    private void cleanPreferences() {
        getSharedPreferences(getClass().getSimpleName(), MODE_PRIVATE)
                .edit()
                .remove(CURRENT_POSITION)
                .apply();
    }
}
