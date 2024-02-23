package main.java.memoranda.ui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.memoranda.Course;

/**
 * UI for Owner to manage the instructor assigned to a course.
 */
public class ManageInstructorPopup extends JFrame {

    /**
     * Constructor for the manage instructor UI.
     */
    public ManageInstructorPopup(ClassPanel cpRef, Course course) {
        super("Manage Instructor");
        // stores reference to parent class panel
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUserInterface(course);

        setLocationRelativeTo(null); // centers the frame
    }


    /**
     * Builds UI for Manage Instructor Popup Window.
     *
     * @param course course object to reference course data
     */
    private void initUserInterface(Course course) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel instructorPanel = new JPanel();
        instructorPanel.setLayout(new BoxLayout(instructorPanel, BoxLayout.Y_AXIS));

        JLabel currentInstructorLabel = new JLabel();
        currentInstructorLabel.setText("Current Instructor: ");

        // will just be empty if there's not an instructor
        JLabel instructorLabel = new JLabel(course.getInstructorName());
        instructorPanel.add(instructorLabel);

        // Set button text based on if course has an instructor
        JButton instructorButton = new JButton();
        if (course.getInstructorName().isEmpty()) {
            instructorButton.setText("Assign Instructor");
        } else {
            instructorButton.setText("Change Instructor");
        }

        instructorButton.addActionListener(e -> {
            //todo:
            // Open up UI that displays currently available instructors
            // Owner can select one
            // Instructor is updated on actual course object and displayed on UI
        });

        instructorPanel.add(currentInstructorLabel);

        instructorPanel.add(instructorButton);

        mainPanel.add(instructorPanel);

        add(mainPanel);
    }
}
