package ch.wiss.kfer_clickergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView highscoreListView;
    private ArrayAdapter<String> adapter;
    private List<HighScore> highscoreList;

    private EditText nameInput;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views initialisieren
        highscoreListView = findViewById(R.id.highscoreListView);
        nameInput = findViewById(R.id.nameInput);
        startGameButton = findViewById(R.id.startGameButton);

        // Highscores laden und anzeigen
        loadHighScoresAndShow();

        // Start-Button Listener setzen
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = nameInput.getText().toString().trim();
                if (playerName.isEmpty()) {
                    playerName = "Anonym";
                }
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("PLAYER_NAME", playerName);
                startActivity(intent);
            }
        });
    }

    private void loadHighScoresAndShow() {
        highscoreList = ScoreManager.getHighScores(this);

        // Strings für Adapter erzeugen (Name: Punkte)
        List<String> displayList = new ArrayList<>();
        for (HighScore hs : highscoreList) {
            displayList.add(hs.getName() + ": " + hs.getPoints());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        highscoreListView.setAdapter(adapter); // Wichtig!

        // Long-Click zum Löschen
        highscoreListView.setOnItemLongClickListener((parent, view, position, id) -> {
            HighScore scoreToRemove = highscoreList.get(position);
            highscoreList.remove(position);
            ScoreManager.saveHighScores(this, highscoreList);
            loadHighScoresAndShow();
            Toast.makeText(this, "Gelöscht: " + scoreToRemove.getName(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHighScoresAndShow();
    }
}
