package main.java.view;

import sas.Circle;
import sas.Picture;

import java.awt.*;

public class PacmanView extends Circle { //extends Picture

    private int pixelWidth;
        public PacmanView(boolean opponent, int x, int y, int pixelWidth) {
            super(x*pixelWidth, y*pixelWidth, pixelWidth/2, opponent ? new Color(255, 0, 0) : new Color(255, 255, 0));
            this.pixelWidth = pixelWidth;
            //super(x*30, y*30, 30, 30, opponent ? acman-opponent.png" : "pacman.png");
        }

        public void moveToCoordinate(int x, int y) {
            this.moveTo(x*pixelWidth, y*pixelWidth);
        }

}