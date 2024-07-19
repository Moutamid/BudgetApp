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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.budgetmanagementapp.model.Income;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editTextExpenseAmount;
    private Spinner spinnerExpenseCategory;
    private EditText editTextExpenseDate;
//    private EditText editTextExpenseDescription;
    private Button buttonSaveExpense;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

        editTextExpenseAmount = findViewById(R.id.editTextExpenseAmount);
        spinnerExpenseCategory = findViewById(R.id.spinnerExpenseCategory);
        editTextExpenseDate = findViewById(R.id.editTextExpenseDate);
//        editTextExpenseDescription = findViewById(R.id.editTextExpenseDescription);
        buttonSaveExpense = findViewById(R.id.buttonSaveExpense);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("expenses");

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
        String amount = editTextExpenseAmount.getText().toString().trim();
        String category = spinnerExpenseCategory.getSelectedItem().toString();
        String date = editTextExpenseDate.getText().toString().trim();
//        String description = editTextExpenseDescription.getText().toString().trim();

        if (amount.isEmpty() || date.isEmpty() || category.equals("Select Category")) {
            Toast.makeText(this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an Expense object
        Income expense = new Income(amount, category, date);

        // Save the expense object to Firebase Database
        String expenseId = databaseReference.push().getKey();
        databaseReference.child(expenseId).setValue(expense);

        Toast.makeText(this, getString(R.string.expense_saved_successfully), Toast.LENGTH_SHORT).show();

        // Clear the form fields
        editTextExpenseAmount.setText("");
        editTextExpenseDate.setText("");
//        editTextExpenseDescription.setText("");
        spinnerExpenseCategory.setSelection(0);
    }

    public void back(View view) {
        onBackPressed();
    }
}
