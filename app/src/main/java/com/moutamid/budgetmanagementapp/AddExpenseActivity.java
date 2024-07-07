package com.moutamid.budgetmanagementapp;
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

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editTextExpenseAmount;
    private Spinner spinnerExpenseCategory;
    private EditText editTextExpenseDate;
    private EditText editTextExpenseDescription;
    private Button buttonSaveExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editTextExpenseAmount = findViewById(R.id.editTextExpenseAmount);
        spinnerExpenseCategory = findViewById(R.id.spinnerExpenseCategory);
        editTextExpenseDate = findViewById(R.id.editTextExpenseDate);
        editTextExpenseDescription = findViewById(R.id.editTextExpenseDescription);
        buttonSaveExpense = findViewById(R.id.buttonSaveExpense);

        // Populate the spinner with expense categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpenseCategory.setAdapter(adapter);

        // Set up date picker for expense date
        editTextExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddExpenseActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextExpenseDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void saveExpense() {

        // TODO: Save this data to your database or handle it as needed
        // Example: You can create an Expense object and save it to Room database

        Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        onBackPressed();
    }
}
