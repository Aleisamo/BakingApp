package aleisamo.github.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public RecipeDetailFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_recipes,container,false);
        ButterKnife.bind(this,rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecycleRecipeDetail.setLayoutManager(llm);
        Intent listIngredients = getActivity().getIntent();
        ArrayList<?> ingredients =listIngredients.getParcelableArrayListExtra("ingredients");
        ListOfIngredientsAdapter adapter = new ListOfIngredientsAdapter((List<Ingredient>) ingredients);
        mRecycleRecipeDetail.setAdapter(adapter);
        LinearLayoutManager llmSteps = new LinearLayoutManager(getContext());
        mRecycleSteps.setLayoutManager(llmSteps);
        ArrayList<?>steps = listIngredients.getParcelableArrayListExtra("steps");
        List<Step>step1 = (List<Step>) steps;
        Log.v("steps",step1.toString() );
        StepsAdapter stepsAdapter = new StepsAdapter((List<Step>)steps);
        mRecycleSteps.setAdapter(stepsAdapter);
        stepsAdapter.setClickListener(this);
        return rootView;

    }


    @Override
    public void onClick(View view, int position, List<?> list) {
        Step steps = (Step) list.get(position);
        Intent intent = new Intent(getContext(),Description.class);
        intent.putExtra("description", steps.getDescription());
        intent.putExtra("thumbnailURL", steps.getThumbnailURL());
        intent.putExtra("VideoURL", steps.getVideoUrl());
        getContext().startActivity(intent);

    }
}
