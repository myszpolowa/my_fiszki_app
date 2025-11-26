package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class home_activity extends AppCompatActivity {

    private ImageButton buttonSetting;
    private Button buttonLevel1, buttonLevel2, buttonLevel3, buttonLevel4, buttonLevel5;
    private TextView textViewGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Tworzenie bazy danych
        database_helper dbHelper = new database_helper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Przyciski
        buttonSetting = findViewById(R.id.buttonSetting);
        buttonLevel1 = findViewById(R.id.buttonLevel1);
        buttonLevel2 = findViewById(R.id.buttonLevel2);
        buttonLevel3 = findViewById(R.id.buttonLevel3);
        buttonLevel4 = findViewById(R.id.buttonLevel4);
        buttonLevel5 = findViewById(R.id.buttonLevel5);

        textViewGreeting = findViewById(R.id.textViewGreeting);

        // Ustawienie powitania
        String login = getIntent().getStringExtra("LOGIN");
        if (login != null) {
            textViewGreeting.setText("hi, " + login + "!");
        }

        // Wczytanie progressu z SharedPreferences
        int progress = getSharedPreferences("FISZKI_PREFS", MODE_PRIVATE).getInt("PROGRESS", 0);

        Button[] buttons = {buttonLevel1, buttonLevel2, buttonLevel3, buttonLevel4, buttonLevel5};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(i <= progress);
        }

        // Ustawienie listenerów
        buttonSetting.setOnClickListener(v -> goBackToSetting());

        buttonLevel1.setOnClickListener(v -> goToPlay(1));
        buttonLevel2.setOnClickListener(v -> goToPlay(2));
        buttonLevel3.setOnClickListener(v -> goToPlay(3));
        buttonLevel4.setOnClickListener(v -> goToPlay(4));
        buttonLevel5.setOnClickListener(v -> goToPlay(5));
    }

    private void goBackToSetting() {
        Intent intent = new Intent(this, setting_activity.class);
        intent.putExtra("LOGIN", getIntent().getStringExtra("LOGIN"));
        startActivity(intent);
        finish();
    }

    private void goToPlay(int level) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("LEVEL", level); // przekazujemy numer levela
        startActivity(intent);
        finish();
    }
}
