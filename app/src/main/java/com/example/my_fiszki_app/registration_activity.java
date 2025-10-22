package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;

public class registration_activity extends AppCompatActivity {
    private EditText editTextNewUsername, editTextNewPassword, editTextConfirmPassword;
    private View passwordStrengthBar;
    private TextView passwordStrengthText;
    private Button buttonRegister;
    private TextView textViewPasswordError;

    private boolean isValidPassword(String password) {

        return password != null && password.matches("(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        // strength UI user interface
        passwordStrengthBar = findViewById(R.id.passwordStrengthBar);
        passwordStrengthText = findViewById(R.id.passwordStrengthText);

        buttonRegister.setOnClickListener(v -> registerUser());

        textViewPasswordError = findViewById(R.id.textViewPasswordError);

        editTextNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = s == null ? "" : s.toString();

                // update strength bar live
                updatePasswordStrength(pwd);

                // show/hide textual policy hint
                if (isValidPassword(pwd)) {
                    if (textViewPasswordError != null)
                        textViewPasswordError.setVisibility(View.GONE);
                    if (editTextNewPassword != null) editTextNewPassword.setError(null);
                } else {
                    if (textViewPasswordError != null) {
                        textViewPasswordError.setVisibility(View.VISIBLE);
                        textViewPasswordError.setText("The password must have ≥8 characters, 1 uppercase letter and 1 special characters");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void registerUser() {
        String username = editTextNewUsername.getText().toString();
        String password = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (username.isEmpty()) {
            editTextNewUsername.setError("Please enter username");
            return;
        }
        if (password.isEmpty()) {
            editTextNewPassword.setError("Please enter password");
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Please confirm password");
            return;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords don't match");
            return;
        }

        if (!isValidPassword(password)) {
            editTextNewPassword.setError("The password must be at least 8 characters long, one uppercase letter and one special character");
            if (textViewPasswordError != null) {
                textViewPasswordError.setVisibility(View.VISIBLE);
                textViewPasswordError.setText("The password must have ≥8 characters, 1 uppercase letter and 1 special character");
            }
            return;
        }

        // temporal logic - always successful registration
        startActivity(new Intent(this, login_activity.class));
        finish();
    }

    private void updatePasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            if (passwordStrengthBar != null) passwordStrengthBar.setVisibility(View.GONE);
            if (passwordStrengthText != null) passwordStrengthText.setVisibility(View.GONE);
            return;
        }

        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[^A-Za-z0-9].*")) score++;

        int color;
        String label;
        if (score == 3 && password.length() >= 12) {
            color = Color.parseColor("#4CAF50"); // green
            label = "Strong";
        } else if (score >= 2) {
            color = Color.parseColor("#FFEB3B"); // yellow
            label = "Medium";
        } else {
            color = Color.parseColor("#F44336"); // red
            label = "Weak";
        }

        // guard nulls
        if (passwordStrengthBar != null) {
            passwordStrengthBar.setVisibility(View.VISIBLE);
            passwordStrengthBar.setBackgroundColor(color);
        }
        if (passwordStrengthText != null) {
            passwordStrengthText.setVisibility(View.VISIBLE);
            passwordStrengthText.setText(label);
            passwordStrengthText.setTextColor(color);
        }
    }
}
