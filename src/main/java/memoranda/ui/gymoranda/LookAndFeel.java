package main.java.memoranda.ui.gymoranda;

import javax.swing.*;
import java.awt.*;

public class LookAndFeel {


    public static void gymButtonHelper(JButton button) {

        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));

    }


    private LookAndFeel() {
        // No objects here!
    }
}
