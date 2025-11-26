package com.example.my_fiszki_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LevelCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);

        TextView tvPercent = findViewById(R.id.tvPercent);
        TextView tvCorrect = findViewById(R.id.tvCorrect);

        Button btnTryAgain = findViewById(R.id.btnTryAgain);
        Button btnMainMenu = findViewById(R.id.btnMainMenu);

        // Odbieranie danych z PlayActivity
        int correct = getIntent().getIntExtra("correct", 0);
        int total = getIntent().getIntExtra("total", 0);
        int percent = (int) ((correct * 100.0f) / total);

        tvPercent.setText(percent + "%");
        tvCorrect.setText("Correct: " + correct + " / " + total);

        // Restart level
        btnTryAgain.setOnClickListener(v -> {
            Intent i = new Intent(LevelCompleteActivity.this, PlayActivity.class);
            startActivity(i);
            finish();
        });

        // Powrót do menu
        btnMainMenu.setOnClickListener(v -> {
            Intent i = new Intent(LevelCompleteActivity.this, home_activity.class);
            startActivity(i);
            finish();
        });
    }
}

