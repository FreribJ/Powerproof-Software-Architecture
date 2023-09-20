package main.java.logic;

public abstract class Character {
    private int x;
    private int y;

    public boolean moveLeft(int[][] gameBoard) {
        if (x > 0 && gameBoard[x - 1][y] != 2) {
            setX(x - 1);
            return true;
        }
        return false;
    }

    public boolean moveRight(int[][] gameBoard) {
        if (x < gameBoard.length - 1 && gameBoard[x + 1][y] != 2) {
            setX(x + 1);
            return true;
        }
        return false;
    }

    public boolean moveUp(int[][] gameBoard) {
        if (y > 0 && gameBoard[x][y - 1] != 2) {
            setY(y - 1);
            return true;
        }
        return false;
    }

    public boolean moveDown(int[][] gameBoard) {
        if (y < gameBoard[x].length - 1 && gameBoard[x][y + 1] != 2) {
            setY(y + 1);
            return true;
        }
        return false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
