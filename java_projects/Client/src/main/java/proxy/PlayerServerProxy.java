package main.java.proxy;

import main.java.helper.RPCReader;
import main.java.helper.RPCWriter;
import main.java.model.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class PlayerServerProxy implements Runnable {

    private final Socket socket;
    private final Player player;
    private final RPCReader rpcReader;
    private final RPCWriter rpcWriter;
    private boolean running = true;

    public PlayerServerProxy(Socket socket, Player player) throws IOException {
        this.socket = socket;
        this.player = player;
        rpcReader = new RPCReader(new InputStreamReader(socket.getInputStream()));
        rpcWriter = new RPCWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        while (running) {
            rpcWriter.println("PROTOCOL: 1. setGameBoard; 2. setPlayerPosition; 3. setOpponentPosition; 4. setOpponentName; 5. setPlayerScore; 6. setOpponentScore; 7. setGhostPosition; 8. endConnection");
            try {
                String input = rpcReader.readLine();
                switch (input) {
                    case "1" -> setGameBoard();
                    case "2" -> setPlayerPosition();
                    case "3" -> setOpponentPosition();
                    case "4" -> setOpponentName();
                    case "5" -> setPlayerScore();
                    case "6" -> setOpponentScore();
                    case "7" -> setGhostPosition();
                    case "8" -> endConnection();
                    default -> rpcWriter.println("PROTOCOL ERROR: " + input);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setGameBoard() throws IOException {
        rpcWriter.println("REQUEST: gameBoardHeight");
        int gameBoardHeight = Integer.parseInt(rpcReader.readLine());
        rpcWriter.println("REQUEST: gameBoardWidth");
        int gameBoardWidth = Integer.parseInt(rpcReader.readLine());
        Boolean[][] gameBoard = new Boolean[gameBoardHeight][gameBoardWidth];
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                rpcWriter.println("REQUEST: gameBoard[" + i + "][" + j + "]");
                gameBoard[i][j] = Boolean.parseBoolean(rpcReader.readLine());
            }
        }
        player.setGameBoard(gameBoard);
        rpcWriter.println("0: Okay");
    }

    private void setPlayerPosition() throws IOException {
        rpcWriter.println("REQUEST: x-Position");
        int x = Integer.parseInt(rpcReader.readLine());
        rpcWriter.println("REQUEST: y-Position");
        int y = Integer.parseInt(rpcReader.readLine());
        player.setPlayerPosition(x, y);
        rpcWriter.println("0: Okay");
    }

    private void setOpponentPosition() throws IOException {
        rpcWriter.println("REQUEST: opponentId");
        String opponentId = rpcReader.readLine();
        rpcWriter.println("REQUEST: x-Position");
        int x = Integer.parseInt(rpcReader.readLine());
        rpcWriter.println("REQUEST: y-Position");
        int y = Integer.parseInt(rpcReader.readLine());
        player.setOpponentPosition(opponentId, x, y);
        rpcWriter.println("0: Okay");
    }

    private void setOpponentName() throws IOException {
        rpcWriter.println("REQUEST: opponentId");
        String opponentId = rpcReader.readLine();
        rpcWriter.println("REQUEST: opponentName");
        String opponentName = rpcReader.readLine();
        player.setOpponentName(opponentId, opponentName);
        rpcWriter.println("0: Okay");
    }

    private void setPlayerScore() throws IOException {
        rpcWriter.println("REQUEST: score");
        int score = Integer.parseInt(rpcReader.readLine());
        player.setPlayerScore(score);
        rpcWriter.println("0: Okay");
    }

    private void setOpponentScore() throws IOException {
        rpcWriter.println("REQUEST: opponentId");
        String opponentId = rpcReader.readLine();
        rpcWriter.println("REQUEST: score");
        int score = Integer.parseInt(rpcReader.readLine());
        player.setOpponentScore(opponentId, score);
        rpcWriter.println("0: Okay");
    }

    private void setGhostPosition() throws IOException {
        rpcWriter.println("REQUEST: ghostId");
        String ghostId = rpcReader.readLine();
        rpcWriter.println("REQUEST: x-Position");
        int x = Integer.parseInt(rpcReader.readLine());
        rpcWriter.println("REQUEST: y-Position");
        int y = Integer.parseInt(rpcReader.readLine());
        player.setGhostPosition(ghostId, x, y);
        rpcWriter.println("0: Okay");
    }

    private void endConnection() throws IOException {
        socket.close();
        running = false;
    }
}
