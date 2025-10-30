package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class reset_password_activity extends AppCompatActivity {
    private EditText editTextCode, editTextUserLogin, editTextNewPassword, editTextConfirmPassword;
    private Button buttonReset;
    private ImageButton buttonBackLogin;
    private user_database_helper db_helper;
    private String generatedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        db_helper  = new user_database_helper(this);

        editTextCode = findViewById(R.id.editTextCode);
        editTextUserLogin = findViewById(R.id.editTextUserLogin);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonReset = findViewById(R.id.buttonReset);
        buttonBackLogin = findViewById(R.id.buttonBackLoginRP);

        buttonReset.setOnClickListener(v -> {
            resetPassword();
        });

        buttonBackLogin.setOnClickListener(v -> goBackToLogin());

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
}