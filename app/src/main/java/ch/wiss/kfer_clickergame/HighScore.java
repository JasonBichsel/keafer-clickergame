package ch.wiss.kfer_clickergame;

public class HighScore {
    private String name;
    private int points;

    public HighScore(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return name + ": " + points;
    }
}

