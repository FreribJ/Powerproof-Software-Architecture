package main.java.view;

import javax.swing.*;

// Hier habe ich einfach mal was ausprobiert. IntelliJ hat so ein GUI Tool, der mit Swing arbeitet.
public class PacMan {

    public PacMan() {
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    private JPanel panel;
    private JLabel label;
}
