package main.java.view;

import sas.Circle;
import sas.Rectangle;
import sas.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoardView {
    List<Rectangle> boarders = new ArrayList<>();
    List<Circle> circles = new ArrayList<>();
    int[][] gameBoard;
    private View view;

    public GameBoardView(int[][] gameBoard, View view) {
        this.gameBoard = gameBoard;
        this.view = view;
        draw();
    }

    public void updateGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
        draw();
    }

    private void draw() {
        boarders.forEach(view::remove);
        boarders.clear();
        circles.forEach(view::remove);
        circles.clear();
        for (int i = 0; i < gameBoard.length; i++) {
            int[] row = gameBoard[i];
            for (int j = 0; j < row.length; j++) {
                int field = row[j];
                if (field == 2) {
                    boarders.add(new Rectangle(i * 30, j * 30, 30, 30, Color.BLUE));
                } else if (field == 1) {
                    circles.add(new Circle(i * 30 + 14, j * 30 + 14, 2, Color.WHITE));
                }
            }
        }
    }
}
