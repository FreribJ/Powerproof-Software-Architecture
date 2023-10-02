package main.java.view;

import sas.Circle;
import sas.Picture;

import java.awt.*;

public class PacmanView extends Circle { //extends Picture

        public PacmanView(boolean opponent, int x, int y) {
            super(x*30, y*30, 15, opponent ? new Color(255, 0, 0) : new Color(255, 255, 0));
            //super(x*30, y*30, 30, 30, opponent ? acman-opponent.png" : "pacman.png");
        }

        public void moveToCoordinate(int x, int y) {
            this.moveTo(x*30, y*30);
        }

}