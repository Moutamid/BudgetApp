package com.moutamid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.budgetmanagementapp.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.budgetmanagementapp.model.Income;

public class AddIncomeActivity extends AppCompatActivity {

    private EditText editTextIncomeAmount;
    private Spinner spinnerIncomeSource;
    private EditText editTextIncomeDate;
    private EditText editTextIncomeDescription;
    private Button buttonSaveIncome;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_add_income);

        editTextIncomeAmount = findViewById(R.id.editTextIncomeAmount);
        spinnerIncomeSource = findViewById(R.id.spinnerIncomeSource);
        editTextIncomeDate = findViewById(R.id.editTextIncomeDate);
        editTextIncomeDescription = findViewById(R.id.editTextIncomeDescription);
        buttonSaveIncome = findViewById(R.id.buttonSaveIncome);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("incomes");

        // Populate the spinner with income sources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.income_source_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncomeSource.setAdapter(adapter);

        // Set up date picker for income date
        editTextIncomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIncome();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddIncomeActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextIncomeDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void saveIncome() {
        String amount = editTextIncomeAmount.getText().toString().trim();
        String source = spinnerIncomeSource.getSelectedItem().toString();
        String date = editTextIncomeDate.getText().toString().trim();
        String description = editTextIncomeDescription.getText().toString().trim();

        if (amount.isEmpty() || date.isEmpty() || source.equals("Select Source")) {
            Toast.makeText(this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an Income object
        Income income = new Income(amount, source, date, description);

        // Save the income object to Firebase Database
        String incomeId = databaseReference.push().getKey();
        databaseReference.child(incomeId).setValue(income);

        Toast.makeText(this, "Income saved successfully!", Toast.LENGTH_SHORT).show();

        // Clear the form fields
        editTextIncomeAmount.setText("");
        editTextIncomeDate.setText("");
        editTextIncomeDescription.setText("");
        spinnerIncomeSource.setSelection(0);
    }

    public void back(View view) {
        onBackPressed();
    }
}
