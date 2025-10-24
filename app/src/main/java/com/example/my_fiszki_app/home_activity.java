package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class home_activity extends AppCompatActivity {

    private ImageButton buttonSetting;
    private Button buttonLevel1;

    private TextView textViewGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        buttonSetting = findViewById(R.id.buttonSetting);
        buttonLevel1 = findViewById(R.id.buttonLevel1);
        textViewGreeting = findViewById(R.id.textViewGreeting);

        buttonSetting.setOnClickListener(v -> goBackToSetting());
        buttonLevel1.setOnClickListener(v -> goToPlay());

        String login = getIntent().getStringExtra("LOGIN");
        if (login != null) {
            textViewGreeting.setText("hi, " + login+"!");
        }
    }

    private void goBackToSetting() {
        startActivity(new Intent(this, setting_activity.class));
        finish();
    }

    private void goToPlay() {
        startActivity(new Intent(this, play_activity.class));
        finish();
    }
}