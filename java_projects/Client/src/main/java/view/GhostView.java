package main.java.view;


import sas.Picture;

public class GhostView extends Picture {
    private int pixelWidth;

    public GhostView(int x, int y, int pixelWidth, int id) {
        super(x * pixelWidth + 120, y * pixelWidth + 170, pixelWidth, pixelWidth, id % 4 == 0 ? "ghost1.png" : id % 4 == 1 ? "ghost2.png" : id % 4 == 2 ? "ghost3.png" : "ghost4.png");
        this.pixelWidth = pixelWidth;
    }

    public void moveToCoordinate(int x, int y) {
        this.moveTo(x * pixelWidth + 120, y * pixelWidth + 170);
    }
}
