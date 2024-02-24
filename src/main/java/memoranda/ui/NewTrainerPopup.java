package main.java.memoranda.ui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewTrainerPopup extends JFrame {

    private JTextField usernameTextField;

    public NewTrainerPopup() {

        setTitle("Upgrade User to Trainer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField();

        JButton upgradeButton = new JButton("Upgrade");
        JButton cancelButton = new JButton("Cancel");

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(upgradeButton);
        panel.add(cancelButton);

        upgradeButton.addActionListener(e -> {
            String username = usernameTextField.getText();

            // Perform upgrade logic here
            JOptionPane.showMessageDialog(null, "User '" + username + "' has been upgraded to Trainer.");
            dispose(); // Close the popup window after upgrade
        });

        cancelButton.addActionListener(e -> {
            dispose(); // Close the popup window if cancel is clicked
        });

        add(panel);
        pack();
        setLocationRelativeTo(null); // Center the popup window
        setVisible(true);
    }

    private void clearForm() {
        usernameTextField.setText("");
    }

    private void performTrainerUpgrade() {

        String username = usernameTextField.getText();




    }
}
