package com.moutamid.budgetmanagementapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.model.SavingGoal;

public class NewSavingGoalActivity extends AppCompatActivity {
    private EditText editTextAmount, editTextSaved, editTextNote, editTextGoalName, editTextTimeOfAchievement, editTextNotificationTitle, editTextNotificationDescription;
    private Spinner spinnerPaymentOption;
    private Button buttonSaveGoal;

//    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_saving_goal);
//        databaseReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SavingGoals");
        editTextNote = findViewById(R.id.editTextNote);
        editTextSaved = findViewById(R.id.editTextSaved);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextGoalName = findViewById(R.id.editTextGoalName);
        editTextTimeOfAchievement = findViewById(R.id.editTextTimeOfAchievement);
        spinnerPaymentOption = findViewById(R.id.spinnerPaymentOption);
        editTextNotificationTitle = findViewById(R.id.editTextNotificationTitle);
        editTextNotificationDescription = findViewById(R.id.editTextNotificationDescription);
        buttonSaveGoal = findViewById(R.id.buttonSaveGoal);
        buttonSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveGoal();
            }
        });
    }

//    private void saveGoal() {
//        String goalName = editTextGoalName.getText().toString().trim();
//        String timeOfAchievement = editTextTimeOfAchievement.getText().toString().trim();
//        String notificationTitle = editTextNotificationTitle.getText().toString().trim();
//        String notificationDescription = editTextNotificationDescription.getText().toString().trim();
//        String savedAmount = editTextSaved.getText().toString().trim();
//        String note = editTextNote.getText().toString().trim();
//        String amount = editTextAmount.getText().toString().trim();
//
//        if (savedAmount.isEmpty() || note.isEmpty() || amount.isEmpty() || goalName.isEmpty() || timeOfAchievement.isEmpty() || notificationTitle.isEmpty() || notificationDescription.isEmpty()) {
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String goalId = databaseReference.push().getKey();
//        SavingGoal savingGoal = new SavingGoal(goalName, timeOfAchievement, notificationTitle, notificationDescription, Integer.valueOf(amount), Integer.valueOf(savedAmount), note);
//        assert goalId != null;
//        databaseReference.child(goalId).setValue(savingGoal)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(NewSavingGoalActivity.this, "Saving Goal Saved", Toast.LENGTH_SHORT).show();
//                        finish(); // Close the activity
//                    } else {
//                        Toast.makeText(NewSavingGoalActivity.this, "Failed to save goal. Please try again.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    public void back(View view) {
        onBackPressed();
    }
}
