package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class home_activity extends AppCompatActivity {

    private ImageButton buttonSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        buttonSetting = findViewById(R.id.buttonSetting);

        buttonSetting.setOnClickListener(v -> goBackToSetting());
    }

    private void goBackToSetting() {
        startActivity(new Intent(this, setting_activity.class));
        finish();
    }
}