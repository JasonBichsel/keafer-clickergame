package ch.wiss.kfer_clickergame;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.List;

public class ScoreManager {

    private static final String PREFS_NAME = "GamePrefs";
    private static final String HIGH_SCORES_KEY = "HighScores";

    // Speichert komplette Highscore-Liste
    public static void saveHighScores(Context context, List<HighScore> highScores) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String serialized = HighScoreSerializer.serializeHighScores(highScores);
        prefs.edit().putString(HIGH_SCORES_KEY, serialized).apply();
    }

    // Lädt komplette Highscore-Liste
    public static List<HighScore> getHighScores(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String serialized = prefs.getString(HIGH_SCORES_KEY, "");
        return HighScoreSerializer.deserializeHighScores(serialized);
    }

    // Neuen Score hinzufügen, falls er in der Liste ist und ggf. sortieren / max Größe prüfen
    public static void addNewHighScore(Context context, HighScore newScore) {
        List<HighScore> highscores = getHighScores(context);
        highscores.add(newScore);

        // Sortieren absteigend nach Punkten
        highscores.sort((a, b) -> Integer.compare(b.getPoints(), a.getPoints()));

        // Maximal 10 Einträge behalten
        if (highscores.size() > 10) {
            highscores = highscores.subList(0, 10);
        }

        saveHighScores(context, highscores);
    }
}