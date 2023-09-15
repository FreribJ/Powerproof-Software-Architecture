package main.java.model;

public interface Game {
    void addPlayer(Player player);
    void removePlayer(Player player);
    void movePlayerLeft(Player player);
    void movePlayerRight(Player player);
    void movePlayerUp(Player player);
    void movePlayerDown(Player player);
    void startGame();
}
