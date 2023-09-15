package main.java.model;

public interface Player {
    void setGameBoard(Boolean[][] gameBoard);

    void setPlayerPosition(int x, int y);

    void setOpponentPosition(String playerId, int x, int y);

    void setOpponentName(String playerId, String name);

    void setPlayerScore(int score);

    void setOpponentScore(String playerId, int score);

    void setGhostPosition(String ghostId, int x, int y);

    String getName();

    // Only in the PlayerClient
    void endConnection();
}
