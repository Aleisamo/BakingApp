package aleisamo.github.com.bakingapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.card_short_description)
    CardView mCardShortDescription;
    @BindView(R.id.text_short_description)
    TextView mTextSHortDescription;

    public StepsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }




}
