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
public class DropClassPopup extends JFrame {
    
    private Course course;
    private RoomPanel roomPanel;
    private JButton dropButton;
    
    public DropClassPopup(RoomPanel roomPanel, int courseId) {
        super("Drop Class?");
        this.roomPanel = roomPanel;
        this.course = PersistentClass.getCourseById(courseId);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initPanel();
        setLocationRelativeTo(null);
    }
    
    private void initPanel() {
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel infoPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        
        //Get data needed for correctly displaying class
        String time = course.getClassHour() > 12 ? (course.getClassHour() - 12 + "PM") : course.getClassHour() + "AM";
        String date = course.getClassMonth() + "/" + course.getClassDay() + "/" + course.getClassYear();
        
        //add class data to popup panel
        infoPanel.add(new JLabel("Class Name: " + course.getClassName()));
        infoPanel.add(new JLabel("Class Size: " + course.getCurrentClassSize()));
        infoPanel.add(new JLabel("Instructor: " + course.getInstructorName()));
        infoPanel.add(new JLabel("Date: " + date));
        infoPanel.add(new JLabel("Time: " + time));
        infoPanel.add(new JLabel("Length: " + course.getClassLength() + " hours"));
        
        dropButton = new JButton("Drop Class");
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropClass();
            }
        });
        
        // Add the form panel to the main panel
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        // Add the submit button to the main panel, aligned to the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(dropButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the JFrame
        add(mainPanel);
    }
    
    private void dropClass() {
        course.removeStudentFromRoster(User.getUsername());
        JOptionPane.showMessageDialog(this, "Class added succesfully");
        if(roomPanel != null){
            roomPanel.updateTables();
        }
        this.dispose();
    }
}