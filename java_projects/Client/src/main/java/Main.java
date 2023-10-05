package main.java;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;
import main.java.proxy.GameClientProxy;
import main.java.view.PlayerView;
import sas.*;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, PlayerAlreadyAddedException, PlayerNotInTheGameException, GameNotStartedException {
        String ip;
        String name;
        if (args.length >= 2) {
            ip = args[0];
            name = args[1];
        } else {
            System.out.println("Welcome to the game!");
            System.out.println("------------------------");
            System.out.println("Enter IP address of server:");
            Scanner scanner = new Scanner(System.in);
            ip = scanner.next();
            System.out.println("------------------------");
            System.out.println("Enter your name:");
            name = scanner.next();
            scanner.close();
        }
        View view = new View(800, 800, "PacMan");
        view.setBackgroundColor(Color.BLACK);
        Player player = new PlayerView(name, view);
        Socket socket = new Socket(ip, 10000);
        Game game = new GameClientProxy(socket);
        game.addPlayer(player);
        boolean running = true;
        while (running) {
            System.out.println("PROTOCOL: 1. addPlayer; 2. removePlayer; 3. movePlayerLeft; 4. movePlayerRight; 5. movePlayerUp; 6. movePlayerDown; 7. startGame; 8. endConnection");
                char choice = view.keyGetChar();
            switch (choice) {
                case '2' -> game.removePlayer(player);
                case 'a' -> game.movePlayerLeft(player);
                case 'd' -> game.movePlayerRight(player);
                case 'w' -> game.movePlayerUp(player);
                case 's' -> game.movePlayerDown(player);
                case '7' -> game.startGame();
                case '8' -> {
                    game.endConnection();
                    running = false;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }
}
