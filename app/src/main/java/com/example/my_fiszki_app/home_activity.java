package com.example.my_fiszki_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class home_activity extends AppCompatActivity {

    private ImageButton buttonSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonSetting = findViewById(R.id.buttonSetting);
        buttonSetting.setOnClickListener(v -> goBackToSetting());

        LinearLayout level1 = findViewById(R.id.level1);
        LinearLayout level2 = findViewById(R.id.level2);
        LinearLayout level3 = findViewById(R.id.level3);
        LinearLayout level4 = findViewById(R.id.level4);
        LinearLayout level5 = findViewById(R.id.level5);
        LinearLayout level6 = findViewById(R.id.level6);

        level1.setOnClickListener(v -> showLevelPopupBelow(v, 1));
        level2.setOnClickListener(v -> showLevelPopupBelow(v, 2));
        level3.setOnClickListener(v -> showLevelPopupBelow(v, 3));
        level4.setOnClickListener(v -> showLevelPopupBelow(v, 4));
        level5.setOnClickListener(v -> showLevelPopupBelow(v, 5));
        level6.setOnClickListener(v -> showLevelPopupBelow(v, 6));
    }

    private void goBackToSetting() {
        startActivity(new Intent(this, setting_activity.class));
        finish();
    }

    // Wyświetlanie popup pod kołem
    private void showLevelPopupBelow(View anchorView, int level) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_level, null);

        TextView levelText = popupView.findViewById(R.id.popupLevelText);
        levelText.setText("Poziom " + level);

        // PopupWindow
        final PopupWindow popup = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);

        //  Start w popup
        Button startButton = popupView.findViewById(R.id.popupStartButton);
        startButton.setOnClickListener(v -> {
            // Włączenie play_activity
            Intent intent = new Intent(home_activity.this, play_activity.class);
            intent.putExtra("level", level);  // Opcjonalnie numer poziomu
            startActivity(intent);

            popup.dismiss();
        });

        // Popup pod kołem
        popup.showAsDropDown(anchorView, 0, 16); // xOffset = 0, yOffset = 16px
    }
}
