package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import main.java.memoranda.User;

//followed the model of 'NewclassPopup.java' so we have continuity
public class ManageClassPopup extends JFrame {
    
    private Course course;
    private RoomPanel roomPanel;
    private JButton dropButton;
    private JButton signUpButton;
    
    /**
     * Constructor for ManageClassPopup.
     * @param roomPanel parent panel for popup to appear on
     * @param course course that is being managed
     */
    public ManageClassPopup(RoomPanel roomPanel, Course course) {
        super("Class Info: ");
        this.roomPanel = roomPanel;
        this.course = course;
        setSize(250, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initPanel();
        setLocationRelativeTo(null);
    }
    
    /**
     * Initiation for the panel.
     */
    private void initPanel() {
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel infoPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        
        //Get data needed for correctly displaying class
        int hour = course.getClassHour();
        String time = hour > 12 ? (hour - 12 + "PM") : hour + "AM";
        String date = course.getClassMonth() + "/" 
                    + course.getClassDay() + "/" 
                    + course.getClassYear();
        String difficulty = course.isCourseAdvanced() ? "Advanced" : "Beginner";
        
        //add class data to popup panel
        infoPanel.add(new JLabel("Class Name: " + course.getClassName(), SwingConstants.CENTER));
        infoPanel.add(new JLabel("Class Size: " + course.getMaxClassSize(), SwingConstants.CENTER));
        infoPanel.add(new JLabel("Difficulty: " + difficulty, SwingConstants.CENTER));
        infoPanel.add(new JLabel("Instructor: " + course.getInstructorName(), 
                SwingConstants.CENTER));
        infoPanel.add(new JLabel("Date: " + date, SwingConstants.CENTER));
        infoPanel.add(new JLabel("Time: " + time, SwingConstants.CENTER));
        infoPanel.add(new JLabel("Length: " + course.getClassLength() + " hours", 
                SwingConstants.CENTER));
        infoPanel.add(new JLabel("Availability: " + (course.getMaxClassSize() 
                                                   - course.getCurrentClassSize()), 
                                                   SwingConstants.CENTER));
        
        //Listener for when "drop class" button is clicked
        dropButton = new JButton("Drop Class");
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropClass();
            }
        });
        
        //Listener for when "sign up" button is clicked
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClass();
            }
        });
        
        JPanel infoPanelContainer = new JPanel(new GridBagLayout());
        
        // Add info panel to its container
        infoPanelContainer.add(infoPanel);
        // Add the info panel to the main panel
        mainPanel.add(infoPanelContainer, BorderLayout.CENTER);
        // Choose which button depending on if user is in class or not
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (course.isStudentRegistered(User.getUsername())) {
            buttonPanel.add(dropButton);
        } else {
            buttonPanel.add(signUpButton);
        }
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the JFrame
        add(mainPanel);
    }
    
    /**
     * Drops the class in question when button is clicked.
     */
    private void dropClass() {
        PersistentClass.removeStudentFromCourse(User.getUsername(), course.getClassId());
        JOptionPane.showMessageDialog(this, "You dropped the class succesfully!");
        if (roomPanel != null) {
            roomPanel.updateTables();
        }
        this.dispose();
    }
    
    /**
     * Adds the class in question when button is clicked.
     */
    private void addClass() {
        if (course.getCurrentClassSize() < course.getMaxClassSize()) {
            if (course.isCourseAdvanced() && (!User.getBeltRank().isAdvanced())) {
                JOptionPane.showMessageDialog(this, 
                "You need to have a belt rank of at least blue to enter an advanced class");
                this.dispose();
                return;
            }
            PersistentClass.addStudentToCourse(User.getUsername(), course.getClassId());
            JOptionPane.showMessageDialog(this, "You were added to the class succesfully");
        } else {
            JOptionPane.showMessageDialog(this, "Not enough room in the class!");
        }
        if (roomPanel != null) {
            roomPanel.updateTables();
        }
        this.dispose();
    }
}