package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Description extends AppCompatActivity {
    @BindView(R.id.back)
    Button mBack;
    @BindView(R.id.next)
    Button mNext;

    private String description;
    private String video;
    private ArrayList<?> steps;
    private int position;
    private int currentPosition;
    String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() == null) {
            return;
        }
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        setTitle(mTitle);

        steps = intent.getParcelableArrayListExtra("steps");

        position = intent.getIntExtra("position", 0);

        // restore state from share preferences
        currentPosition = this.getSharedPreferences(getClass().getSimpleName(),
                this.MODE_PRIVATE)
                .getInt("currentPosition", position);
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
        this.getSharedPreferences(getClass().getSimpleName(), this.MODE_PRIVATE)
                .edit().putInt("currentPosition", position).apply();

        mBack.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        mNext.setVisibility(position == steps.size() - 1 ? View.GONE : View.VISIBLE);

        Step step = (Step) steps.get(position);
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
        getSharedPreferences(getClass().getSimpleName(), MODE_PRIVATE)
                .getInt("Current position", position);
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.description, descriptionFragment)
                .commit();
    }


}
