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

    private static int currentPosition = -1;

    @BindView(R.id.back)
    Button mBack;

    @BindView(R.id.next)
    Button mNext;

    private ArrayList<?> steps;

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
        if (currentPosition < 0) {
            currentPosition = position;
        }
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
        currentPosition = -1;
    }
}
