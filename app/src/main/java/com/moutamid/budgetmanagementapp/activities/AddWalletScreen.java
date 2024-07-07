package com.moutamid.budgetmanagementapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.moutamid.budgetmanagementapp.R;

public class AddWalletScreen extends AppCompatActivity {
    private EditText editTextTotalMoney;
    private Switch switchNotificationPermission;
    private Spinner spinnerCurrency;
    private Button buttonSaveWallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet_screen);
        editTextTotalMoney = findViewById(R.id.editTextTotalMoney);
        switchNotificationPermission = findViewById(R.id.switchNotificationPermission);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        buttonSaveWallet = findViewById(R.id.buttonSaveWallet);

        // Populate the spinner with currency options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);
        buttonSaveWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWallet();
            }
        });
    }

    private void saveWallet() {


        Toast.makeText(this, "Wallet saved successfully!", Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        onBackPressed();
    }
}