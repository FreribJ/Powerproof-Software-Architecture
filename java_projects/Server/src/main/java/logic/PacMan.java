package main.java.logic;

public class PacMan extends Character {
    private static int id = 0;
    private final int pacManId = id++;
    private int points = 0;

    public PacMan(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getPacManId() {
        return pacManId;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
}
