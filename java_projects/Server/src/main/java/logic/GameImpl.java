package main.java.logic;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameImpl implements Game {
    private final Map<Player, PacMan> players = new ConcurrentHashMap<>();
    private final Map<Player, String> directions = new ConcurrentHashMap<>();
    private final List<Ghost> ghosts = new ArrayList<>();

    //2: Wand; 1: Maze-Block; 0: Leeres Feld
    //Spielboard muss man sich gedreht vorstellen
    int[][] gameBoard = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0, 0, 0, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 0, 0, 0, 2, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 2},
            {2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 0, 0, 0, 2, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0, 0, 0, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    };
    private boolean gameStarted = false;

    @Override
    public void addPlayer(Player player) throws PlayerAlreadyAddedException {
        //Players und PacMans sind unterschiedliche Objekte, die aber miteinander verknüpft sind
        //Player ist die Schnittstelle für den Client, PacMan ist das Objekt, das die Logik enthält
        if (players.get(player) != null) throw new PlayerAlreadyAddedException(player);
        PacMan pacMan = new PacMan(1, 1);
        players.put(player, pacMan);
        player.setGameBoard(gameBoard);
        player.setPlayerScore(pacMan.getPoints());
        players.forEach((player1, pacMan1) -> {
            if (player1 != player) {
                player.setOpponent(Integer.toString(pacMan1.getPacManId()), player1.getName(), pacMan1.getX(), pacMan1.getY());
                player.setOpponentScore(Integer.toString(pacMan1.getPacManId()), pacMan1.getPoints());
                player1.setOpponent(Integer.toString(pacMan.getPacManId()), player.getName(), pacMan.getX(), pacMan.getY());
                player1.setOpponentScore(Integer.toString(pacMan.getPacManId()), pacMan.getPoints());
            }
        });
        informPlayersAboutPlayersPosition(player);
    }

    @Override
    public void removePlayer(Player player) throws PlayerNotInTheGameException {
        if (players.remove(player) == null) throw new PlayerNotInTheGameException(player);
        //TODO: Inform other players about the player leaving
    }

    @Override
    public void movePlayerLeft(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        directions.put(player, "left");
    }

    @Override
    public void movePlayerRight(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        directions.put(player, "right");
    }

    @Override
    public void movePlayerUp(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        directions.put(player, "up");
    }

    @Override
    public void movePlayerDown(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        directions.put(player, "down");
    }

    @Override
    public void startGame() {
        if (!gameStarted) {
            gameStarted = true;
            players.keySet().forEach(Player::gameStarted);
            for (int i = 0; i < 4; i++) {
                int x = (int) (Math.random() * 6 + 11);
                int y = (int) (Math.random() * 3 + 13);
                Ghost ghost = new Ghost(x, y);
                ghosts.add(ghost);
            }
            new Thread(this::move).start();
        }
    }

    private void informPlayersAboutPlayersPosition(Player player) {
        PacMan pacMan = players.get(player);
        player.setPlayerPosition(pacMan.getX(), pacMan.getY());
        players.forEach((player1, pacMan1) -> {
            if (player1 != player) {
                player1.setOpponent(Integer.toString(pacMan.getPacManId()), player.getName(), pacMan.getX(), pacMan.getY());
            }
        });
    }

    private void checkPoints(Player player) {
        PacMan pacMan = players.get(player);
        if (gameBoard[pacMan.getX()][pacMan.getY()] == 1) {
            pacMan.addPoints(1);
            gameBoard[pacMan.getX()][pacMan.getY()] = 0;
            player.setPlayerScore(pacMan.getPoints());
            players.forEach((player1, pacMan1) -> {
                player1.removeScorePoint(pacMan.getX(), pacMan.getY());
                if (player1 != player) {
                    player1.setOpponentScore(Integer.toString(pacMan.getPacManId()), pacMan.getPoints());
                }
            });
        }
        if (Arrays.stream(gameBoard).allMatch(row -> Arrays.stream(row).noneMatch(field -> field == 1))) {
            players.keySet().forEach(Player::gameOver);
            gameStarted = false;
        }
    }

    private void move() {
        while (gameStarted) {
            LocalTime start = LocalTime.now();
            ghosts.forEach(g -> {
                g.move(gameBoard);
                players.forEach((player, pacMan) -> {
                    player.setGhostPosition(Integer.toString(g.getGhostId()), g.getX(), g.getY());
                    checkForCollision(g, player, pacMan);
                });
            });
            players.forEach((player, pacMan) -> {
                String direction = directions.get(player);
                if (direction != null) {
                    boolean moved = false;
                    switch (direction) {
                        case "left" -> moved = pacMan.moveLeft(gameBoard);
                        case "right" -> moved = pacMan.moveRight(gameBoard);
                        case "up" -> moved = pacMan.moveUp(gameBoard);
                        case "down" -> moved = pacMan.moveDown(gameBoard);
                    }
                    if (moved) {
                        informPlayersAboutPlayersPosition(player);
                        checkPoints(player);
                    }
                }
            });
            ghosts.forEach(g -> players.forEach((player, pacMan) -> checkForCollision(g, player, pacMan)));
            while (LocalTime.now().isBefore(start.plusNanos(500000000))) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkForCollision(Ghost g, Player player, PacMan pacMan) {
        if (pacMan.getX() == g.getX() && pacMan.getY() == g.getY()) {
            pacMan.setX(1);
            pacMan.setY(1);
            player.setPlayerPosition(1, 1);
            pacMan.addPoints(-10);
            player.setPlayerScore(pacMan.getPoints());
            players.forEach((player1, pacMan1) -> {
                if (player1 != player) {
                    player1.setOpponentScore(Integer.toString(pacMan.getPacManId()), pacMan.getPoints());
                }
            });
        }
    }
}
