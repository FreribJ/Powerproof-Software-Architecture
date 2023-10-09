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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public PlayerView(String name, Game game) throws PlayerAlreadyAddedException, PlayerNotInTheGameException, GameNotStartedException {
        this.view = new View(800, 800, "PacMan");
        view.setBackgroundColor(Color.BLACK);
        char menuChoice = displayMenu();
        this.name = name;
        Text header = new Text(350, 10, "PacMan");
        header.setFontColor(Color.WHITE);
        header.setFontMonospaced(true, 30);
        //Button exit = new Button(700, 10, 70, 40, "Exit", Color.RED);
        Text tipps = new Text(330, 750, "WASD to move; press e to exit");
        tipps.setFontColor(Color.WHITE);
        tipps.setFontMonospaced(true, 20);
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
                case '7' -> game.startGame();
                case 'p' -> this.gameOver(); //testing purposes
                case 'e' -> game.endConnection();
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
            this.score = new Text(100, 70, "Score: " + score);
            this.score.setFontColor(Color.WHITE);
            this.score.setFontMonospaced(true, 24);
        } else {
            this.score.setText("Score: " + score);
        }
    }

    @Override
    public void setOpponentScore(String playerId, int score) {
        Text opponentScore = opponentScores.get(playerId);
        if (opponentScore == null) {
            opponentScore = new Text(100 + (150 * (opponentScores.size() + 1)), 70, opponentNames.get(playerId) + ": " + score);
            opponentScore.setFontColor(Color.WHITE);
            opponentScore.setFontMonospaced(true, 24);
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
            int nextHighestScore = Integer.parseInt(playerScores.get(0).substring(playerScores.get(0).lastIndexOf(':')).replaceAll("[^0-9-]", ""));
            int indexOfNextHighestScore = 0;
            for (int j = 0; j < playerScores.size(); j++) {
                int currentScore = Integer.parseInt(playerScores.get(j).substring(playerScores.get(j).lastIndexOf(':')).replaceAll("[^0-9-]", ""));
                //nextHighestScore = Integer.parseInt(playerScores.get(indexOfNextHighestScore).substring(playerScores.get(indexOfNextHighestScore).lastIndexOf(':')).replaceAll("[^0-9-]", ""));
                if (currentScore > nextHighestScore) {
                    indexOfNextHighestScore = j;
                    nextHighestScore = currentScore;

                }
            }

            endText.add(new Text(100, 150 + i * 50, "" + (i + 1) + ". " + playerNames.get(indexOfNextHighestScore) + ": " + nextHighestScore + " Pts."));
            playerNames.remove(indexOfNextHighestScore);
            playerScores.remove(indexOfNextHighestScore);
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
