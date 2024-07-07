package com.moutamid.budgetmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.moutamid.budgetmanagementapp.R;

public class PersonlizeMyApActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personlize_my_ap);
    }
    public void back(View view) {
        onBackPressed();
    }
}