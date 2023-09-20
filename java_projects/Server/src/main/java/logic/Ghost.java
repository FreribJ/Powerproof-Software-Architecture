package main.java.logic;

import java.util.List;

public class Ghost extends Character {
    private static int id = 0;
    private final int ghostId = id++;

    public Ghost(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getGhostId() {
        return ghostId;
    }

    public void move(int[][] gameBoard, List<PacMan> pacMans) {
        PacMan nearestPacMan = null;
        int nearestDistance = Integer.MAX_VALUE;
        for (PacMan pacMan : pacMans) {
            int distance = Math.abs(getX() - pacMan.getX()) + Math.abs(getY() - pacMan.getY());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPacMan = pacMan;
            }
        }
        if (getX() < nearestPacMan.getX()) {
            moveRight(gameBoard);
        } else if (getX() > nearestPacMan.getX()) {
            moveLeft(gameBoard);
        } else if (getY() < nearestPacMan.getY()) {
            moveDown(gameBoard);
        } else if (getY() > nearestPacMan.getY()) {
            moveUp(gameBoard);
        }
    }
}
