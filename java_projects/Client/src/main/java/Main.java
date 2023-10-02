package main.java;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.model.Game;
import main.java.model.Player;
import main.java.proxy.GameClientProxy;
import main.java.view.PlayerView;
import sas.View;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, PlayerAlreadyAddedException, PlayerNotInTheGameException, GameNotStartedException {
        System.out.println("Welcome to the game!");
        System.out.println("------------------------");
        System.out.println("Enter IP address of server:");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.next();
        System.out.println("------------------------");
        System.out.println("Enter your name:");
        View view = new View(900, 1100, "PacMan");
        view.setBackgroundColor(Color.BLACK);
        Player player = new PlayerView(scanner.next(), view);
        Socket socket = new Socket(ip, 10000);
        Game game = new GameClientProxy(socket);
        boolean running = true;
        while (running) {
            System.out.println("PROTOCOL: 1. addPlayer; 2. removePlayer; 3. movePlayerLeft; 4. movePlayerRight; 5. movePlayerUp; 6. movePlayerDown; 7. startGame; 8. endConnection");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    game.addPlayer(player);
                    break;
                case "2":
                    game.removePlayer(player);
                    break;
                case "3":
                    game.movePlayerLeft(player);
                    break;
                case "4":
                    game.movePlayerRight(player);
                    break;
                case "5":
                    game.movePlayerUp(player);
                    break;
                case "6":
                    game.movePlayerDown(player);
                    break;
                case "7":
                    game.startGame();
                    break;
                case "8":
                    game.endConnection();
                    running = false;
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }
}
