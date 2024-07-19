package com.moutamid.budgetmanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.adapter.LanguageSpinnerAdapter;
import com.moutamid.budgetmanagementapp.adapter.SliderAdapter;
import com.moutamid.budgetmanagementapp.authetications.LoginActivity;
import com.moutamid.budgetmanagementapp.model.LanguageItem;
import com.moutamid.budgetmanagementapp.model.SliderData;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {
    String url1 = "https://firebasestorage.googleapis.com/v0/b/childfr-35a43.appspot.com/o/tst.jpg?alt=media&token=0703b6c0-5de4-4b86-b8d9-6c65d9c6632a";
    String url2 = "https://firebasestorage.googleapis.com/v0/b/childfr-35a43.appspot.com/o/tst.jpg?alt=media&token=0703b6c0-5de4-4b86-b8d9-6c65d9c6632a";
    String url3 = "https://firebasestorage.googleapis.com/v0/b/childfr-35a43.appspot.com/o/tst.jpg?alt=media&token=0703b6c0-5de4-4b86-b8d9-6c65d9c6632a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderData(R.drawable.slider3, getString(R.string.smart_budgeting_for_a_brighter_future)));
        sliderDataArrayList.add(new SliderData(R.drawable.slider2, getString(R.string.spend_wisely_save_easily)));
        sliderDataArrayList.add(new SliderData(R.drawable.slider1, getString(R.string.empowering_you_to_take_control_of_your_finances)));
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        List<LanguageItem> languageList = new ArrayList<>();
        languageList.add(new LanguageItem("English", R.drawable.united_states));;
        languageList.add(new LanguageItem("Italian", R.drawable.italy));
        Spinner spinner = findViewById(R.id.spinner);
        LanguageSpinnerAdapter adapter1 = new LanguageSpinnerAdapter(this, languageList);
        spinner.setAdapter(adapter1);
    }

    public void start(View view) {
        Stash.put("onBoarding", true);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
