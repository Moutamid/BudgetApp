package com.moutamid.budgetmanagementapp.activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.helper.LayoutDialogClass;
import com.moutamid.budgetmanagementapp.helper.ThemeDialogClass;
import com.smarteist.autoimageslider.SliderView;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class PersonlizeMyApActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personlize_my_ap);
showGuideViewsSequentially();
    }
    public void back(View view) {
        onBackPressed();
    }

    public void theme(View view) {
        ThemeDialogClass themeDialogClass= new ThemeDialogClass(PersonlizeMyApActivity.this);
        themeDialogClass.show();
    }
 public void layout(View view) {
        LayoutDialogClass themeDialogClass= new LayoutDialogClass(PersonlizeMyApActivity.this);
        themeDialogClass.show();
    }
    private void showGuideViewsSequentially() {
        TextView name;
        TextView layout;

        name = findViewById(R.id.name);
        layout = findViewById(R.id.layout);

        createGuideView(name, getString(R.string.app_theme), getString(R.string.tap_here_to_change_app_mode_either_in_dark_or_light), () ->
                        createGuideView(layout, getString(R.string.app_layout), getString(R.string.tap_here_to_change_whole_app_color),null


                        )
        );
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