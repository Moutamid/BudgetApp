package com.moutamid.budgetmanagementapp.authetications;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.fxn.stash.Stash;
import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.activities.SliderActivity;
import com.moutamid.budgetmanagementapp.helper.LocaleHelper;

import java.util.concurrent.Executor;

public class SplashActivity extends AppCompatActivity {

    private static int DELAY_TIME = 2200;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        BiometricManager biometricManager = BiometricManager.from(this);
//        switch (biometricManager.canAuthenticate()) {
//            case BiometricManager.BIOMETRIC_SUCCESS:
//                showBiometricPrompt();
//                break;
//            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//                // Handle case where no biometric hardware is present
//                break;
//            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//                // Handle case where biometric hardware is unavailable
//                break;
//            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//                // Handle case where no biometrics are enrolled
//                break;
//        }
//        ImageView textView = findViewById(R.id.imageView2);
//
//        Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
//        textView.startAnimation(zoomOut);
        context = LocaleHelper.setLocale(SplashActivity.this, "it");
        resources = context.getResources();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Stash.getBoolean("onBoarding", false)) {
                    Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },DELAY_TIME);
    }

    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle authentication error
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                startActivity(i);
                finish();
                // Handle authentication success
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Handle authentication failure
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Authenticate using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}