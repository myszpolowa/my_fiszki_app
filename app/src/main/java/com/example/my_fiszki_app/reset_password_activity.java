package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class reset_password_activity extends AppCompatActivity {
    private EditText editTextCode, editTextNewPassword, editTextConfirmPassword;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // initializing elements - using  IDs from XML
        EditText editTextCode = findViewById(R.id.editTextCode);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonReset = findViewById(R.id.buttonReset);

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
    }

    private void resetPassword() {
        EditText editTextCode = findViewById(R.id.editTextCode);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        if (editTextCode == null || editTextNewPassword == null || editTextConfirmPassword == null) {
            Toast.makeText(this, "Error: UI elements not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String code = editTextCode.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // checking code
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

        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, login_activity.class));
        finish();
    }
}