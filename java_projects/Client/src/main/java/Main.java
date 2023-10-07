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
import java.util.ArrayList;
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
        Socket socket = new Socket(ip, 10000);
        Game game = new GameClientProxy(socket);
        new PlayerView(name, game);
    }
}
