package main.java.view;

import sas.Circle;
import sas.Rectangle;
import sas.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardView {
    List<Rectangle> borders = new ArrayList<>();
    Map<String, Circle> circles = new HashMap<>();
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

    public void removeScorePoint(int x, int y) {
        String key = x + ";" + y;
        Circle circle = circles.get(key);
        if (circle != null) {
            view.remove(circle);
            circles.remove(key);
        }
    }

    private void draw() {
        borders.forEach(view::remove);
        borders.clear();
        circles.values().forEach(view::remove);
        circles.clear();
        for (int i = 0; i < gameBoard.length; i++) {
            int[] row = gameBoard[i];
            for (int j = 0; j < row.length; j++) {
                int field = row[j];
                if (field == 2) {
                    borders.add(new Rectangle(i * pixelWidth + 120, j * pixelWidth + 130, pixelWidth, pixelWidth, Color.BLUE));
                } else if (field == 1) {
                    circles.put(i + ";" + j,new Circle(i * pixelWidth + pixelWidth / 2 + 119, j * pixelWidth + pixelWidth / 2 + 129, 2, Color.WHITE));
                }
            }
        }
    }
}
