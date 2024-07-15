package com.moutamid.budgetmanagementapp.activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moutamid.budgetmanagementapp.R;

public class SetPinActivity extends AppCompatActivity {
    private EditText pinInput;
    private Button savePinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_pin);
        pinInput = findViewById(R.id.pin_edit);
        savePinButton = findViewById(R.id.save_pin_button);

        savePinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pinInput.getText().toString();
                if (!pin.isEmpty()||pin.length()<3) {
                    savePin(pin);
                    Toast.makeText(SetPinActivity.this, "PIN saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SetPinActivity.this, "Enter a valid PIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePin(String pin) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PIN", pin);
        editor.apply();
    }

    public void back(View view) {
        onBackPressed();
    }
}
