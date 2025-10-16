package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class play_activity extends AppCompatActivity {
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(v -> goBackToHome());
    }

    private void goBackToHome() {
        startActivity(new Intent(this, home_activity.class));
        finish();
    }
}
