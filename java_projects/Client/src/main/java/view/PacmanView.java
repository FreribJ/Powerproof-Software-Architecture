package main.java.view;

import sas.Picture;

public class PacmanView extends Picture {

    private int pixelWidth;
    private int xPosition = 0;
    private int yPosition = 0;

    public PacmanView(boolean opponent, int x, int y, int pixelWidth) {
        super(x * pixelWidth + 120, y * pixelWidth + 170, pixelWidth, pixelWidth, opponent ? "pacman-opponent.png" : "pacman.png");
        this.pixelWidth = pixelWidth;
    }

    public void moveToCoordinate(int x, int y) {
        this.moveTo(x * pixelWidth + 120, y * pixelWidth + 170);
        if (xPosition < x) {
            this.turnTo(90);
        } else if (xPosition > x) {
            this.turnTo(270);
        } else if (yPosition < y) {
            this.turnTo(180);
        } else if (yPosition > y) {
            this.turnTo(0);
        }
        xPosition = x;
        yPosition = y;
    }

}