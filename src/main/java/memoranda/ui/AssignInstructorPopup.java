package main.java.memoranda.ui;

import java.awt.GridLayout;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;

public class AssignInstructorPopup extends JFrame {

    private final JComboBox<String> trainerOptions;

    public AssignInstructorPopup(ClassPanel classPanelRef, Course course,
                                 String[] availableTrainers) {

        setTitle("Assign Instructor");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        trainerOptions = new JComboBox<>(availableTrainers);

        JButton assignButton = new JButton("Assign");

        assignButton.addActionListener(e -> {

            assignNewInstructor(classPanelRef, course);

        });


        panel.add(new JLabel("Trainer options: "));
        panel.add(trainerOptions);
        panel.add(assignButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void assignNewInstructor(ClassPanel classPanelRef, Course course) {
        // add new instructor to the course, save courses, then refresh course cards

        String newTrainer = Objects.requireNonNull(trainerOptions.getSelectedItem()).toString();
        course.setInstructorName(newTrainer);
        // save the classes so refresh pulls the new data
        PersistentClass.saveClassesToFile();

        String message = newTrainer + " assigned to " + course.getClassName();
        classPanelRef.refreshCards();
        JOptionPane.showMessageDialog(null, message);
        dispose();
    }
}
