package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import main.java.memoranda.User;
import main.java.memoranda.UserType;
import main.java.memoranda.ui.gymoranda.LookAndFeel;


/**
 * Builds the class panel UI.
 */
public class ClassPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar classesToolBar = new JToolBar();

    // tabbed pane to hold the basic and advanced courses tabs
    JTabbedPane classesTabbedPane = new JTabbedPane();
    JScrollPane beginnerClassesScrollPane = new JScrollPane();
    JScrollPane advancedClassesScrollPane = new JScrollPane();
    JPanel beginnerCardsPanel = new JPanel();
    JPanel advancedCardsPanel = new JPanel();
    JPanel trainerInfoPanel = new JPanel();

    JButton refreshCardsBtn = new JButton();
    JButton newClassBtn = new JButton();

    /**
     * <<<<<<< HEAD Constructor for Class Panel UI. ======= Constructor for ClassPanel. >>>>>>> dev
     */
    public ClassPanel() {
        try {
            jbInit();
            initCardsPanel();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }


    /**
     * Refreshes class cards when new courses are added.
     */

    public void refreshCards() {
        initCardsPanel();
    }

    private void classButtonHelper(JButton button) {
        LookAndFeel.gymButtonHelper(button);

    }


    void jbInit() throws Exception {
        classesToolBar.setFloatable(false);

        newClassBtn.setText("Add a class");
        newClassBtn.setVisible(User.getUserType() == UserType.OWNER);
        classButtonHelper(newClassBtn);
        newClassBtn.setToolTipText("Add a new class");

        newClassBtn.addActionListener(e -> {
            //Pass the classpanel so that we can refresh the UI on adding a new class
            NewClassPopup popup = new NewClassPopup(ClassPanel.this);
            popup.setVisible(true);

        });


        refreshCardsBtn.setText("Refresh Cards");
        classButtonHelper(refreshCardsBtn);
        refreshCardsBtn.setToolTipText("Refresh cards to load changes");
        refreshCardsBtn.addActionListener(e -> {

            // refresh the page when the refresh button is pressed

            if ((User.getUserType() == UserType.OWNER) || (User.getBeltRank().isAdvanced())) {
                classesTabbedPane.setEnabledAt(1, true);
            } else {
                classesTabbedPane.setEnabledAt(1, false);
            }

            classesTabbedPane.revalidate();
            classesTabbedPane.repaint();
        });


        this.setLayout(borderLayout1);


        // Tabbed pane contains a tab of beginner courses and a tab of advanced courses
        classesTabbedPane.addTab("Beginner", beginnerClassesScrollPane);
        classesTabbedPane.addTab("Advanced", advancedClassesScrollPane);

        // Set background color of the scroll panes
        beginnerClassesScrollPane.getViewport().setBackground(Color.WHITE);
        advancedClassesScrollPane.getViewport().setBackground(Color.WHITE);

        // Only show advanced courses to users that meet the minimum belt rank requirement
        // Or to the owner
        classesTabbedPane.setEnabledAt(1, false);

        if ((User.getUserType() == UserType.OWNER) || (User.getBeltRank().isAdvanced())) {
            classesTabbedPane.setEnabledAt(1, true);
        }

        // Add tabbed pane to the class panel layout
        this.add(classesTabbedPane, BorderLayout.CENTER);


        // Place buttons on the buttons bar with some space between them
        classesToolBar.addSeparator(new Dimension(8, 24));
        classesToolBar.add(newClassBtn, null);
        classesToolBar.addSeparator(new Dimension(8, 24));
        classesToolBar.add(refreshCardsBtn, null);
        classesToolBar.addSeparator(new Dimension(8, 24));

        this.add(classesToolBar, BorderLayout.NORTH);


    }


    void initCardsPanel() {
        beginnerCardsPanel.removeAll();
        advancedCardsPanel.removeAll();

        List<Course> courses = PersistentClass.getListOfCourses();

        beginnerCardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        advancedCardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));


        //message for trainers telling them their classes are outlined in red
        if (User.getUserType() == UserType.TRAINER) {
            trainerInfoPanel.setLayout(new GridBagLayout());
            trainerInfoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            JLabel trainerInfoLabel = new JLabel("CLASSES YOU'RE TEACHING ARE OUTLINED IN RED");
            trainerInfoLabel.setFont(new
                    Font(trainerInfoLabel.getFont().toString(), Font.PLAIN, 20));
            trainerInfoLabel.setForeground(Color.red);
            trainerInfoPanel.add(trainerInfoLabel);
            this.add(trainerInfoPanel, BorderLayout.SOUTH);
        }

        for (Course course : courses) {

            JPanel card = createCourseCard(course);

            //create conditional to outline cards in red if logged in trainer is teaching course
            if (User.getUserType() == UserType.TRAINER
                    && course.getInstructorName().equals(User.getUsername())) {
                Border roundedLineBorder = new LineBorder(Color.RED, 2, true);
                Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
                CompoundBorder compoundBorder =
                        new CompoundBorder(roundedLineBorder, paddingBorder);
                card.setBorder(compoundBorder);
            }

            // decide if course goes onto beginner or advanced tab
            if (course.isCourseAdvanced()) {

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
     * Helper to build the button panel for owner management actions.
     *
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

            // If manage instructor is clicked, open up instructor
            // manager window and pull in course object
            ManageInstructorPopup manageInstructorPopup
                    = new ManageInstructorPopup(ClassPanel.this, course);
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
     *
     * @param course course added
     * @return card infoPanel
     */
    private static JPanel buildInfoPanel(Course course) {

        JLabel instructorNameLabel;

        JLabel classNameLabel = new JLabel("Class: " + course.getClassName());
        if (course.getInstructorName() == null) {
            instructorNameLabel = new JLabel("Instructor: Not Assigned");
        } else {
            instructorNameLabel = new JLabel("Instructor: " + course.getInstructorName());
        }

        JLabel classSizeLabel = new JLabel("Size: "
                + course.getCurrentClassSize() + "/" + course.getMaxClassSize());

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