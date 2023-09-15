package main.java.logic;

import main.java.model.Game;
import main.java.model.Player;

public class GameImpl implements Game {
    //TODO: Implement Logic of the Game
    // Hier kann einfach mit den Playern gearbeitet werden,
    // die Player sollen einfach nur die Informationen bekommen
    @Override
    public void addPlayer(Player player) {
        System.out.println("Player added:" + player);
    }

    @Override
    public void removePlayer(Player player) {
        System.out.println("Player removed:" + player);
    }

    @Override
    public void movePlayerLeft(Player player) {
        System.out.println("Player moved left:" + player);
    }

    @Override
    public void movePlayerRight(Player player) {
        System.out.println("Player moved right:" + player);
    }

    @Override
    public void movePlayerUp(Player player) {
        System.out.println("Player moved up:" + player);
    }

    @Override
    public void movePlayerDown(Player player) {
        System.out.println("Player moved down:" + player);
    }

    @Override
    public void startGame() {
        System.out.println("Game started");
    }
}
