package main.java.model;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;

public interface Game {
    void addPlayer(Player player) throws PlayerAlreadyAddedException;

    void removePlayer(Player player) throws PlayerNotInTheGameException;

    void movePlayerLeft(Player player) throws PlayerNotInTheGameException, GameNotStartedException;

    void movePlayerRight(Player player) throws PlayerNotInTheGameException, GameNotStartedException;

    void movePlayerUp(Player player) throws PlayerNotInTheGameException, GameNotStartedException;

    void movePlayerDown(Player player) throws PlayerNotInTheGameException, GameNotStartedException;

    void startGame();

    // Only in the GameClient
    void endConnection();
}
