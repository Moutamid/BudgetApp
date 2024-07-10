package com.moutamid.budgetmanagementapp.authetications;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.budgetmanagementapp.MainActivity;
import com.moutamid.budgetmanagementapp.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private TextView btnSignup;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(LoginActivity.this);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
       inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    show_toast("Email address is not yet provided", 0);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    show_toast("Password is not yet provided", 0);
                    return;
                }

                // Authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // There was an error
                                    if (password.length() < 6) {
                                        show_toast(getString(R.string.minimum_password), 0);
                                    } else {
                                        show_toast(getString(R.string.auth_failed), 0);
                                    }
                                } else {
                                    Dialog loadingBar = new Dialog(LoginActivity.this);
                                    loadingBar.setContentView(R.layout.loading);
                                    Objects.requireNonNull(loadingBar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                                    loadingBar.setCancelable(false);
                                    loadingBar.show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    public void show_toast(String message, int type) {
        LayoutInflater inflater = getLayoutInflater();
        View layout;

        if (type == 0) {
            layout = inflater.inflate(R.layout.toast_wrong, (ViewGroup) findViewById(R.id.toast_layout_root));
        } else {
            layout = inflater.inflate(R.layout.toast_right, (ViewGroup) findViewById(R.id.toast_layout_root));
        }

        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void guest(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
