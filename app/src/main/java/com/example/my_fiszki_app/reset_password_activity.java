package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;


public class reset_password_activity extends AppCompatActivity {
    private EditText editTextCode, editTextUserLogin, editTextNewPassword, editTextConfirmPassword;
    private Button buttonReset;
<<<<<<< HEAD
    private ImageButton buttonBackLoginRP;
    private user_database_helper db_helper;
    private String generatedCode;
=======
    private View passwordStrengthBar;
    private TextView passwordStrengthText;
    private TextView textViewPasswordError;
>>>>>>> DS

    private boolean isValidPassword(String password) {
        // min 8 znaków, przynajmniej jedna wielka litera i przynajmniej jeden znak specjalny
        return password != null && password.matches("(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        db_helper  = new user_database_helper(this);

<<<<<<< HEAD
        editTextCode = findViewById(R.id.editTextCode);
        editTextUserLogin = findViewById(R.id.editTextUserLogin);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonReset = findViewById(R.id.buttonReset);
        buttonBackLoginRP = findViewById(R.id.buttonBackLoginRP);

        buttonReset.setOnClickListener(v -> {resetPassword();});
        buttonBackLoginRP.setOnClickListener(v -> goBackToLogin());

        generatedCode = "1111";
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
=======
        // strength UI
        passwordStrengthBar = findViewById(R.id.passwordStrengthBar);
        passwordStrengthText = findViewById(R.id.passwordStrengthText);

        // checking that the elements are found
        if (editTextCode == null) {
            Toast.makeText(this, "editTextCode not found! Check XML", Toast.LENGTH_LONG).show();
        }
        if (editTextNewPassword == null) {
            Toast.makeText(this, "editTextNewPassword not found! Check XML", Toast.LENGTH_LONG).show();
        }
        if (editTextConfirmPassword == null) {
            Toast.makeText(this, "editTextConfirmPassword not found! Check XML", Toast.LENGTH_LONG).show();
        }
        if (buttonReset == null) {
            Toast.makeText(this, "buttonReset not found! Check XML", Toast.LENGTH_LONG).show();
        } else {
            buttonReset.setOnClickListener(v -> resetPassword());
        }

        // update strength while typing new password
        if (editTextNewPassword != null) {
            editTextNewPassword.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updatePasswordStrength(s == null ? "" : s.toString());
                }
                @Override public void afterTextChanged(Editable s) {}
            });
        }
>>>>>>> DS
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

<<<<<<< HEAD
        boolean success = db_helper.resetPassword(username, newPassword);
=======
        if (!isValidPassword(newPassword)) {
            editTextNewPassword.setError("The password must be at least 8 characters long, one uppercase letter and one special character");
            if (textViewPasswordError != null) {
                textViewPasswordError.setVisibility(View.VISIBLE);
                textViewPasswordError.setText("The password must have ≥8 characters, 1 uppercase letter and 1 special character");
            }
            return;
        }
        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
>>>>>>> DS

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