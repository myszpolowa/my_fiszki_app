package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class registration_activity extends AppCompatActivity {
    private EditText editTextNewUsername, editTextNewPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private ImageButton buttonBackLogin;
    private user_database_helper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db_helper = new user_database_helper(this);

        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonBackLogin = findViewById(R.id.buttonBackLoginR);

        buttonRegister.setOnClickListener(v -> registerUser());

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
    private void registerUser() {
        String username = editTextNewUsername.getText().toString();
        String password = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        Integer progress = 0;

        if (username.isEmpty()) {
            editTextNewUsername.setError("Please enter username");
            editTextNewUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextNewPassword.setError("Please enter password");
            editTextNewPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Please confirm password");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords don't match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (db_helper.checkUserExists(username)) {
            editTextNewUsername.setError("User with that username already exists");
            editTextNewUsername.requestFocus();
            return;
        }

        db_helper.registerUser(username, password, progress);
        startActivity(new Intent(this, login_activity.class));
        finish();
    }

    private void goBackToLogin() {
        startActivity(new Intent(this, login_activity.class));
        finish();
    }
}