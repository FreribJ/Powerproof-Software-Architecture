package main.java;

import main.java.logic.GameImpl;
import main.java.model.Game;
import main.java.proxy.GameServerProxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10000);
        Game game = new GameImpl();
        while (true) {
            Socket socket = serverSocket.accept();
            GameServerProxy gameServerProxy = new GameServerProxy(socket, game);
            new Thread(gameServerProxy).start();
        }
    }
}
