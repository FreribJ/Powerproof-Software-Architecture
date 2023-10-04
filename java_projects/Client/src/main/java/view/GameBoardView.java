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
    private int pixelWidth;

    public GameBoardView(int[][] gameBoard, View view, int pixelWidth) {
        this.pixelWidth = pixelWidth;
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
                    boarders.add(new Rectangle(i * pixelWidth + 120, j * pixelWidth + 170, pixelWidth, pixelWidth, Color.BLUE));
                } else if (field == 1) {
                    circles.add(new Circle(i * pixelWidth + pixelWidth / 2 + 119, j * pixelWidth + pixelWidth / 2 + 169, 2, Color.WHITE));
                }
            }
        }
    }
}
