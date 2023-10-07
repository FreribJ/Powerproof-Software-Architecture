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
            rpcWriter.println("PROTOCOL: 1. setGameBoard; 2. setPlayerPosition; 3. setOpponent; 4. setPlayerScore; 5. setOpponentScore; 6. setGhostPosition; 7. gameStarted; 8. gameOver; 9. removeScorePoint; 10. endConnection");
            try {
                String input = rpcReader.readLine();
                switch (input) {
                    case "1" -> setGameBoard();
                    case "2" -> setPlayerPosition();
                    case "3" -> setOpponent();
                    case "4" -> setPlayerScore();
                    case "5" -> setOpponentScore();
                    case "6" -> setGhostPosition();
                    case "7" -> gameStarted();
                    case "8" -> gameOver();
                    case "9" -> removeScorePoint();
                    case "10" -> endConnection();
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
        int[][] gameBoard = new int[gameBoardHeight][gameBoardWidth];
        rpcWriter.println("REQUEST: gameBoard");
        String gameBoardString = rpcReader.readLine();
        int counter = 0;
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                gameBoard[i][j] = Integer.parseInt(gameBoardString.substring(counter, counter + 1));
                counter++;
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

    private void setOpponent() throws IOException {
        rpcWriter.println("REQUEST: opponentId");
        String opponentId = rpcReader.readLine();
        rpcWriter.println("REQUEST: opponentName");
        String opponentName = rpcReader.readLine();
        rpcWriter.println("REQUEST: x-Position");
        int x = Integer.parseInt(rpcReader.readLine());
        rpcWriter.println("REQUEST: y-Position");
        int y = Integer.parseInt(rpcReader.readLine());
        player.setOpponent(opponentId, opponentName, x, y);
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

    private void gameStarted() throws IOException {
        rpcWriter.println("0: Okay");
        player.gameStarted();
    }

    private void gameOver() throws IOException {
        rpcWriter.println("0: Okay");
        player.gameOver();
    }

    private void endConnection() throws IOException {
        socket.close();
        running = false;
    }

    private void removeScorePoint() throws IOException {
        rpcWriter.println("REQUEST: x-Position");
        int x = Integer.parseInt(rpcReader.readLine());
        rpcWriter.println("REQUEST: y-Position");
        int y = Integer.parseInt(rpcReader.readLine());
        player.removeScorePoint(x, y);
        rpcWriter.println("0: Okay");
    }
}
