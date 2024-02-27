package main.java.memoranda.ui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.java.memoranda.GymUser;
import main.java.memoranda.User;
import main.java.memoranda.UserType;

public class NewTrainerPopup extends JFrame {

    private final JTextField usernameTextField;

    /**
     * Constructor for popup window.
     *
     * @param trainerPanel ref to parent
     */
    public NewTrainerPopup(TrainerPanel trainerPanel) {

        setTitle("Upgrade User to Trainer");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Username to Upgrade:");
        usernameTextField = new JTextField();

        JButton upgradeButton = new JButton("Upgrade");
        JButton cancelButton = new JButton("Cancel");

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(upgradeButton);
        panel.add(cancelButton);

        upgradeButton.addActionListener(e -> {
            performTrainerUpgrade();
            trainerPanel.refreshTrainerCards();
            clearForm();
            dispose(); // Close the popup window after upgrade
        });


        // Actions on close:
        cancelButton.addActionListener(e -> {
            clearForm();
            dispose();
        });

        add(panel);
        pack();
        // Center the popup window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearForm() {
        usernameTextField.setText("");
    }

    private void performTrainerUpgrade() {

        String username = usernameTextField.getText();

        GymUser user = User.getUser(username);

        if (user != null) {

            UserType type = user.getUserType();

            if (user.getUserType() == UserType.TRAINER) {
                JOptionPane.showMessageDialog(this,
                        "User is already a trainer");
                return;
            } else if (type == UserType.OWNER) {
                JOptionPane.showMessageDialog(this,
                        "User is already an owner");
                return;
            } else {

                // if they're not an owner or trainer already, upgrade to trainer.
                user.becomeTrainer();

            }

            // check upgrade status
            type = user.getUserType();

            if (type == UserType.TRAINER) {
                // display message to user
                JOptionPane.showMessageDialog(this, "User: " + username
                        + " has been upgraded to a trainer!");

                // update json file
                User.saveUsersToFile();

            } else if (type == UserType.MEMBER) {
                JOptionPane.showMessageDialog(this, "User: " + username
                        + "'s upgrade to trainer failed.");
            }

        } else {
            System.out.println("User does not exist");
        }


    }
}
