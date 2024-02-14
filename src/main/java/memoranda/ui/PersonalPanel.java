package main.java.memoranda.ui;

import main.java.memoranda.User;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class PersonalPanel extends JPanel {
    private String username;


    public PersonalPanel() {
        username = User.getUsername();
        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        // Main panel that holds both the left and right panels
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Left panel configuration
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 200)); // Example size, adjust as needed
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Border around the left panel

        // Title label at the top of the left panel
        JLabel titleLabel = new JLabel("Thanks for using Gymoranda, " + username, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size and style
        leftPanel.add(titleLabel, BorderLayout.NORTH); // Add the title to the top of the left panel

        // Right panel configuration
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200, 200)); // Example size, adjust as needed
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Border around the right panel
        initializeRightPanel(rightPanel);
        // Add left panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2; // Takes up two-thirds of the space
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(leftPanel, gbc);

        // Add right panel to the main panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1; // Takes up one-third of the space
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(rightPanel, gbc);

        // Add the main panel to the PersonalPanel
        this.add(mainPanel, BorderLayout.CENTER);
    }


    private void initializeRightPanel(JPanel rightPanel) {
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around components

        // Fixed width for text fields
        int textFieldWidth = 250;


        JLabel usernameLabel = new JLabel("Username: " + username);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));


        JLabel newPasswordLabel = new JLabel("New Password:");
        JTextField newPasswordTextField = new JTextField();
        newPasswordTextField.setMaximumSize(new Dimension(textFieldWidth, newPasswordTextField.getPreferredSize().height + 5));


        JLabel confirmNewPasswordLabel = new JLabel("Confirm New Password:");
        JTextField confirmNewPasswordTextField = new JTextField();
        confirmNewPasswordTextField.setMaximumSize(new Dimension(textFieldWidth, confirmNewPasswordTextField.getPreferredSize().height + 5));

        JButton changePasswordButton = new JButton("Change Password");


        JPanel changePassword = new JPanel(new GridLayout(0, 1));
        Border padding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        changePassword.setBorder(padding);

        changePassword.add(usernameLabel);

        changePassword.add(newPasswordLabel);
        changePassword.add(newPasswordTextField);

        changePassword.add(confirmNewPasswordLabel);
        changePassword.add(confirmNewPasswordTextField);
        changePassword.add(changePasswordButton);
        rightPanel.add(changePassword);
    }
}