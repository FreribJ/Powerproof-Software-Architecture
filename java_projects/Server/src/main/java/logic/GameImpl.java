package main.java.logic;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;

import java.util.*;

public class GameImpl implements Game {
    private final Map<Player, PacMan> players = new HashMap<>();
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
            {2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 0, 0, 0, 2, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 2},
            {2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 0, 0, 0, 2, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 2},
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
                player1.setOpponentScore(Integer.toString(pacMan.getPacManId()), pacMan.getPoints());
                player.setOpponentScore(Integer.toString(pacMan1.getPacManId()), pacMan1.getPoints());
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
        pacMan.moveLeft(gameBoard);
        informPlayersAboutPlayersPosition(player);
        checkPoints(player);
    }

    @Override
    public void movePlayerRight(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveRight(gameBoard);
        informPlayersAboutPlayersPosition(player);
        checkPoints(player);
    }

    @Override
    public void movePlayerUp(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveUp(gameBoard);
        informPlayersAboutPlayersPosition(player);
        checkPoints(player);
    }

    @Override
    public void movePlayerDown(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveDown(gameBoard);
        informPlayersAboutPlayersPosition(player);
        checkPoints(player);
    }

    @Override
    public void startGame() {
        players.keySet().forEach(Player::gameStarted);
        gameStarted = true;
        Ghost ghost = new Ghost(15, 15);
        ghosts.add(ghost);
        new Thread(this::moveGhosts).start();
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
                player1.setGameBoard(gameBoard);
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

    private void moveGhosts() {
        while (gameStarted) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ghosts.forEach(g -> {
                if(g.move(gameBoard, players.values().stream().toList())) {
                    players.forEach((player, pacMan) -> {
                        player.setGhostPosition(Integer.toString(g.getGhostId()), g.getX(), g.getY());

                        //Currently only check whether a ghost hit you
                        if (pacMan.getX() == g.getX() && pacMan.getY() == g.getY()) {
                            player.setPlayerPosition(0, 0);
                        }
                    });
                }
            });
        }
    }
}
