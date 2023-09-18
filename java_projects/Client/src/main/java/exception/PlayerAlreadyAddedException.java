package main.java.exception;

import main.java.model.Player;

public class PlayerAlreadyAddedException extends Exception {
    public PlayerAlreadyAddedException(Player player) {
        super("The player " + player.getName() + " is already added to the game.");
    }
}
