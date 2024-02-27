package main.java.memoranda.ui;

import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.memoranda.Course;
import main.java.memoranda.Trainer;
import main.java.memoranda.TrainerList;

/**
 * UI for Owner to manage the instructor assigned to a course.
 */
public class ManageInstructorPopup extends JFrame {

    /**
     * Constructor for the manage instructor UI.
     */
    public ManageInstructorPopup(ClassPanel classPanelRef, Course course) {
        super("Manage Instructor");
        // stores reference to parent class panel
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUserInterface(classPanelRef, course);

        setLocationRelativeTo(null); // centers the frame
    }


    /**
     * Builds UI for Manage Instructor Popup Window.
     *
     * @param course course object to reference course data
     */
    private void initUserInterface(ClassPanel classPanelRef, Course course) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel instructorPanel = new JPanel();
        instructorPanel.setLayout(new BoxLayout(instructorPanel, BoxLayout.Y_AXIS));

        JLabel currentInstructorLabel = new JLabel();
        currentInstructorLabel.setText("Current Instructor: ");

        // Displays not assigned if there is no instructor yet
        String instructorName = course.getInstructorName();

        if (instructorName == null) {
            instructorName = "Not Assigned";
        }
        JLabel instructorLabel = new JLabel(instructorName);
        instructorPanel.add(instructorLabel);

        // Set button text based on if course has an instructor
        JButton instructorButton = buildInstructorButton(classPanelRef, course);

        instructorButton.addActionListener(e -> {
            // open the assign instructor popup window
            AssignInstructorPopup assignInstructorPopup =
                    new AssignInstructorPopup(classPanelRef, course, getAvailableTrainers(course));
            dispose();
        });
        // add everything to the frame
        instructorPanel.add(currentInstructorLabel);
        instructorPanel.add(instructorButton);
        mainPanel.add(instructorPanel);
        add(mainPanel);
    }

    private static JButton buildInstructorButton(ClassPanel classPanelRef, Course course) {
        JButton instructorButton = new JButton();
        if (course.getInstructorName().isEmpty()) {
            instructorButton.setText("Assign Instructor");
        } else {
            instructorButton.setText("Change Instructor");
        }

        return instructorButton;
    }

    private static String[] getAvailableTrainers(Course course) {
        int courseTime = course.getClassHour();
        Vector<Trainer> trainerList = TrainerList.getTrainersAvailableAtTime(courseTime);

        String[] trainerArray = new String[trainerList.size()];
        int index = 0;
        for (Trainer trainer : trainerList) {
            trainerArray[index] = trainer.getTrainerName();
            index++;
        }

        return trainerArray;
    }
}
