package main.java.logic;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;

import java.util.HashMap;
import java.util.Map;

public class GameImpl implements Game {
    private final Map<Player, PacMan> players = new HashMap<>();
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
    private boolean gameStarted = false;

    @Override
    public void addPlayer(Player player) throws PlayerAlreadyAddedException {
        if (players.get(player) != null) throw new PlayerAlreadyAddedException(player);
        PacMan pacMan = new PacMan(0, 0);
        players.put(player, pacMan);
        player.setGameBoard(gameBoard);
        players.forEach((player1, pacMan1) -> {
            if (player1 != player) {
                player.setOpponent(Integer.toString(pacMan1.getPacManId()), player1.getName(), pacMan1.getX(), pacMan1.getY());
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
    }

    @Override
    public void movePlayerRight(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveRight(gameBoard);
        informPlayersAboutPlayersPosition(player);
    }

    @Override
    public void movePlayerUp(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveUp(gameBoard);
        informPlayersAboutPlayersPosition(player);
    }

    @Override
    public void movePlayerDown(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveDown(gameBoard);
        informPlayersAboutPlayersPosition(player);
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
}
