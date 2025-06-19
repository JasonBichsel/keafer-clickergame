package ch.wiss.kfer_clickergame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private TextView highscoreText;
    private static final String PREFS_NAME = "GamePrefs";
    private static final String HIGH_SCORE_KEY = "HighScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highscoreText = findViewById(R.id.highscoreText);
        Button startButton = findViewById(R.id.startGameButton);
        EditText nameInput = findViewById(R.id.nameInput);

        int highscore = ScoreManager.getHighScore(this);
        Log.d("muecke", "Highscore was loaded as "+highscore);
        highscoreText.setText("Highscore: " + highscore);

        startButton.setOnClickListener(view -> {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) name = "Anonym";
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("PLAYER_NAME", name);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateHighscoreDisplay();
    }

    private void updateHighscoreDisplay() {
        String name = ScoreManager.getHighScoreName(this);
        highscoreText.setText("Highscore: " + ScoreManager.getHighScore(this) + " von " + name);
    }
}
