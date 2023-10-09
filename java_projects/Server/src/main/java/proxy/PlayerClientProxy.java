package main.java.proxy;

import main.java.helper.RPCReader;
import main.java.helper.RPCWriter;
import main.java.model.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class PlayerClientProxy implements Player {

    private final String name;
    private final Socket socket;
    private final RPCReader rpcReader;
    private final RPCWriter rpcWriter;

    public PlayerClientProxy(Socket socket, String name) throws IOException {
        this.name = name;
        this.socket = socket;
        rpcReader = new RPCReader(new InputStreamReader(socket.getInputStream()));
        rpcWriter = new RPCWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public synchronized void setGameBoard(int[][] gameBoard) {
        try {
            rpcReader.readLine();
            rpcWriter.println("1");
            rpcReader.readLine();
            rpcWriter.println(gameBoard.length);
            rpcReader.readLine();
            rpcWriter.println(gameBoard[0].length);
            rpcReader.readLine();
            String gameBoardString = "";
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[0].length; j++) {
                    gameBoardString += gameBoard[i][j];
                }
            }
            rpcWriter.println(gameBoardString);
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
    public synchronized void removeScorePoint(int x, int y) {
        try {
            rpcReader.readLine();
            rpcWriter.println("9");
            rpcReader.readLine();
            rpcWriter.println(x);
            rpcReader.readLine();
            rpcWriter.println(y);
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
    public synchronized void setPlayerPosition(int x, int y)  {
        try {
            rpcReader.readLine();
            rpcWriter.println("2");
            rpcReader.readLine();
            rpcWriter.println(x);
            rpcReader.readLine();
            rpcWriter.println(y);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            throw new RuntimeException(returnCode);
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void setOpponent(String playerId, String name, int x, int y)  {
        try {

            rpcReader.readLine();
            rpcWriter.println("3");
            rpcReader.readLine();
            rpcWriter.println(playerId);
            rpcReader.readLine();
            rpcWriter.println(name);
            rpcReader.readLine();
            rpcWriter.println(x);
            rpcReader.readLine();
            rpcWriter.println(y);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            throw new RuntimeException(returnCode);
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void setPlayerScore(int score)  {
        try {
            rpcReader.readLine();
            rpcWriter.println("4");
            rpcReader.readLine();
            rpcWriter.println(score);
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
    public synchronized void setOpponentScore(String playerId, int score)  {
        try {
            rpcReader.readLine();
            rpcWriter.println("5");
            rpcReader.readLine();
            rpcWriter.println(playerId);
            rpcReader.readLine();
            rpcWriter.println(score);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
            throw new RuntimeException(returnCode);
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void setGhostPosition(String ghostId, int x, int y)  {
        try {
            rpcReader.readLine();
            rpcWriter.println("6");
            rpcReader.readLine();
            rpcWriter.println(ghostId);
            rpcReader.readLine();
            rpcWriter.println(x);
            rpcReader.readLine();
            rpcWriter.println(y);
            String returnCode = rpcReader.readLine();
            if (returnCode.startsWith("0")) {
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void gameStarted()  {
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
    public synchronized void gameOver()  {
        try {
            rpcReader.readLine();
            rpcWriter.println("8");
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
    public String getName() {
        return name;
    }

    @Override
    public synchronized void endConnection()  {
        try {
            rpcReader.readLine();
            rpcWriter.println("10");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
