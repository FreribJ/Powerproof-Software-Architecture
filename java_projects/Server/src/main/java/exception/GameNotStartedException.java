package main.java.exception;

public class GameNotStartedException extends Exception {
    public GameNotStartedException() {
        super("The game is not started yet.");
    }
}
