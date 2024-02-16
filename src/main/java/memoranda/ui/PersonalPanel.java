package main.java.memoranda.ui;

import main.java.memoranda.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


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
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); // Vertical layout for labels
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding

        // Title label
        JLabel titleLabel = new JLabel("Thanks for using Gymoranda, " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align in BoxLayout

        // Upcoming classes label
        JLabel upcomingLabel = new JLabel("My upcoming classes:");
        upcomingLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font, adjust as needed
        upcomingLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align in BoxLayout

        // Add labels to the label panel
        labelPanel.add(titleLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer between labels
        labelPanel.add(upcomingLabel);

        // Add the label panel to the top of the left panel
        leftPanel.add(labelPanel, BorderLayout.NORTH);

        initializeLeftPanel(leftPanel);
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
        JPanel trainerSchedule;
        if(User.getUserType() == UserType.TRAINER){
            trainerSchedule = new JPanel(new GridLayout(0, 1));
            Border trainerPad = BorderFactory.createEmptyBorder(10, 30, 10, 30);
            trainerSchedule.setBorder(trainerPad);
            TrainerList trainer = new TrainerList();
            Trainer t = trainer.getTrainer(username);
            class TimeItem {
                String display;
                int value;

                public TimeItem(int hour) {
                    this.value = hour;
                    this.display = String.format("%d:00 %s", hour <= 12 ? hour : hour - 12, hour < 12 ? "AM" : "PM");
                }

                @Override
                public String toString() { // ComboBox uses this method to display the item
                    return display;
                }

                public int getValue() {
                    return value;
                }
            }

            JComboBox<TimeItem> scheduleStartComboBox = new JComboBox<>();
            JComboBox<TimeItem> scheduleEndComboBox = new JComboBox<>();
            for (int hour = 8; hour <= 19; hour++) {
                scheduleStartComboBox.addItem(new TimeItem(hour));
                scheduleEndComboBox.addItem(new TimeItem(hour));
            }

            // Set the combo boxes to the trainer's current start and end availability
            int startAvailability = t.getStartAvailability();
            int endAvailability = t.getEndAvailability();

            // Preselect the start availability
            for (int i = 0; i < scheduleStartComboBox.getItemCount(); i++) {
                if (scheduleStartComboBox.getItemAt(i).getValue() == startAvailability) {
                    scheduleStartComboBox.setSelectedIndex(i);
                    break;
                }
            }

            // Preselect the end availability
            for (int i = 0; i < scheduleEndComboBox.getItemCount(); i++) {
                if (scheduleEndComboBox.getItemAt(i).getValue() == endAvailability) {
                    scheduleEndComboBox.setSelectedIndex(i);
                    break;
                }
            }


            scheduleStartComboBox.addActionListener(e -> {
                TimeItem selectedItem = (TimeItem) scheduleStartComboBox.getSelectedItem();
                if (selectedItem != null) {
                    trainer.setTrainerStartAvailability(username,selectedItem.getValue());
                }
            });

            scheduleEndComboBox.addActionListener(e -> {
                TimeItem selectedEnd = (TimeItem) scheduleEndComboBox.getSelectedItem();
                if (selectedEnd != null) {
                    trainer.setTrainerEndAvailability(username,selectedEnd.getValue());
                }
            });
            trainerSchedule.add(new JLabel("Schedule Start:"));
            trainerSchedule.add(scheduleStartComboBox);
            trainerSchedule.add(new JLabel("Schedule End:"));
            trainerSchedule.add(scheduleEndComboBox);

            rightPanel.add(trainerSchedule);

        }


        changePasswordButton.addActionListener(e -> {
            String newPassword = newPasswordTextField.getText();
            String confirmNewPassword = confirmNewPasswordTextField.getText();

            if(newPassword.equals(confirmNewPassword)){
                boolean didChange = User.changePassword(newPassword);
                if(!didChange){
                    JOptionPane.showMessageDialog(this, "Error changing password");
                } else{
                    JOptionPane.showMessageDialog(this, "Changed password successfully.");
                }
            } else{
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
            }
        });
    }

    private void initializeLeftPanel(JPanel leftPanel){
        ArrayList<Course> nextEnrolled = PersistentClass.getNext5EnrolledClasses(username);
        if(nextEnrolled == null){
            return;
        }
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        for (Course course: nextEnrolled) {
            JPanel card = createCourseCard(course);
            cardsPanel.add(card);
        }
        leftPanel.add(cardsPanel);
    }

    private JPanel createCourseCard(Course course) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        Border roundedLineBorder = new LineBorder(Color.BLACK, 1, true);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        CompoundBorder compoundBorder = new CompoundBorder(roundedLineBorder, paddingBorder);
        card.setBorder(compoundBorder);
        JPanel infoPanel = buildInfoPanel(course);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private static JPanel buildInfoPanel(Course course) {

        JLabel instructorNameLabel;

        JLabel classNameLabel = new JLabel("Class: " + course.getClassName());
        if (course.getInstructorName().equals("")) {
            instructorNameLabel = new JLabel("Instructor: Not Assigned");
        } else {
            instructorNameLabel = new JLabel("Instructor: " + course.getInstructorName());
        }

        JLabel classSizeLabel = new JLabel("Size: " + course.getCurrentClassSize() + "/" + course.getMaxClassSize());

        JLabel classLength = new JLabel("Length:" + course.getClassLength() + " hours.");
        String isPublic = "Private";
        if (course.getPublic()) {
            isPublic = "Public";
        }

        JLabel classPrivacy = new JLabel("Class Type: " + isPublic);
        JPanel infoPanel = new JPanel(new GridLayout(5, 1));

        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        infoPanel.add(classNameLabel);
        infoPanel.add(instructorNameLabel);
        infoPanel.add(classSizeLabel);
        infoPanel.add(classLength);
        infoPanel.add(classPrivacy);

        return infoPanel;
    }
}