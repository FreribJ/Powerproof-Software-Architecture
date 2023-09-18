package main.java.exception;

import main.java.model.Player;

public class PlayerNotInTheGameException extends Exception {
    public PlayerNotInTheGameException(Player player) {
        super("The player " + player.getName() + " is not in the game.");
    }
}
