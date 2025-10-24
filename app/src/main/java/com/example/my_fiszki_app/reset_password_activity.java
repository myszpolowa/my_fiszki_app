package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class reset_password_activity extends AppCompatActivity {
    private EditText editTextCode, editTextUserLogin, editTextNewPassword, editTextConfirmPassword;
    private Button buttonReset;
    private user_database_helper db_helper;
    private String generatedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        db_helper  = new user_database_helper(this);

        EditText editTextCode = findViewById(R.id.editTextCode);
        EditText editTextUserLogin = findViewById(R.id.editTextUserLogin);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(v -> {
            resetPassword();
        });

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
        EditText editTextCode = findViewById(R.id.editTextCode);
        EditText editTextUserLogin = findViewById(R.id.editTextUserLogin);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        String code = editTextCode.getText().toString();
        String username = editTextUserLogin.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!db_helper.checkUserExists(username)) {
            editTextUserLogin.setError("User not found");
            editTextUserLogin.requestFocus();
            return;
        }

        if (!code.equals("1111")) {
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

        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, login_activity.class));
        finish();
    }
}