package com.moutamid.budgetmanagementapp.authetications;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.budgetmanagementapp.MainActivity;
import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.activities.SelectCurrencyActivity;

import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName;
    private Button btnSignUp;
    private TextView btnSignIn;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users");

        CheckBox textViewTerms = findViewById(R.id.textView_terms);
        String text = "I carefully read all terms and conditions and accept it";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Handle the click, e.g., open a new activity or a dialog
            }
        };

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.yellow));
        UnderlineSpan underlineSpan = new UnderlineSpan();
        int start = text.indexOf("terms and conditions");
        int end = start + "terms and conditions".length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewTerms.setText(spannableString);
        textViewTerms.setMovementMethod(LinkMovementMethod.getInstance());

        inputName = findViewById(R.id.name);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String name = inputName.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    show_toast("Enter name", 0);
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    show_toast("Enter email address!", 0);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    show_toast("Enter password!", 0);
                    return;
                }
                if (password.length() < 6) {
                    show_toast("Password too short, enter minimum 6 characters!", 0);
                    return;
                }

                Dialog loadingBar = new Dialog(SignupActivity.this);
                loadingBar.setContentView(R.layout.loading);
                Objects.requireNonNull(loadingBar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                loadingBar.setCancelable(false);
                loadingBar.show();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingBar.dismiss();
                                if (!task.isSuccessful()) {
                                    show_toast("Authentication failed: " + task.getException(), 0);
                                } else {
                                    String userId = auth.getCurrentUser().getUid();
                                    HashMap<String, String> userData = new HashMap<>();
                                    userData.put("name", name);
                                    userData.put("email", email);

                                    databaseReference.child(userId).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SignupActivity.this, SelectCurrencyActivity.class));
                                                finishAffinity();
                                            } else {
                                                show_toast("Failed to store user data: " + task.getException(), 0);
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void back(View view) {
        onBackPressed();
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
}
