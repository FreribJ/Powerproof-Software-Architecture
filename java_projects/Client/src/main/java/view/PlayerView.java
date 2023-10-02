package main.java.view;

import main.java.model.Player;
import sas.Text;
import sas.View;

import java.awt.*;
import java.util.*;

//TODO: Implement the UI here
// Die View bekommt die Daten vom Server und soll hier einfach nur die Daten anzeigen
public class PlayerView implements Player {
    private final String name;
    private View view;
    Map<String, GhostView> ghosts = new HashMap<>();
    PacmanView pacman;
    Map<String, PacmanView> opponents = new HashMap<>();
    private GameBoardView gameBoard;

    public PlayerView(String name, View view) {
        this.view = view;
        Text header = new Text(350, 10, "PacMan");
        header.setFontColor(Color.WHITE);
        header.setFontMonospaced(true, 30);
        this.name = name;
    }

    @Override
    public void setGameBoard(int[][] gameBoard) {
        if(this.gameBoard == null) {
            this.gameBoard = new GameBoardView(gameBoard, view);
        }
        else
            this.gameBoard.updateGameBoard(gameBoard);
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
        GhostView ghost = ghosts.get(ghostId);
        if(ghost == null) {
            ghost = new GhostView(x, y);
            ghosts.put(ghostId, ghost);
        } else
            ghost.moveToCoordinate(x, y);
        System.out.println("Drawing ghost position: " + ghostId + ": " + x + ", " + y);
    }

    @Override
    public void gameStarted() {
        System.out.println("Game started");
    }

    @Override
    public void gameOver() {
        System.out.println("Game over");
    }

    @Override
    public String getName() {
        return name;
    }
}
