package main.java.model;

public interface Player {
    void setGameBoard(int[][] gameBoard);

    void setPlayerPosition(int x, int y);

    void setOpponent(String playerId, String name, int x, int y);

    void setPlayerScore(int score);

    void setOpponentScore(String playerId, int score);

    void setGhostPosition(String ghostId, int x, int y);

    void gameStarted();

    void gameOver();

    String getName();
}
