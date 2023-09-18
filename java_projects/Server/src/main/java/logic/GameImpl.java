package main.java.logic;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameImpl implements Game {
    private final Map<Player, PacMan> players = new HashMap<>();

    //Spielboard muss man sich gedreht vorstellen
    private final Boolean[][] gameBoard = {
            {true, true, true, true, true, true, true, true, true, true, true},
            {true, false, false, false, false, true, false, false, false, false, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, true, true, true, false, false, false, true, true, true, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, false, false, false, false, true, false, false, false, false, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, true, true, true, false, false, false, true, true, true, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, false, false, false, false, true, false, false, false, false, true},
            {true, true, true, true, true, true, true, true, true, true, true}};

    private final Boolean[][] points = {
            {false, true, true, true, true, true, true, true, true, true, true},
            {true, false, false, false, false, true, false, false, false, false, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, true, true, true, false, false, false, true, true, true, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, false, false, false, false, true, false, false, false, false, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, true, true, true, false, false, false, true, true, true, true},
            {true, false, true, true, true, true, true, true, true, false, true},
            {true, false, false, false, false, true, false, false, false, false, true},
            {true, true, true, true, true, true, true, true, true, true, true}};
    private boolean gameStarted = false;

    @Override
    public void addPlayer(Player player) throws PlayerAlreadyAddedException {
        //Players und PacMans sind unterschiedliche Objekte, die aber miteinander verknüpft sind
        //Player ist die Schnittstelle für den Client, PacMan ist das Objekt, das die Logik enthält
        if (players.get(player) != null) throw new PlayerAlreadyAddedException(player);
        PacMan pacMan = new PacMan(0, 0);
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
        if (points[pacMan.getX()][pacMan.getY()]) {
            pacMan.addPoints(1);
            points[pacMan.getX()][pacMan.getY()] = false;
            player.setPlayerScore(pacMan.getPoints());
            players.forEach((player1, pacMan1) -> {
                if (player1 != player) {
                    player1.setOpponentScore(Integer.toString(pacMan.getPacManId()), pacMan.getPoints());
                }
            });
        }
        if (Arrays.stream(points).allMatch(booleans -> Arrays.stream(booleans).noneMatch(aBoolean -> aBoolean))) {
            players.keySet().forEach(Player::gameOver);
            gameStarted = false;
        }
    }
}
