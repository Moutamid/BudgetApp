package com.moutamid.budgetmanagementapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.budgetmanagementapp.R;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class IntelligentFeaturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intelligent_features);
        LinearLayout main_layout = findViewById(R.id.main_layout);

        if (!Stash.getBoolean("intell_check", false)) {
            Stash.put("intell_check", true);
            createGuideView(main_layout, "Intelligent Feature", "You can enable / disable update and other feature to alert yourself before purchasing", null);
        }


    }
    public void back(View view) {
        onBackPressed();
    }

    private void createGuideView(View targetView, String title, String contentText, Runnable onDismiss) {
        targetView.setFocusable(true);
        new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(contentText)
                .setDismissType(DismissType.anywhere) // optional - default DismissType.targetView
                .setTargetView(targetView)
                .setContentTextSize(12) // optional
                .setTitleTextSize(14) // optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        if (onDismiss != null) {
                            onDismiss.run();
                        }
                    }
                })
                .build()
                .show();
    }
}