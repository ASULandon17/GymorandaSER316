package main.java.memoranda.ui;

import main.java.memoranda.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class ClassPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar classesToolBar = new JToolBar();

    // tabbed pane to hold the basic and advanced courses tabs
    JTabbedPane classesTabbedPane = new JTabbedPane();
    JScrollPane beginnerClassesScrollPane = new JScrollPane();
    JScrollPane advancedClassesScrollPane = new JScrollPane();
    JPanel beginnerCardsPanel = new JPanel();
    JPanel advancedCardsPanel = new JPanel();


    JButton newClassBtn = new JButton();

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
        classesToolBar.setFloatable(false);

        newClassBtn.setText("Add a class");

        newClassBtn.setVisible(User.getUserType() == UserType.OWNER);
        newClassButtonHelper(newClassBtn);
        newClassBtn.setToolTipText("Add a new class");

        newClassBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Pass the classpanel so that we can refresh the UI on adding a new class
                NewclassPopup popup = new NewclassPopup(ClassPanel.this);
                popup.setVisible(true);

            }
        });

        this.setLayout(borderLayout1);

        // Only show advanced courses to users that meet the minimum belt rank requirement
        // Or to the owner
        advancedClassesScrollPane.setEnabled(false);
        if (User.getUserType() == UserType.OWNER || User.getBeltRank().isAdvanced() )


        // Tabbed pane contains a tab of beginner courses and a tab of advanced courses
        classesTabbedPane.addTab("Beginner", beginnerClassesScrollPane);
        classesTabbedPane.addTab("Advanced", advancedClassesScrollPane);

        // Set background color of the scroll panes
        beginnerClassesScrollPane.getViewport().setBackground(Color.WHITE);
        advancedClassesScrollPane.getViewport().setBackground(Color.WHITE);

        // Add tabbed pane to the class panel layout
        this.add(classesTabbedPane, BorderLayout.CENTER);


        classesToolBar.addSeparator(new Dimension(8, 24));
        classesToolBar.addSeparator(new Dimension(8, 24));
        classesToolBar.add(newClassBtn, null);

        this.add(classesToolBar, BorderLayout.NORTH);


}

    void initCardsPanel() {
        beginnerCardsPanel.removeAll();
        ArrayList<Course> courses = PersistentClass.getListOfCourses();

        beginnerCardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        advancedCardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Course course : courses) {

            JPanel card = createCourseCard(course);

            // decide if course goes onto beginner or advanced tab
            if (course.isCourseAdvanced()){

                advancedCardsPanel.add(card);
            } else {

                beginnerCardsPanel.add(card);
            }

        }

        beginnerClassesScrollPane.setViewportView(beginnerCardsPanel);
        advancedClassesScrollPane.setViewportView(advancedCardsPanel);

        beginnerCardsPanel.revalidate();
        beginnerCardsPanel.repaint();

        advancedCardsPanel.revalidate();
        advancedCardsPanel.repaint();
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