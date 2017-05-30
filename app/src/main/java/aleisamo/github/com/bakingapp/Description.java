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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);

        /*if (savedInstanceState == null) {
            //create fragment description
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            // add fragment using fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            // transaction
            fragmentManager.beginTransaction()
                    .add(R.id.description, descriptionFragment)
                    .commit();
        }*/
        if (getIntent() == null) {
            return;
        }

        Intent intent = getIntent();

        setTitle(intent.getStringExtra("title"));
        steps = intent.getParcelableArrayListExtra("steps");
        position = intent.getIntExtra("position", 0);
        foo(position);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo(--position);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo(++position);
            }
        });
    }

    private void foo(int position) {
        mBack.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        mNext.setVisibility(position == steps.size() - 1 ? View.GONE : View.VISIBLE);

        Step step = (Step) steps.get(position);
        description = step.getDescription();
        video = step.getVideoUrl();
        Bundle args = new Bundle();
        args.putString("ARGUMENTS_DESCRIPTION", description);
        args.putString("ARGUMENTS_VIDEO", video);
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.description, descriptionFragment)
                .commit();
    }
}
