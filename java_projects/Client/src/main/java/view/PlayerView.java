package main.java.view;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;
import sas.Rectangle;
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
    private Text score, pressToStart;
    private Map<String, Text> opponentScores = new HashMap<>();
    private Map<String, String> opponentNames = new HashMap<>();

    public PlayerView(String name, Game game) throws PlayerAlreadyAddedException, PlayerNotInTheGameException, GameNotStartedException {
        this.view = new View(800, 800, "PacMan");
        view.setBackgroundColor(Color.BLACK);
        char menuChoice = displayMenu();
        this.name = name;
        Text header = new Text(350, 10, "PacMan");
        header.setFontColor(Color.WHITE);
//        pressToStart = new Text(50,50, "Press '7' to start the game!");
//        pressToStart.setFontColor(Color.WHITE);
        header.setFontMonospaced(true, 30);
        boolean running = true;
        if (menuChoice == '1') {
            game.addPlayer(this);
        } else {
            game.endConnection();
            running = false;
        }

        while (running) {
            char choice = view.keyGetChar();
            switch (choice) {
                case '2' -> game.removePlayer(this);
                case 'a' -> game.movePlayerLeft(this);
                case 'd' -> game.movePlayerRight(this);
                case 'w' -> game.movePlayerUp(this);
                case 's' -> game.movePlayerDown(this);
                case '7' -> {
                    view.remove(pressToStart);
                    game.startGame();
                }
                case '8' -> {
                    game.endConnection();
                    running = false;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    private char displayMenu() {
        ArrayList<Text> menu = new ArrayList<Text>();
        menu.add(new Text(350, 10, "PacMan"));
        menu.add(new Text(100, 100, "Press Numbers to select:"));
        menu.add(new Text(100, 150, "1. Join Game"));
        menu.add(new Text(100, 200, "2. Leave Game"));
        menu.add(new Text(100, 700, "Controls: WASD"));
        menu.add(new Text(100, 750,"After joining, hit '7' to start the game"));
        for (Text item : menu) {
            item.setFontColor(Color.WHITE);
            item.setFontMonospaced(true, 30);
        }
        boolean inMenu = true;
        char ret = 0;
        while (inMenu) {
            char choice = view.keyGetChar();
            if (choice == '1' || choice == '2') {
                inMenu = false;
                ret = choice;
            } else {
                System.out.println("Menu: Wrong input");
            }
        }
        for (Text item : menu) {
            view.remove(item);
        }
        return ret;
    }


    @Override
    public void setGameBoard(int[][] gameBoard) {
        if (this.gameBoard == null) {
            this.gameBoard = new GameBoardView(gameBoard, view, pixelWidth);
        } else
            this.gameBoard.updateGameBoard(gameBoard);
    }

    @Override
    public void removeScorePoint(int x, int y) {
        this.gameBoard.removeScorePoint(x, y);
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
        System.out.println("Opponent: " + name + " " + x + " " + y);
    }

    @Override
    public void setPlayerScore(int score) {
        if (this.score == null) {
            this.score = new Text(100, 100, "Score: " + score);
            this.score.setFontColor(Color.WHITE);
        } else {
            this.score.setText("Score: " + score);
        }
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
        System.out.println("Opponent score: " + playerId + " " + score);
    }

    @Override
    public void setGhostPosition(String ghostId, int x, int y) {
        GhostView ghost = ghosts.get(ghostId);
        if (ghost == null) {
            ghost = new GhostView(x, y, pixelWidth, Integer.parseInt(ghostId));
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
        Rectangle endScreen = new Rectangle(0, 0, view.getWidth(), view.getHeight());
        endScreen.setColor(Color.BLACK);

        ArrayList<String> playerNames = new ArrayList<String>();
        ArrayList<String> playerScores = new ArrayList<String>();
        for (String key : opponentScores.keySet()) {
            playerNames.add(opponentNames.get(key));
            playerScores.add(opponentScores.get(key).getText());
        }
        playerNames.add(getName());
        playerScores.add(score.getText());

        ArrayList<Text> endText = new ArrayList<Text>();
        int playerCount = playerScores.size();
        for (int i = 0; i < playerCount; i++) {
            int nextHighestScore = 0;
            for (int j = 0; j < playerScores.size(); j++) {
                if (Integer.parseInt(playerScores.get(j).replaceAll("[^0-9]", "")) > Integer.parseInt(playerScores.get(nextHighestScore).replaceAll("[^0-9]", ""))) {
                    nextHighestScore = j;
                }
            }

            endText.add(new Text(100, 150 + i * 50, "" + (i + 1) + ". " + playerNames.get(nextHighestScore) + ": " + playerScores.get(nextHighestScore).replaceAll("[^0-9]", "") + " Pts."));
            playerNames.remove(nextHighestScore);
            playerScores.remove(nextHighestScore);
        }
        endText.add(new Text(350, 10, "Game Over!"));
        endText.add(new Text(100, 100, "Score Board:"));
        endText.add(new Text(100, 700, "Thank you for playing!"));
        for (Text item : endText) {
            item.setFontColor(Color.WHITE);
            item.setFontMonospaced(true, 30);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
