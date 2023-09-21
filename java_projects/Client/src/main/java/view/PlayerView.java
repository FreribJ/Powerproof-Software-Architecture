package main.java.view;

import main.java.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

//TODO: Implement the UI here
// Die View bekommt die Daten vom Server und soll hier einfach nur die Daten anzeigen
public class PlayerView extends JFrame implements Player {

    private final String name;
    private final JLabel header = new JLabel("PacMan");
    private GameBoardView gameBoardView;

    public PlayerView(String name) {
        super("PacMan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        Container cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(Color.BLACK);

        header.setBounds(0,0,750,50);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setHorizontalTextPosition(SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 30));
        cp.add(header);

        gameBoardView = new GameBoardView();
        gameBoardView.setBounds(95,50,560,620);
        cp.add(gameBoardView);

        this.name = name;
    }

    @Override
    public void setGameBoard(int[][] gameBoard) {
        gameBoardView.setGameBoard(gameBoard);
        System.out.println("Drawing game board: " + Arrays.deepToString(gameBoard));
    }

    @Override
    public void setPlayerPosition(int x, int y) {
        System.out.println("Drawing player position: " + x + ", " + y);
    }

    @Override
    public void setOpponent(String playerId, String name, int x, int y) {
        System.out.println("Drawing opponent: " + playerId + ": " + name + ": " + x + ", " + y);
    }

    @Override
    public void setPlayerScore(int score) {
        System.out.println("Drawing player score: " + score);
    }

    @Override
    public void setOpponentScore(String playerId, int score) {
        System.out.println("Drawing opponent score: " + playerId + ": " + score);
    }

    @Override
    public void setGhostPosition(String ghostId, int x, int y) {
        System.out.println("Drawing ghost position: " + ghostId + ": " + x + ", " + y);
    }

    @Override
    public void gameStarted() {
        System.out.println("Game started");
    }

    @Override
    public void gameOver() {
        System.out.println("Game over");
    }

    @Override
    public String getName() {
        return name;
    }
}
