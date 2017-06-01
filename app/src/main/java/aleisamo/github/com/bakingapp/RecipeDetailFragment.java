package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements OnItemClickListener {


    @BindView(R.id.recipe_details_recycleView)
    RecyclerView mRecycleRecipeDetail;
    @BindView(R.id.recipe_details_step_recycleView)
    RecyclerView mRecycleSteps;
    @BindView(R.id.card)
    CardView mCard;
    private String title;

    public RecipeDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_recipes, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            mRecycleRecipeDetail.setLayoutManager(llm);
            ArrayList<Ingredient> ingredients = getArguments().getParcelableArrayList("ARGUMENT_INGREDIENTS");
            ListOfIngredientsAdapter adapter = new ListOfIngredientsAdapter(ingredients);
            // adapter for ingredients
            mRecycleRecipeDetail.setAdapter(adapter);
            LinearLayoutManager llmSteps = new LinearLayoutManager(getContext());
            mRecycleSteps.setLayoutManager(llmSteps);
            ArrayList<Step> steps = getArguments().getParcelableArrayList("ARGUMENT_STEPS");
            // Adapter for Steps
            StepsAdapter stepsAdapter = new StepsAdapter(steps);
            mRecycleSteps.setAdapter(stepsAdapter);
            stepsAdapter.setClickListener(this);
            title = getArguments().getString("ARGUMENT_TITLE");
        }
        return rootView;
    }

    @Override
    public void onClick(View view, int position, List<?> list) {
            Intent intent = new Intent(getContext(), Description.class);
            intent.putExtra("title", title);
            intent.putExtra("position", position);
            intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) list);
            getContext().startActivity(intent);


    }
    }



