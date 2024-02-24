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

    public NewTrainerPopup() {

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
            user.becomeTrainer();

            if(user.getUserType() == UserType.TRAINER) {
                // display message to user
                JOptionPane.showMessageDialog(this, "User: " + username
                        + " has been upgraded to a trainer!");

                // update json file
                User.saveUsersToFile();
            }



        } else {
            System.out.println("User does not exist");
        }








    }
}
