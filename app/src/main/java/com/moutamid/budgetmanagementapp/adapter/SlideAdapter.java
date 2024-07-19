package com.moutamid.budgetmanagementapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.activities.AddGoalsActivity;
import com.moutamid.budgetmanagementapp.activities.NewSavingGoalActivity;
import com.moutamid.budgetmanagementapp.model.SliderModel;
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

        viewHolder.amount.setText(sliderItem.getAlreadySaved() + "/" + sliderItem.getAmount());
        viewHolder.textViewTitle.setText(sliderItem.getGoalName());
        viewHolder.progressBar.setProgress(sliderItem.getProgress());
        viewHolder.create_new_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, NewSavingGoalActivity.class));
            }
        });
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int amount = sliderItem.getAmount();
                Intent intent = new Intent(mContext, AddGoalsActivity.class);
                intent.putExtra("GOAL_NAME", sliderItem.getGoalName());
                intent.putExtra("GOAL_AMOUNT", Float.parseFloat(String.valueOf(sliderItem.getAmount())));
                intent.putExtra("GOAL_ID", sliderItem.getKey());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        TextView textViewTitle, amount;
        ProgressBar progressBar;
        AppCompatButton create_new_goal;
        ImageView add;

        public SliderViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            progressBar = itemView.findViewById(R.id.progressBar);
            create_new_goal = itemView.findViewById(R.id.create_new_goal);
            add = itemView.findViewById(R.id.add);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
