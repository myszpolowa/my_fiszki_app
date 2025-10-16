package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister, textViewReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewReset = findViewById(R.id.textViewReset);

        buttonLogin.setOnClickListener(v -> {
            loginUser();
        });

        textViewRegister.setOnClickListener(v -> startActivity(new Intent(this, registration_activity.class)));
        textViewReset.setOnClickListener(v -> startActivity(new Intent(this, reset_password_activity.class)));
    }

    private void loginUser() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.isEmpty()) {
            editTextUsername.setError("Please enter username");
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter password");
            return;
        }

        startActivity(new Intent(this, home_activity.class));
        finish();
    }
}