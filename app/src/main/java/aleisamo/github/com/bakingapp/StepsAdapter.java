package aleisamo.github.com.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private final List<Step> steps;
    private OnItemClickListener clickListener;

    public StepsAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_short_description, parent, false);
        StepsViewHolder stepsViewHolder = new StepsViewHolder(v,clickListener,steps);
        return stepsViewHolder;
       }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        String shortDescription = steps.get(position).getShortDescription();
        holder.mTextSHortDescription.setText(shortDescription);
    }


    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
