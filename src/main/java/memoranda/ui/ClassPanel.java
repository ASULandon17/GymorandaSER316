package main.java.memoranda.ui;

import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import main.java.memoranda.User;
import main.java.memoranda.UserType;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class ClassPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar eventsToolBar = new JToolBar();
    JScrollPane scrollPane = new JScrollPane();
    private JPanel cardsPanel = new JPanel();

    JButton newClass = new JButton();

    public ClassPanel() {
        try {
            jbInit();
            initCardsPanel();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    //This is used in NewclassPopup so that on adding a new class, the UI refreshes.
    public void refreshCards(){
        initCardsPanel();
    }

    void newClassButtonHelper(JButton button){
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));
    }

    void jbInit() throws Exception {
        eventsToolBar.setFloatable(false);

        newClass.setText("Add a class");
        if(User.getUserType() == UserType.OWNER){
            newClass.setVisible(true);
        } else {
            newClass.setVisible(false);
        }
        newClassButtonHelper(newClass);
        newClass.setToolTipText("Add a new class");
        newClass.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Pass the classpanel so that we can refresh the UI on adding a new class
                NewclassPopup popup = new NewclassPopup(ClassPanel.this);
                popup.setVisible(true);

            }
        });

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);

        this.add(scrollPane, BorderLayout.CENTER);

        eventsToolBar.addSeparator(new Dimension(8, 24));


        eventsToolBar.addSeparator(new Dimension(8, 24));

        eventsToolBar.add(newClass, null);
        this.add(eventsToolBar, BorderLayout.NORTH);


}
    void initCardsPanel() {
        cardsPanel.removeAll();
        ArrayList<Course> courses = PersistentClass.getListOfCourses();

        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Course course : courses) {
            JPanel card = createCourseCard(course);
            cardsPanel.add(card);
        }

        scrollPane.setViewportView(cardsPanel);
        cardsPanel.revalidate();
        cardsPanel.repaint();
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


        // Add "Delete and Manage Instructor" buttons to class card if user is an Owner
        if (User.getUserType() == UserType.OWNER) {

            JPanel buttonPanel = buildButtonPanel(course);
            card.add(buttonPanel, BorderLayout.SOUTH);
        }

        return card;
    }

    /**
     * Helper to build the button panel for owner management actions
     * @param course course object
     * @return button panel with owner options
     */
    private JPanel buildButtonPanel(Course course) {
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            PersistentClass.deleteCourseById(course.getClassId());
            refreshCards();
        });

        JButton manageInstructorButton = new JButton("Manage Instructor");
        manageInstructorButton.addActionListener(e -> {
            // If manage instructor is clicked, open up instructor manager window and pull in course object
            ManageInstructorPopup manageInstructorPopup = new ManageInstructorPopup(ClassPanel.this, course);
            manageInstructorPopup.setVisible(true);

        });

        // Create button panel for owner buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(deleteButton);
        buttonPanel.add(manageInstructorButton);
        return buttonPanel;
    }

    /**
     * Constructs the infoPanel for a class card.
     * @param course course added
     * @return card infoPanel
     */
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