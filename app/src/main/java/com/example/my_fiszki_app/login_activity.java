package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister, textViewReset;
    private ImageButton buttonTogglePassword;
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
        buttonTogglePassword = findViewById(R.id.buttonTogglePassword);

        buttonLogin.setOnClickListener(v -> loginUser());

        textViewRegister.setOnClickListener(v -> startActivity(new Intent(this, registration_activity.class)));
        textViewReset.setOnClickListener(v -> startActivity(new Intent(this, reset_password_activity.class)));

        // Toggle password visibility
        buttonTogglePassword.setOnClickListener(v -> {
            if (editTextPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // Pokaż hasło
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                buttonTogglePassword.setImageResource(R.drawable.ic_eye);

            } else {
                // Ukryj hasło
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                buttonTogglePassword.setImageResource(R.drawable.ic_eye_cl);
            }
            editTextPassword.setSelection(editTextPassword.getText().length());
        });
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
