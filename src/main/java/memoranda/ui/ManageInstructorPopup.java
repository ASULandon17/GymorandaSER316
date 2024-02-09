package main.java.memoranda.ui;

import main.java.memoranda.Course;

import javax.swing.*;

public class ManageInstructorPopup extends JFrame {

    private ClassPanel cpRef; // stores reference to parent class panel
    private JLabel currentInstructorLabel;
    private JLabel instructorLabel;
    private JButton instructorButton;



    public ManageInstructorPopup(ClassPanel cpRef, Course course) {
        super("Manage Instructor");
        this.cpRef = cpRef;
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(course);

        setLocationRelativeTo(null); // centers the frame
    }


    private void initUI(Course course) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel instructorPanel = new JPanel();
        instructorPanel.setLayout(new BoxLayout(instructorPanel, BoxLayout.Y_AXIS));

        currentInstructorLabel = new JLabel();
        currentInstructorLabel.setText("Current Instructor: ");
        instructorLabel = new JLabel(course.getInstructorName()); // will just be empty if there's not an instructor


        // Set button text based on if course has an instructor
        instructorButton = new JButton();
        if (course.getInstructorName().isEmpty()) {
            instructorButton.setText("Assign Instructor");
        } else {
            instructorButton.setText("Change Instructor");
        }

        instructorButton.addActionListener( e -> {
            // Do things - Sprint 3 todo

            // Open up UI that displays currently available instructors
            // Owner can select one
            // Instructor is updated on actual course object and displayed on UI
        });

        instructorPanel.add(currentInstructorLabel);
        instructorPanel.add(instructorLabel);
        instructorPanel.add(instructorButton);

        mainPanel.add(instructorPanel);

        add(mainPanel);
    }
}
