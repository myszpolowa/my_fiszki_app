package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class setting_activity extends AppCompatActivity {
    private EditText editTextChangeUsername, editTextChangePassword;
    private Button buttonSaveUsername, buttonSavePassword;
    private ImageButton buttonBack;

    private user_database_helper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editTextChangeUsername = findViewById(R.id.editTextChangeUsername);
        editTextChangePassword = findViewById(R.id.editTextChangePassword);
        buttonSaveUsername = findViewById(R.id.buttonSaveUsername);
        buttonSavePassword = findViewById(R.id.buttonSavePassword);
        buttonBack = findViewById(R.id.buttonBack);

        dbHelper = new user_database_helper(this);
        dbHelper.open();

        currentUsername = getIntent().getStringExtra("LOGIN");

        buttonSaveUsername.setOnClickListener(v -> changeUsername());
        buttonSavePassword.setOnClickListener(v -> changePassword());
        buttonBack.setOnClickListener(v -> goBackToHome());
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }

    private void changeUsername() {
        String newUsername = editTextChangeUsername.getText().toString().trim();
        if (newUsername.isEmpty()) {
            editTextChangeUsername.setError("Enter new username");
            return;
        }

        boolean success = dbHelper.updateUsername(currentUsername, newUsername);
        if (success) {
            Toast.makeText(this, "Username changed to: " + newUsername, Toast.LENGTH_SHORT).show();
            currentUsername = newUsername;
            editTextChangeUsername.setText("");
        } else {
            Toast.makeText(this, "Error changing username", Toast.LENGTH_SHORT).show();
        }
    }


    private void changePassword() {
        String newPassword = editTextChangePassword.getText().toString().trim();
        if (newPassword.isEmpty()) {
            editTextChangePassword.setError("Enter new password");
            return;
        }

        boolean success = dbHelper.resetPassword(currentUsername, newPassword);
        if (success) {
            Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
            editTextChangePassword.setText("");
        } else {
            Toast.makeText(this, "Error changing password", Toast.LENGTH_SHORT).show();
        }
    }

    private void goBackToHome() {
        startActivity(new Intent(this, home_activity.class));
        finish();
    }
}