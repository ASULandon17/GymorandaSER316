package main.java.memoranda.ui.gymoranda;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

/**
 * This class contains UI helper methods for consistent design across Gymoranda.
 */
public class LookAndFeel {

    /**
     * Look and feel for Gymoranda buttons.
     * @param button - button to configure
     */
    public static void gymButtonHelper(JButton button) {

        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        button.setOpaque(true);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));

    }


    /**
     * Private constructor for util class.
     */
    private LookAndFeel() {
        // No objects here!
    }
}
