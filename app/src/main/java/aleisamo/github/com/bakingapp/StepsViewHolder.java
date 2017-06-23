package aleisamo.github.com.bakingapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.card_short_description)
    CardView mCardShortDescription;
    @BindView(R.id.text_short_description)
    TextView mTextSHortDescription;

    private OnItemClickListener clickListener;
    private List<Step> steps;

    public StepsViewHolder(View itemView, OnItemClickListener clickListener, List<Step> steps) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.clickListener = clickListener;
        this.steps = steps;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClick(v, getAdapterPosition(), steps);
        }

    }
}


