package main.java.view;

import sas.Rectangle;

import java.awt.*;

public class GhostView extends Rectangle {

    public GhostView(int x, int y) {
        super(x*30, y*30, 30, 30, Color.CYAN);
    }

    public void moveToCoordinate(int x, int y) {
        this.moveTo(x*30, y*30);
    }
}
