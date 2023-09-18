package main.java.proxy;

import main.java.exception.GameNotStartedException;
import main.java.exception.PlayerAlreadyAddedException;
import main.java.exception.PlayerNotInTheGameException;
import main.java.helper.RPCReader;
import main.java.helper.RPCWriter;
import main.java.model.Game;
import main.java.model.Player;
import main.java.helper.Utility;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GameClientProxy implements Game {

    private Socket socket;
    private RPCWriter rpcWriter;
    private RPCReader rpcReader;
    private final Map<Player, String> players = new HashMap<>();
    private int maxId = 0;

    public GameClientProxy(Socket socket) {
        this.socket = socket;
        try {
            this.rpcWriter = new RPCWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.rpcReader = new RPCReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPlayer(Player player) throws PlayerAlreadyAddedException {
        try {
            rpcReader.readLine();
            rpcWriter.println("1");
            sendPlayer(player);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            if (returnCode.startsWith("1"))
                throw new PlayerAlreadyAddedException(player);
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePlayer(Player player) throws PlayerNotInTheGameException {
        try {
            rpcReader.readLine();
            rpcWriter.println("2");
            sendPlayer(player);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            if (returnCode.startsWith("1"))
                throw new PlayerNotInTheGameException(player);
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void movePlayerLeft(Player player) throws GameNotStartedException, PlayerNotInTheGameException {
        try {
            rpcReader.readLine();
            rpcWriter.println("3");
            sendPlayer(player);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            if (returnCode.startsWith("1"))
                throw new PlayerNotInTheGameException(player);
            if (returnCode.startsWith("2"))
                throw new GameNotStartedException();
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void movePlayerRight(Player player) throws GameNotStartedException, PlayerNotInTheGameException {
        try {
            rpcReader.readLine();
            rpcWriter.println("4");
            sendPlayer(player);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            if (returnCode.startsWith("1"))
                throw new PlayerNotInTheGameException(player);
            if (returnCode.startsWith("2"))
                throw new GameNotStartedException();
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void movePlayerUp(Player player) throws GameNotStartedException, PlayerNotInTheGameException {
        try {
            rpcReader.readLine();
            rpcWriter.println("5");
            sendPlayer(player);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            if (returnCode.startsWith("1"))
                throw new PlayerNotInTheGameException(player);
            if (returnCode.startsWith("2"))
                throw new GameNotStartedException();
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void movePlayerDown(Player player) throws GameNotStartedException, PlayerNotInTheGameException {
        try {
            rpcReader.readLine();
            rpcWriter.println("6");
            sendPlayer(player);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            if (returnCode.startsWith("1"))
                throw new PlayerNotInTheGameException(player);
            if (returnCode.startsWith("2"))
                throw new GameNotStartedException();
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startGame() {
        try {
            rpcReader.readLine();
            rpcWriter.println("7");
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            throw new RuntimeException(returnCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endConnection() {
        try {
            rpcReader.readLine();
            rpcWriter.println("8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendPlayer(Player player) {
        try {
            String playerId = players.get(player);
            if (playerId == null) {
                maxId++;
                players.put(player, Integer.toString(maxId));
                rpcReader.readLine();
                rpcWriter.println(maxId);
                rpcReader.readLine();
                rpcWriter.println(Utility.getLocalIpAddress());
                rpcReader.readLine();
                ServerSocket serverSocket = new ServerSocket(0);
                rpcWriter.println(serverSocket.getLocalPort());
                rpcReader.readLine();
                rpcWriter.println(player.getName());
                Socket socket = serverSocket.accept();
                PlayerServerProxy playerServerProxy = new PlayerServerProxy(socket, player);
                new Thread(playerServerProxy).start();
            } else {
                rpcReader.readLine();
                rpcWriter.println(playerId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
