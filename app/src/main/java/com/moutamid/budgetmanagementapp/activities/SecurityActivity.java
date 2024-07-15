package com.moutamid.budgetmanagementapp.activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moutamid.budgetmanagementapp.R;

public class SecurityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_security);
    }
    public void back(View view) {
        onBackPressed();
    }

    public void setPin(View view) {
        startActivity(new Intent(this, SetPinActivity.class));
    }
}