package ch.wiss.kfer_clickergame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView scoreText, roundText, timeText, objectCountText;
    private FrameLayout gameArea;
    private int score = 0, round = 1, remainingHits = 5;
    private CountDownTimer roundTimer;
    private Handler handler = new Handler();
    private Random random = new Random();

    private final int ROUND_TIME_MS = 60000;
    private final int KAEFER_VISIBLE_MS = 2000;
    private boolean roundRunning = false;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreText = findViewById(R.id.scoreText);
        roundText = findViewById(R.id.roundText);
        timeText = findViewById(R.id.timeText);
        objectCountText = findViewById(R.id.objectCountText);
        gameArea = findViewById(R.id.gameArea);
        playerName = getIntent().getStringExtra("PLAYER_NAME");
        if (playerName == null || playerName.isEmpty()) playerName = "Anonym";
        startNewRound();
    }

    private void startNewRound() {
        roundRunning = true;
        remainingHits = 5 * round;
        updateUI();
        startRoundTimer();
        handler.post(this::spawnNextKafer);
    }

    private void startRoundTimer() {
        if (roundTimer != null) roundTimer.cancel();

        roundTimer = new CountDownTimer(ROUND_TIME_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Zeit: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                if (remainingHits > 0) {
                    gameOver();
                }
            }
        }.start();
    }

    private void spawnNextKafer() {
        if (!roundRunning) return;

        gameArea.removeAllViews();

        final TextView kafer = new TextView(this);
        kafer.setText("🐞");
        kafer.setTextSize(20);
        kafer.setGravity(Gravity.CENTER);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(150, 150);
        params.leftMargin = random.nextInt(Math.max(gameArea.getWidth() - 150, 1));
        params.topMargin = random.nextInt(Math.max(gameArea.getHeight() - 150, 1));
        kafer.setLayoutParams(params);

        kafer.setOnClickListener(v -> {
            gameArea.removeView(kafer);
            score += 100;
            remainingHits--;
            updateUI();

            if (remainingHits == 0) {
                roundSuccess();
            } else {
                handler.postDelayed(this::spawnNextKafer, 300);
            }
        });

        gameArea.addView(kafer);

        // Käfer verschwindet nach 2 Sekunden (wenn nicht getroffen)
        handler.postDelayed(() -> {
            if (gameArea.indexOfChild(kafer) != -1) {
                gameArea.removeView(kafer);
                handler.postDelayed(this::spawnNextKafer, 300); // nächster Versuch
            }
        }, KAEFER_VISIBLE_MS);
    }

    private void roundSuccess() {
        roundRunning = false;
        roundTimer.cancel();
        ScoreManager.saveHighScore(this, score, playerName);
        Toast.makeText(this, "Runde geschafft!", Toast.LENGTH_SHORT).show();
        round++;
        handler.postDelayed(this::startNewRound, 2000);
    }

    private void updateUI() {
        scoreText.setText("Punkte: " + score);
        roundText.setText("Runde: " + round);
        objectCountText.setText("Verbleibende: " + remainingHits);
    }

    private void gameOver() {
        roundRunning = false;
        handler.removeCallbacksAndMessages(null);
        roundTimer.cancel();
        ScoreManager.saveHighScore(this, score, playerName);
        Toast.makeText(this, "Game Over! Punkte: " + score, Toast.LENGTH_LONG).show();
        finish();
    }
}
