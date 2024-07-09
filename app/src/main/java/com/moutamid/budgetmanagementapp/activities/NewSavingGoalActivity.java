package com.moutamid.budgetmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.moutamid.budgetmanagementapp.R;

public class NewSavingGoalActivity extends AppCompatActivity {
    private EditText editTextGoalName, editTextTimeOfAchievement, editTextNotificationTitle, editTextNotificationDescription;
    private Spinner spinnerPaymentOption;
    private Button buttonSaveGoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_saving_goal);
        editTextGoalName = findViewById(R.id.editTextGoalName);
        editTextTimeOfAchievement = findViewById(R.id.editTextTimeOfAchievement);
        spinnerPaymentOption = findViewById(R.id.spinnerPaymentOption);
        editTextNotificationTitle = findViewById(R.id.editTextNotificationTitle);
        editTextNotificationDescription = findViewById(R.id.editTextNotificationDescription);
        buttonSaveGoal = findViewById(R.id.buttonSaveGoal);
        String[] paymentOptions = {"Cash", "Card", "Bank Transfer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentOption.setAdapter(adapter);

        buttonSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });
    }
    private void saveGoal() {
        String goalName = editTextGoalName.getText().toString().trim();
        String timeOfAchievement = editTextTimeOfAchievement.getText().toString().trim();
        String paymentOption = spinnerPaymentOption.getSelectedItem().toString();
        String notificationTitle = editTextNotificationTitle.getText().toString().trim();
        String notificationDescription = editTextNotificationDescription.getText().toString().trim();

        // Validate inputs
        if (goalName.isEmpty() || timeOfAchievement.isEmpty() || notificationTitle.isEmpty() || notificationDescription.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the goal (this part depends on your implementation, e.g., saving to a database)
        // Example:
        // SavingGoal goal = new SavingGoal(goalName, timeOfAchievement, paymentOption, notificationTitle, notificationDescription);
        // goal.save();

        Toast.makeText(this, "Saving Goal Saved", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
    }

    public void back(View view) {
        onBackPressed();
    }
}