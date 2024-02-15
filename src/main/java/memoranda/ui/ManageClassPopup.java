package main.java.memoranda.ui;

import main.java.memoranda.Course;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.java.memoranda.PersistentClass;
import main.java.memoranda.User;

//followed the model of 'NewclassPopup.java' so we have continuity
public class ManageClassPopup extends JFrame {
    
    private Course course;
    private RoomPanel roomPanel;
    private JButton dropButton;
    private JButton signUpButton;
    
    public ManageClassPopup(RoomPanel roomPanel, Course course) {
        super("Class Info: ");
        this.roomPanel = roomPanel;
        this.course = course;
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initPanel();
        setLocationRelativeTo(null);
    }
    
    private void initPanel() {
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel infoPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        
        //Get data needed for correctly displaying class
        String time = course.getClassHour() > 12 ? (course.getClassHour() - 12 + "PM") : course.getClassHour() + "AM";
        String date = course.getClassMonth() + "/" + course.getClassDay() + "/" + course.getClassYear();
        
        //add class data to popup panel
        infoPanel.add(new JLabel("Class Name: " + course.getClassName()));
        infoPanel.add(new JLabel("Class Size: " + course.getMaxClassSize()));
        infoPanel.add(new JLabel("Instructor: " + course.getInstructorName()));
        infoPanel.add(new JLabel("Date: " + date));
        infoPanel.add(new JLabel("Time: " + time));
        infoPanel.add(new JLabel("Length: " + course.getClassLength() + " hours"));
        infoPanel.add(new JLabel("Availability: " + (course.getMaxClassSize() - course.getCurrentClassSize())));
        
        dropButton = new JButton("Drop Class");
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropClass();
            }
        });
        
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClass();
            }
        });
        
        // Add the info panel to the main panel
        mainPanel.add(infoPanel, BorderLayout.CENTER);
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
    
    private void dropClass() {
        PersistentClass.removeStudentFromCourse(User.getUsername(), course.getClassId());
        JOptionPane.showMessageDialog(this, "You dropped the class succesfully!");
        if(roomPanel != null){
            roomPanel.updateTables();
        }
        this.dispose();
    }
    
    private void addClass() {
        if (course.getCurrentClassSize() < course.getMaxClassSize()) {
            PersistentClass.addStudentToCourse(User.getUsername(), course.getClassId());
            JOptionPane.showMessageDialog(this, "You were added to the class succesfully");
        } else {
            JOptionPane.showMessageDialog(this, "Not enough room in the class!");
        }
        if(roomPanel != null){
            roomPanel.updateTables();
        }
        this.dispose();
    }
}