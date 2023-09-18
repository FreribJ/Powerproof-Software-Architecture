package main.java.logic;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;

import java.util.HashMap;
import java.util.Map;

public class GameImpl implements Game {
    //TODO: Implement Logic of the Game
    // Hier kann einfach mit den Playern gearbeitet werden,
    // die Player sollen einfach nur die Informationen bekommen
    private Map<Player, PacMan> players = new HashMap<>();
    private Boolean[][] gameBoard = {
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
        players.put(player, new PacMan(0, 0));
    }

    @Override
    public void removePlayer(Player player) throws PlayerNotInTheGameException {
        if (players.remove(player) == null) throw new PlayerNotInTheGameException(player);
    }

    @Override
    public void movePlayerLeft(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveLeft(gameBoard);
    }

    @Override
    public void movePlayerRight(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveRight(gameBoard);
    }

    @Override
    public void movePlayerUp(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveUp(gameBoard);
    }

    @Override
    public void movePlayerDown(Player player) throws PlayerNotInTheGameException, GameNotStartedException {
        if (!gameStarted) throw new GameNotStartedException();
        PacMan pacMan = players.get(player);
        if (pacMan == null) throw new PlayerNotInTheGameException(player);
        pacMan.moveDown(gameBoard);
    }

    @Override
    public void startGame() {
        gameStarted = true;
    }
}
