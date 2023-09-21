package main.java.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class GameBoardView extends JPanel {
    private int[][] gameBoard;
    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
        drawGameBoard();
    }

    private void drawGameBoard() {
        setBackground(BLACK);
        removeAll();
        for (int i = 0; i < gameBoard.length; i++) {
            int[] row = gameBoard[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 2) {
                    JPanel fieldPanel = new JPanel();
                    fieldPanel.setBounds(i * 20, j * 20, 20, 20);
                    //fieldPanel.setBorder(new LineBorder(BLUE, 3, true));
                    fieldPanel.setBackground(BLUE);
                    add(fieldPanel);
                }
            }
        }
        repaint();
    }
}
