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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editTextChangeUsername = findViewById(R.id.editTextChangeUsername);
        editTextChangePassword = findViewById(R.id.editTextChangePassword);
        buttonSaveUsername = findViewById(R.id.buttonSaveUsername);
        buttonSavePassword = findViewById(R.id.buttonSavePassword);
        buttonBack = findViewById(R.id.buttonBack);

        buttonSaveUsername.setOnClickListener(v -> changeUsername());
        buttonSavePassword.setOnClickListener(v -> changePassword());
        buttonBack.setOnClickListener(v -> goBackToHome());
    }

    private void changeUsername() {
        String newUsername = editTextChangeUsername.getText().toString();
        Intent intent = new Intent(this, home_activity.class);
        if (newUsername.isEmpty()) {
            editTextChangeUsername.setError("Enter new username");
            return;
        }
        intent.putExtra("LOGIN", newUsername);
        Toast.makeText(this, "Username changed to: " + newUsername, Toast.LENGTH_SHORT).show();
        editTextChangeUsername.setText("");
    }

    private void changePassword() {
        String newPassword = editTextChangePassword.getText().toString();
        if (newPassword.isEmpty()) {
            editTextChangePassword.setError("Enter new password");
            return;
        }
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
        editTextChangePassword.setText("");
    }

    private void goBackToHome() {
        startActivity(new Intent(this, home_activity.class));
        finish();
    }
}