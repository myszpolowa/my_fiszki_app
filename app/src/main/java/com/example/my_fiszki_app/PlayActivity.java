package com.example.my_fiszki_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class PlayActivity extends AppCompatActivity {

    private ImageButton buttonBack;
    private Button btnLeft, btnRight;
    private TextView tvQuestion, tvLevel;
    private List<Flashcard> flashcards;
    private int currentIndex = 0;
    private SQLiteDatabase db;
    private int correctAnswers = 0;
    private int totalQuestions = 0;
    private int currentLevel = 1; // aktualny level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        buttonBack = findViewById(R.id.buttonBack);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvLevel = findViewById(R.id.tvLevel);

        currentLevel = getIntent().getIntExtra("LEVEL", 1); // wczytanie levela z home_activity
        tvLevel.setText("Level " + currentLevel);

        FiszkiDbHelper dbHelper = new FiszkiDbHelper(this);
        db = dbHelper.getReadableDatabase();

        loadFlashcards();
        showFlashcard();

        buttonBack.setOnClickListener(v -> goBackToHome());
        btnLeft.setOnClickListener(v -> pickOption(true));
        btnRight.setOnClickListener(v -> pickOption(false));
    }

    private void openLevelComplete() {
        // Zapis progresu: jeśli aktualny level > poprzedni progress
        int savedProgress = getSharedPreferences("FISZKI_PREFS", MODE_PRIVATE).getInt("PROGRESS", 0);
        if (currentLevel > savedProgress) {
            getSharedPreferences("FISZKI_PREFS", MODE_PRIVATE)
                    .edit()
                    .putInt("PROGRESS", currentLevel)
                    .apply();
        }

        Intent intent = new Intent(PlayActivity.this, LevelCompleteActivity.class);
        intent.putExtra("correct", correctAnswers);
        intent.putExtra("total", totalQuestions);
        intent.putExtra("LEVEL_COMPLETED", currentLevel);
        startActivity(intent);
        finish();
    }

    private void goBackToHome() {
        startActivity(new Intent(this, home_activity.class));
        finish();
    }

    private void loadFlashcards() {
        flashcards = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT question_id, question FROM questions WHERE level_id = ?",
                new String[]{String.valueOf(currentLevel)}
        );

        if (cursor.getCount() == 0) {
            Log.e("PlayActivity", "Brak pytań w bazie dla levela " + currentLevel);
        }

        int idxQid = cursor.getColumnIndex("question_id");
        int idxQuestion = cursor.getColumnIndex("question");

        while (cursor.moveToNext()) {
            if (idxQid == -1 || idxQuestion == -1) {
                Log.e("PlayActivity", "Brak kolumny question_id/question w tabeli questions!");
                continue;
            }

            int questionId = cursor.getInt(idxQid);
            String question = cursor.getString(idxQuestion);

            Cursor answerCursor = db.rawQuery(
                    "SELECT answer, is_good FROM answers WHERE question_id = ?",
                    new String[]{String.valueOf(questionId)}
            );

            int idxAnswer = answerCursor.getColumnIndex("answer");
            int idxIsGood = answerCursor.getColumnIndex("is_good");
            List<Answer> answers = new ArrayList<>();

            while (answerCursor.moveToNext()) {
                if (idxAnswer == -1 || idxIsGood == -1) continue;
                String answerText = answerCursor.getString(idxAnswer);
                int isGood = answerCursor.getInt(idxIsGood);
                answers.add(new Answer(answerText, isGood == 1));
            }
            answerCursor.close();

            flashcards.add(new Flashcard(question, answers));
        }

        cursor.close();
        totalQuestions = flashcards.size();
    }

    private void showFlashcard() {
        if (flashcards == null || flashcards.isEmpty()) {
            tvQuestion.setText("Brak fiszek w bazie dla levela " + currentLevel);
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
            return;
        }

        if (currentIndex < 0 || currentIndex >= flashcards.size()) currentIndex = 0;

        Flashcard fc = flashcards.get(currentIndex);
        tvQuestion.setText(fc.getQuestion());

        if (fc.getAnswers().size() >= 2) {
            btnLeft.setText(fc.getAnswers().get(0).getText());
            btnRight.setText(fc.getAnswers().get(1).getText());
            btnLeft.setEnabled(true);
            btnRight.setEnabled(true);
        } else {
            btnLeft.setText("Brak odp.");
            btnRight.setText("Brak odp.");
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
        }
    }

    private void pickOption(boolean isLeft) {
        if (flashcards == null || flashcards.isEmpty()) return;

        Flashcard fc = flashcards.get(currentIndex);
        int idx = isLeft ? 0 : 1;

        if (fc.getAnswers().size() > idx) {
            boolean isGood = fc.getAnswers().get(idx).isGood();
            if (isGood) {
                correctAnswers++;
                Toast.makeText(this, "Poprawna odpowiedź!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Zła odpowiedź!", Toast.LENGTH_SHORT).show();
            }
        }

        currentIndex++;
        if (currentIndex >= flashcards.size()) {
            openLevelComplete();
        } else {
            showFlashcard();
        }
    }

    private static class Flashcard {
        private final String question;
        private final List<Answer> answers;
        public Flashcard(String question, List<Answer> answers) {
            this.question = question;
            this.answers = answers;
        }
        public String getQuestion() { return question; }
        public List<Answer> getAnswers() { return answers; }
    }

    private static class Answer {
        private final String text;
        private final boolean isGood;
        public Answer(String text, boolean isGood) {
            this.text = text;
            this.isGood = isGood;
        }
        public String getText() { return text; }
        public boolean isGood() { return isGood; }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
