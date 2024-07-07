package com.moutamid.budgetmanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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
        sliderDataArrayList.add(new SliderData(R.drawable.slider3, "Smart Budgeting for a Brighter Future."));
        sliderDataArrayList.add(new SliderData(R.drawable.slider2, "Spend Wisely, Save Easily."));
        sliderDataArrayList.add(new SliderData(R.drawable.slider1, "Empowering You to Take Control of Your Finances."));
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        List<LanguageItem> languageList = new ArrayList<>();
        languageList.add(new LanguageItem("English", R.drawable.united_states));
        languageList.add(new LanguageItem("Spanish", R.drawable.flag));
        languageList.add(new LanguageItem("French", R.drawable.france));
        languageList.add(new LanguageItem("German", R.drawable.germany));
        languageList.add(new LanguageItem("Chinese", R.drawable.china));
        languageList.add(new LanguageItem("Japanese", R.drawable.japan));
        languageList.add(new LanguageItem("Russian", R.drawable.russia));
        languageList.add(new LanguageItem("Italian", R.drawable.italy));
        languageList.add(new LanguageItem("Portuguese", R.drawable.purtagal));
        languageList.add(new LanguageItem("Hindi", R.drawable.india));
        Spinner spinner = findViewById(R.id.spinner);
        LanguageSpinnerAdapter adapter1 = new LanguageSpinnerAdapter(this, languageList);
        spinner.setAdapter(adapter1);
    }

    public void start(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
