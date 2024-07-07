package com.moutamid.budgetmanagementapp.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.activities.NewSavingGoalActivity;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;

public class SlideAdapter extends SliderViewAdapter<SlideAdapter.SliderViewHolder> {

    private final List<SliderModel> mSliderItems;
    private final Context mContext;

    public SlideAdapter(Context context, List<SliderModel> sliderItems) {
        this.mContext = context;
        this.mSliderItems = sliderItems;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slides, null);
        return new SliderViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, final int position) {
        SliderModel sliderItem = mSliderItems.get(position);

        viewHolder.textViewTitle.setText(sliderItem.getTitle());
        viewHolder.progressBar.setProgress(sliderItem.getProgress());
viewHolder.create_new_goal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mContext.startActivity(new Intent(mContext, NewSavingGoalActivity.class));
    }
});
        // You can add click listeners or any other customization here if needed
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        TextView textViewTitle;
        ProgressBar progressBar;
        AppCompatButton create_new_goal;

        public SliderViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            progressBar = itemView.findViewById(R.id.progressBar);
            create_new_goal = itemView.findViewById(R.id.create_new_goal);
        }
    }
}
