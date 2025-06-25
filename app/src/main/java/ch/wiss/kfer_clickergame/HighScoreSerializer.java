package ch.wiss.kfer_clickergame;

import java.util.ArrayList;
import java.util.List;

public class HighScoreSerializer {

    // Speichert Liste als String: "Name1,Punkte1;Name2,Punkte2;"
    public static String serializeHighScores(List<HighScore> highscores) {
        StringBuilder sb = new StringBuilder();
        for (HighScore hs : highscores) {
            sb.append(hs.getName()).append(",").append(hs.getPoints()).append(";");
        }
        return sb.toString();
    }

    // Wandelt String zurück in Liste
    public static List<HighScore> deserializeHighScores(String data) {
        List<HighScore> highscores = new ArrayList<>();
        if (data == null || data.isEmpty()) return highscores;

        String[] pairs = data.split(";");
        for (String pair : pairs) {
            if (pair.trim().isEmpty()) continue;
            String[] parts = pair.split(",");
            if (parts.length != 2) continue;
            String name = parts[0];
            int points;
            try {
                points = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                points = 0;
            }
            highscores.add(new HighScore(name, points));
        }
        return highscores;
    }
}