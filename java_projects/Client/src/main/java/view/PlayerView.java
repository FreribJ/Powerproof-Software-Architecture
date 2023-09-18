package main.java.view;

import main.java.model.Player;

import javax.swing.*;
import java.util.Arrays;

//TODO: Implement the UI here
// Die View bekommt die Daten vom Server und soll hier einfach nur die Daten anzeigen
public class PlayerView implements Player {

    private final String name;

    public PlayerView(String name) {
        this.name = name;
    }

    @Override
    public void setGameBoard(Boolean[][] gameBoard) {
        new PacMan();
        System.out.println("Drawing game board: " + Arrays.deepToString(gameBoard));
    }

    @Override
    public void setPlayerPosition(int x, int y) {
        System.out.println("Drawing player position: " + x + ", " + y);
    }

    @Override
    public void setOpponent(String playerId, String name, int x, int y) {
        System.out.println("Drawing opponent: " + playerId + ": " + name + ": " + x + ", " + y);
    }

    @Override
    public void setPlayerScore(int score) {
        System.out.println("Drawing player score: " + score);
    }

    @Override
    public void setOpponentScore(String playerId, int score) {
        System.out.println("Drawing opponent score: " + playerId + ": " + score);
    }

    @Override
    public void setGhostPosition(String ghostId, int x, int y) {
        System.out.println("Drawing ghost position: " + ghostId + ": " + x + ", " + y);
    }

    @Override
    public void gameStarted() {
        System.out.println("Game started");
    }

    @Override
    public String getName() {
        return name;
    }
}
