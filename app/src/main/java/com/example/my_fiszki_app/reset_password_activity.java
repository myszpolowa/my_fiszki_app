package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class reset_password_activity extends AppCompatActivity {
    private EditText editTextCode, editTextUserLogin, editTextNewPassword, editTextConfirmPassword;
    private Button buttonReset;
    private ImageButton buttonBackLoginRP;
    private user_database_helper db_helper;
    private String generatedCode;

    private View passwordStrengthBar;
    private TextView passwordStrengthText;
    private TextView textViewPasswordError;

    private boolean isValidPassword(String password) {
        return password != null && password.matches("(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        db_helper = new user_database_helper(this);

        editTextCode = findViewById(R.id.editTextCode);
        editTextUserLogin = findViewById(R.id.editTextUserLogin);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonReset = findViewById(R.id.buttonReset);
        buttonBackLoginRP = findViewById(R.id.buttonBackLoginRP);

        passwordStrengthBar = findViewById(R.id.passwordStrengthBar);
        passwordStrengthText = findViewById(R.id.passwordStrengthText);
        textViewPasswordError = findViewById(R.id.textViewPasswordError);

        generatedCode = "1111";

        buttonReset.setOnClickListener(v -> resetPassword());
        buttonBackLoginRP.setOnClickListener(v -> goBackToLogin());

        editTextNewPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = s == null ? "" : s.toString();
                updatePasswordStrength(pwd);

                if (isValidPassword(pwd)) {
                    textViewPasswordError.setVisibility(View.GONE);
                    editTextNewPassword.setError(null);
                } else {
                    textViewPasswordError.setVisibility(View.VISIBLE);
                    textViewPasswordError.setText("The password must have ≥8 characters, 1 uppercase letter and 1 special character");
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        db_helper.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db_helper.close();
    }

    private void resetPassword() {
        String code = editTextCode.getText().toString();
        String username = editTextUserLogin.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!db_helper.checkUserExists(username)) {
            editTextUserLogin.setError("User not found");
            editTextUserLogin.requestFocus();
            return;
        }

        if (!code.equals(generatedCode)) {
            editTextCode.setError("Invalid code. Use 1111");
            Toast.makeText(this, "Use code: 1111", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.isEmpty()) {
            editTextNewPassword.setError("Enter new password");
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Confirm password");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords don't match");
            return;
        }

        if (!isValidPassword(newPassword)) {
            editTextNewPassword.setError("The password must be at least 8 characters long, one uppercase letter and one special character");
            textViewPasswordError.setVisibility(View.VISIBLE);
            textViewPasswordError.setText("The password must have ≥8 characters, 1 uppercase letter and 1 special character");
            return;
        }

        boolean success = db_helper.resetPassword(username, newPassword);
        if (success) {
            Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, login_activity.class));
            finish();
        } else {
            Toast.makeText(this, "Error updating password", Toast.LENGTH_SHORT).show();
        }
    }

    private void goBackToLogin() {
        startActivity(new Intent(this, login_activity.class));
        finish();
    }

    private void updatePasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            passwordStrengthBar.setVisibility(View.GONE);
            passwordStrengthText.setVisibility(View.GONE);
            return;
        }

        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[^A-Za-z0-9].*")) score++;

        int color;
        String label;
        if (score == 3 && password.length() >= 12) {
            color = Color.parseColor("#4CAF50");
            label = "Strong";
        } else if (score >= 2) {
            color = Color.parseColor("#FFEB3B");
            label = "Medium";
        } else {
            color = Color.parseColor("#F44336");
            label = "Weak";
        }

        passwordStrengthBar.setVisibility(View.VISIBLE);
        passwordStrengthBar.setBackgroundColor(color);
        passwordStrengthText.setVisibility(View.VISIBLE);
        passwordStrengthText.setText(label);
        passwordStrengthText.setTextColor(color);
    }
}
