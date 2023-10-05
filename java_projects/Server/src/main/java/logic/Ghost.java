package main.java.logic;

import java.util.List;

public class Ghost extends Character {
    private static int id = 0;
    private final int ghostId = id++;

    private int lastDirection = 0;

    public Ghost(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getGhostId() {
        return ghostId;
    }

    public void move(int[][] gameBoard) {
        if ((getY() == 13 || getY() == 14) && getX() >= 11 && getX() <= 16) {
            moveDown(gameBoard);
        } else if (getY() == 15 && getX() >= 11 && getX() <= 12) {
            moveRight(gameBoard);
        } else if (getY() == 15 && getX() >= 15 && getX() <= 16) {
            moveLeft(gameBoard);
        } else if (getY() == 15 && getX() >= 13 && getX() <= 14) {
            moveDown(gameBoard);
        } else if (getY() == 16 && getX() >= 11 && getX() <= 16) {
            moveDown(gameBoard);
        } else {

            boolean[] possibleDirections = new boolean[4];
            possibleDirections[0] = getX() > 0 && gameBoard[getX()][getY() - 1] != 2;
            possibleDirections[1] = getY() < gameBoard[getX()].length - 1 && gameBoard[getX() + 1][getY()] != 2;
            possibleDirections[2] = getX() < gameBoard.length - 1 && gameBoard[getX()][getY() + 1] != 2;
            possibleDirections[3] = getY() > 0 && gameBoard[getX() - 1][getY()] != 2;


            int direction = (int) (Math.random() * 4);
            while (!possibleDirections[direction] || (Math.random() < 0.9 && (direction == (lastDirection + 2) % 4))) {
                direction = (int) (Math.random() * 4);
            }
            lastDirection = direction;
            switch (direction) {
                case 0:
                    moveUp(gameBoard);
                    break;
                case 1:
                    moveRight(gameBoard);
                    break;
                case 2:
                    moveDown(gameBoard);
                    break;
                case 3:
                    moveLeft(gameBoard);
                    break;
            }

        }
    }
}
