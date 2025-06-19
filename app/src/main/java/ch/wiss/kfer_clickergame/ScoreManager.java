package ch.wiss.kfer_clickergame;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreManager {
    private static final String PREFS_NAME = "GamePrefs";
    private static final String HIGH_SCORE_KEY = "HighScore";
    private static final String HIGH_SCORE_NAME_KEY = "HighScoreName";
    public static int getHighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(HIGH_SCORE_KEY, 0);
    }

    public static void saveHighScore(Context context, int score) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int oldScore = prefs.getInt(HIGH_SCORE_KEY, 0);
        if (score > oldScore) {
            prefs.edit().putInt(HIGH_SCORE_KEY, score).apply();
        }
    }
    public static String getHighScoreName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(HIGH_SCORE_NAME_KEY, "Unbekannt");
    }

    public static void saveHighScore(Context context, int score, String name) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int oldScore = prefs.getInt(HIGH_SCORE_KEY, 0);
        if (score > oldScore) {
            prefs.edit()
                    .putInt(HIGH_SCORE_KEY, score)
                    .putString(HIGH_SCORE_NAME_KEY, name)
                    .apply();
        }
    }
}
