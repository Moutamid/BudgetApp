package com.moutamid.budgetmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.moutamid.budgetmanagementapp.R;

public class IntelligentFeaturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligent_features);
    }
    public void back(View view) {
        onBackPressed();
    }
}