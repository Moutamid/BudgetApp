package com.moutamid.budgetmanagementapp.authetications;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.budgetmanagementapp.MainActivity;
import com.moutamid.budgetmanagementapp.R;

public class SignupActivity extends AppCompatActivity {

//    private EditText inputEmail, inputPassword, inputName;
//    private Button btnSignUp;
    private TextView btnSignIn;
//    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.moutamid.budgetmanagementapp.R.layout.activity_signup);
//        LinearLayout main_layout = findViewById(R.id.main_layout);
//        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance();
        CheckBox textViewTerms = findViewById(R.id.textView_terms);

        String text = "I carefully read all terms and conditions and accept it";
        SpannableString spannableString = new SpannableString(text);

        // Define the clickable span
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                Toast.makeText(SignupActivity.this, "Terms and Conditions Clicked", Toast.LENGTH_SHORT).show();
                // Here you can handle the click, e.g., open a new activity or a dialog
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
        textViewTerms.setMovementMethod(LinkMovementMethod.getInstance()); // This is important for the link to be clickable
//        inputName = (EditText)findViewById(R.id.name);

        btnSignIn = (TextView)

                findViewById(R.id.btnSignIn);
//
//        btnSignUp = (Button)
//
//                findViewById(R.id.sign_up_button);

//        inputEmail = (EditText)
//
//                findViewById(R.id.email);
//
//        inputPassword = (EditText)
//
//                findViewById(R.id.password);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String email = inputEmail.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();
//                String name = inputName.getText().toString().trim();
//
//                if (TextUtils.isEmpty(name)) {
//                    show_toast("Enter name", 0);
//                    return;
//                }
//                if (TextUtils.isEmpty(email)) {
//                    show_toast("Enter email address!", 0);
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    show_toast("Enter password!", 0);
//                    return;
//                }
//
//
//                if (password.length() < 6) {
//                    show_toast("Password too short, enter minimum 6 characters!", 0);
//                    return;
//                }
//

////                progressBar.setVisibility(View.VISIBLE);
//                Dialog lodingbar = new Dialog(SignupActivity.this);
//                lodingbar.setContentView(R.layout.loading);
//                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
//                lodingbar.setCancelable(false);
//                lodingbar.show();
//
//                //create user
//                auth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (!task.isSuccessful()) {
//                                    show_toast("Authentication failed." + task.getException(), 0);
//                                } else {
//                startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                finishAffinity();


//                                }
//                            }
//                        });

//            }
//        });
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
            layout = inflater.inflate(R.layout.toast_wrong,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
        } else {
            layout = inflater.inflate(R.layout.toast_right,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

        }
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
