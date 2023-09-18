package main.java.proxy;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.helper.RPCReader;
import main.java.helper.RPCWriter;
import main.java.model.Game;
import main.java.model.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GameServerProxy implements Runnable {

    private final Socket socket;
    private final Game game;
    private final RPCReader rpcReader;
    private final RPCWriter rpcWriter;
    private final Map<String, Player> players = new HashMap<>();
    private boolean running = true;

    public GameServerProxy(Socket socket, Game game) throws IOException {
        this.socket = socket;
        this.game = game;
        rpcReader = new RPCReader(new InputStreamReader(socket.getInputStream()));
        rpcWriter = new RPCWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        while (running) {
            rpcWriter.println("PROTOCOL: 1. addPlayer; 2. removePlayer; 3. movePlayerLeft; 4. movePlayerRight; 5. movePlayerUp; 6. movePlayerDown; 7. startGame; 8. endConnection");
            try {
                String input = rpcReader.readLine();
                switch (input) {
                    case "1" -> addPlayer();
                    case "2" -> removePlayer();
                    case "3" -> movePlayerLeft();
                    case "4" -> movePlayerRight();
                    case "5" -> movePlayerUp();
                    case "6" -> movePlayerDown();
                    case "7" -> startGame();
                    case "8" -> endConnection();
                    default -> rpcWriter.println("PROTOCOL ERROR: " + input);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addPlayer() throws IOException {
        try {
            game.addPlayer(getPlayer());
            rpcWriter.println("0: Okay");
        } catch (PlayerAlreadyAddedException e) {
            rpcWriter.println("1: " + e.getMessage());
        }
    }

    private void removePlayer() throws IOException {
        try {
            game.removePlayer(getPlayer());
            rpcWriter.println("0: Okay");
        } catch (PlayerNotInTheGameException e) {
            rpcWriter.println("1: " + e.getMessage());
        }
    }

    private void movePlayerLeft() throws IOException {
        try {
            game.movePlayerLeft(getPlayer());
            rpcWriter.println("0: Okay");
        } catch (PlayerNotInTheGameException e) {
            rpcWriter.println("1: " + e.getMessage());
        } catch (GameNotStartedException e) {
            rpcWriter.println("2: " + e.getMessage());
        }
    }

    private void movePlayerRight() throws IOException {
        try {
            game.movePlayerRight(getPlayer());
            rpcWriter.println("0: Okay");
        } catch (PlayerNotInTheGameException e) {
            rpcWriter.println("1: " + e.getMessage());
        } catch (GameNotStartedException e) {
            rpcWriter.println("2: " + e.getMessage());
        }
    }

    private void movePlayerUp() throws IOException {
        try {
            game.movePlayerUp(getPlayer());
            rpcWriter.println("0: Okay");
        } catch (PlayerNotInTheGameException e) {
            rpcWriter.println("1: " + e.getMessage());
        } catch (GameNotStartedException e) {
            rpcWriter.println("2: " + e.getMessage());
        }
    }

    private void movePlayerDown() throws IOException {
        try {
            game.movePlayerDown(getPlayer());
            rpcWriter.println("0: Okay");
        } catch (PlayerNotInTheGameException e) {
            rpcWriter.println("1: " + e.getMessage());
        } catch (GameNotStartedException e) {
            rpcWriter.println("2: " + e.getMessage());
        }
    }

    private void startGame() throws IOException {
        rpcWriter.println("0: Okay");
        game.startGame();
    }

    private void endConnection() throws IOException {
        socket.close();
        players.values().forEach(player -> {
            try {
                game.removePlayer(player);
            } catch (PlayerNotInTheGameException e) {
                throw new RuntimeException(e);
            }
            player.endConnection();
        });
        running = false;
    }

    private Player getPlayer() throws IOException {
        rpcWriter.println("REQUEST: playerID");
        String playerID = rpcReader.readLine();
        Player player = players.get(playerID);
        if (player == null) {
            rpcWriter.println("REQUEST: ip");
            String ip = rpcReader.readLine();
            rpcWriter.println("REQUEST: port");
            String port = rpcReader.readLine();
            rpcWriter.println("REQUEST: name");
            String name = rpcReader.readLine();
            player = new PlayerClientProxy(new Socket(ip, Integer.parseInt(port)), name);
            players.put(playerID, player);
        }
        return player;
    }
}
