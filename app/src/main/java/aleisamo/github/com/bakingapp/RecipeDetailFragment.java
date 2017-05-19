package aleisamo.github.com.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.recipe_details_recycleView)
    RecyclerView mRecycleRecipeDetail;

    public RecipeDetailFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_recipes,container,false);
        ButterKnife.bind(this,rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecycleRecipeDetail.setLayoutManager(llm);
        return rootView;
    }
}
