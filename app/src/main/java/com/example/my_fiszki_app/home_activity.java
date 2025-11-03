package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class home_activity extends AppCompatActivity {

    private ImageButton buttonSetting;
    private Button buttonLevel1,buttonLevel2,buttonLevel3,buttonLevel4,buttonLevel5;

    private TextView textViewGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database_helper dbHelper = new database_helper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonSetting = findViewById(R.id.buttonSetting);

        buttonLevel1 = findViewById(R.id.buttonLevel1);
        buttonLevel2 = findViewById(R.id.buttonLevel2);
        buttonLevel3 = findViewById(R.id.buttonLevel3);
        buttonLevel4 = findViewById(R.id.buttonLevel4);
        buttonLevel5 = findViewById(R.id.buttonLevel5);

        textViewGreeting = findViewById(R.id.textViewGreeting);

        buttonSetting.setOnClickListener(v -> goBackToSetting());

        String login = getIntent().getStringExtra("LOGIN");
        if (login != null) {
            textViewGreeting.setText("hi, " + login+"!");
        }

        Integer progress = getIntent().getIntExtra("PROGRESS",0);
        Button[] buttons = {buttonLevel1, buttonLevel2, buttonLevel3, buttonLevel4, buttonLevel5};
        for (int i = 0; i < buttons.length; i++) {
            if (i <= progress) {
                buttons[i].setEnabled(true); // открытые уровни
            } else {
                buttons[i].setEnabled(false); // заблокированные уровни
            }
        }
        buttonLevel1.setOnClickListener(v -> goToPlay());
        buttonLevel2.setOnClickListener(v -> goToPlay());
        buttonLevel3.setOnClickListener(v -> goToPlay());
        buttonLevel4.setOnClickListener(v -> goToPlay());
        buttonLevel5.setOnClickListener(v -> goToPlay());
    }

    private void goBackToSetting() {
        Intent intent = new Intent(this, setting_activity.class);
        intent.putExtra("LOGIN", getIntent().getStringExtra("LOGIN")); // передаём текущий логин
        startActivity(intent);
        finish();
    }

    private void goToPlay() {
        startActivity(new Intent(this, play_activity.class));
        finish();
    }
}