package com.moutamid.budgetmanagementapp.activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.helper.CustomDialogClass;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void currency(View view) {
        startActivity(new Intent(this, SelectCurrencyActivity.class));
    }

    public void change_language(View view) {
        CustomDialogClass customDialogClass= new CustomDialogClass(this);
        customDialogClass.show();
    }
}