package com.moutamid.budgetmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moutamid.budgetmanagementapp.MainActivity;
import com.moutamid.budgetmanagementapp.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }


    public void settings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));

    }  public void intellegence(View view) {
        startActivity(new Intent(this, IntelligentFeaturesActivity.class));

    }  public void security(View view) {
        startActivity(new Intent(this, SecurityActivity.class));

    }  public void personlizeApp(View view) {
        startActivity(new Intent(this, PersonlizeMyApActivity.class));

    }

    public void main(View view) {
        startActivity(new Intent(this, MainActivity.class));
finishAffinity();

    }
}