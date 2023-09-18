package main.java.logic;

public class Ghost extends Character{
    private static int id = 0;
    private final int ghostId = id++;
    public Ghost(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getGhostId() {
        return ghostId;
    }
}
