package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister, textViewReset;
    private user_database_helper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDbHelper = new user_database_helper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewReset = findViewById(R.id.textViewReset);

        buttonLogin.setOnClickListener(v -> {loginUser();});

        textViewRegister.setOnClickListener(v -> startActivity(new Intent(this, registration_activity.class)));
        textViewReset.setOnClickListener(v -> startActivity(new Intent(this, reset_password_activity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        userDbHelper.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userDbHelper.close();
    }

    private void loginUser() {
        String login = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (login.isEmpty()) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }

        if (!userDbHelper.checkUserExists(login)) {
            editTextUsername.setError("User not found");
            editTextUsername.requestFocus();
            return;
        }

        if (!userDbHelper.validateUser(login, password)) {
            editTextPassword.setError("Incorrect password");
            editTextPassword.requestFocus();
            return;
        }

        user user = userDbHelper.getUser(login);

        Intent intent = new Intent(this, home_activity.class);

        if (user != null) {
            intent.putExtra("LOGIN", user.get_login());
            intent.putExtra("PROGRES", user.get_progress());
        }

        startActivity(intent);
        finish();
    }
}