package com.moutamid.budgetmanagementapp.activities;

import android.os.Bundle;
import android.view.View;
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

    private EditText editTextGoalName, editTextTimeOfAchievement, editTextAmount, editTextSaved, editTextNotificationTitle, editTextNotificationDescription, editTextNote;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_saving_goal);

        databaseReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SavingGoals");
        editTextGoalName = findViewById(R.id.editTextGoalName);
        editTextTimeOfAchievement = findViewById(R.id.editTextTimeOfAchievement);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextSaved = findViewById(R.id.editTextSaved);
        editTextNotificationTitle = findViewById(R.id.editTextNotificationTitle);
        editTextNotificationDescription = findViewById(R.id.editTextNotificationDescription);
        editTextNote = findViewById(R.id.editTextNote);

        findViewById(R.id.buttonSaveGoal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });
    }

    private void saveGoal() {
        String goalName = editTextGoalName.getText().toString().trim();
        String timeOfAchievement = editTextTimeOfAchievement.getText().toString().trim();
        String amount = editTextAmount.getText().toString().trim();
        String alreadySaved = editTextSaved.getText().toString().trim();
        String notificationTitle = editTextNotificationTitle.getText().toString().trim();
        String notificationDescription = editTextNotificationDescription.getText().toString().trim();
        String note = editTextNote.getText().toString().trim();

        if (goalName.isEmpty() || timeOfAchievement.isEmpty() || amount.isEmpty() || alreadySaved.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Integer.parseInt(alreadySaved)>=Integer.parseInt(amount))
        {
            Toast.makeText(this, "You already achieved the gaol", Toast.LENGTH_SHORT).show();
            return;

        }

        String id = databaseReference.push().getKey();
        SavingGoal savingGoal = new SavingGoal(goalName, timeOfAchievement, amount, alreadySaved, notificationTitle, notificationDescription, note, id);

        if (id != null) {
            databaseReference.child(id).setValue(savingGoal).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(NewSavingGoalActivity.this, "Goal saved successfully", Toast.LENGTH_SHORT).show();
                    // Clear the form or finish the activity
                    finish();
                } else {
                    Toast.makeText(NewSavingGoalActivity.this, "Failed to save goal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
