package main.java.logic;

public abstract class Character {
    private int x;
    private int y;

    public void moveLeft(Boolean[][] gameBoard) {
        if (x > 0 && gameBoard[x - 1][y]) {
            setX(x - 1);
        }
    }

    public void moveRight(Boolean[][] gameBoard) {
        if (x < gameBoard.length - 2 && gameBoard[x + 1][y]) {
            setX(x + 1);
        }
    }

    public void moveUp(Boolean[][] gameBoard) {
        if (y > 0 && gameBoard[x][y - 1]) {
            setY(y - 1);
        }
    }

    public void moveDown(Boolean[][] gameBoard) {
        if (y < gameBoard[x].length && gameBoard[x][y + 1]) {
            setY(y + 1);
        }
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
