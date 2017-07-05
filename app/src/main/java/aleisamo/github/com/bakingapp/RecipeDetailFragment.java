package aleisamo.github.com.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    private static final String LAYOUT_INGREDIENTS = "layoutManagerIngredients";
    private static final String LAYOUT_STEPS = "layoutManagerSteps";


    @BindView(R.id.recipe_details_recycleView)
    RecyclerView mIngredientsView;

    @BindView(R.id.recipe_details_step_recycleView)
    RecyclerView mStepsView;

    @BindView(R.id.card)
    CardView mCard;


    private LinearLayoutManager ingredientsLlm;
    private LinearLayoutManager stepsLlm;

    public RecipeDetailFragment() {
    }

    private OnItemClickListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnItemClickListener) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_recipes, container, false);

        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            ingredientsLlm = new LinearLayoutManager(getContext());
            mIngredientsView.setLayoutManager(ingredientsLlm);
            ArrayList<Ingredient> ingredients = getArguments().getParcelableArrayList(getString(R.string.ingredients));
            // Adapter for ingredients
            ListOfIngredientsAdapter adapter = new ListOfIngredientsAdapter(ingredients);
            mIngredientsView.setAdapter(adapter);

            stepsLlm = new LinearLayoutManager(getContext());
            mStepsView.setLayoutManager(stepsLlm);
            ArrayList<Step> steps = getArguments().getParcelableArrayList(getString(R.string.steps));
            // Adapter for Steps
            StepsAdapter stepsAdapter = new StepsAdapter(steps);
            mStepsView.setAdapter(stepsAdapter);
            stepsAdapter.setClickListener(callback);
        }
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable csIngredients = ingredientsLlm.onSaveInstanceState();
        outState.putParcelable(LAYOUT_INGREDIENTS, csIngredients);
        Parcelable csSteps = stepsLlm.onSaveInstanceState();
        outState.putParcelable(LAYOUT_STEPS, csSteps);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        ingredientsLlm.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_INGREDIENTS));
        stepsLlm.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_STEPS));

    }


}



