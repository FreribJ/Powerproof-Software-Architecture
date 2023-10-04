package main.java.view;

import main.java.model.Player;
import sas.Text;
import sas.View;

import java.awt.*;
import java.util.*;

public class PlayerView implements Player {
    private static final int pixelWidth = 20;
    private final String name;
    private View view;
    private Map<String, GhostView> ghosts = new HashMap<>();
    private PacmanView pacman;
    private Map<String, PacmanView> opponents = new HashMap<>();
    private GameBoardView gameBoard;
    private Text score;
    private Map<String, Text> opponentScores = new HashMap<>();
    private Map<String, String> opponentNames = new HashMap<>();

    public PlayerView(String name, View view) {
        this.view = view;
        Text header = new Text(350, 10, "PacMan");
        header.setFontColor(Color.WHITE);
        header.setFontMonospaced(true, 30);
        this.name = name;
    }

    @Override
    public void setGameBoard(int[][] gameBoard) {
        if (this.gameBoard == null) {
            this.gameBoard = new GameBoardView(gameBoard, view, pixelWidth);
        } else
            this.gameBoard.updateGameBoard(gameBoard);
    }

    @Override
    public void setPlayerPosition(int x, int y) {
        if (this.pacman == null) {
            this.pacman = new PacmanView(false, x, y, pixelWidth);
        } else {
            this.pacman.moveToCoordinate(x, y);
        }
    }

    @Override
    public void setOpponent(String playerId, String name, int x, int y) {
        PacmanView opponent = opponents.get(playerId);
        if (opponent == null) {
            opponent = new PacmanView(true, x, y, pixelWidth);
            opponents.put(playerId, opponent);
            opponentNames.put(playerId, name);
        } else {
            opponent.moveToCoordinate(x, y);
        }
        System.out.println("setOpponent: " + playerId + " " + name + " " + x + " " + y);
    }

    @Override
    public void setPlayerScore(int score) {
        if (this.score == null) {
            this.score = new Text(100, 100, "Score: " + score);
            this.score.setFontColor(Color.WHITE);
        } else {
            this.score.setText("Score: " + score);
        }
        System.out.println("setPlayerScore: " + score);
    }

    @Override
    public void setOpponentScore(String playerId, int score) {
        Text opponentScore = opponentScores.get(playerId);
        if (opponentScore == null) {
            opponentScore = new Text(100 + (150 * (opponentScores.size() + 1)), 100, opponentNames.get(playerId) + ": " + score);
            opponentScore.setFontColor(Color.WHITE);
            opponentScores.put(playerId, opponentScore);
        } else {
            opponentScore.setText(opponentNames.get(playerId) + ": " + score);
        }
        System.out.println("setOpponentScore: " + playerId + " " + score);
    }

    @Override
    public void setGhostPosition(String ghostId, int x, int y) {
        GhostView ghost = ghosts.get(ghostId);
        if (ghost == null) {
            ghost = new GhostView(x, y, pixelWidth);
            ghosts.put(ghostId, ghost);
        } else
            ghost.moveToCoordinate(x, y);
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
