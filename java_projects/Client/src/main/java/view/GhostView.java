package main.java.view;


import sas.Picture;

public class GhostView extends Picture {
    private int pixelWidth;

    public GhostView(int x, int y, int pixelWidth) {
        super(x * pixelWidth + 120, y * pixelWidth + 170, pixelWidth, pixelWidth, "ghost.png");
        this.pixelWidth = pixelWidth;
    }

    public void moveToCoordinate(int x, int y) {
        this.moveTo(x * pixelWidth + 120, y * pixelWidth + 170);
    }
}
