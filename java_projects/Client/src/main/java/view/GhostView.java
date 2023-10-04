package main.java.view;

import sas.Rectangle;

import java.awt.*;

public class GhostView extends Rectangle {
    private int pixelWidth;

    public GhostView(int x, int y, int pixelWidth) {
        super(x*pixelWidth + 120, y*pixelWidth + 170, pixelWidth, pixelWidth, Color.CYAN);
        this.pixelWidth = pixelWidth;
    }

    public void moveToCoordinate(int x, int y) {
        this.moveTo(x*pixelWidth + 120, y*pixelWidth + 170);
    }
}
