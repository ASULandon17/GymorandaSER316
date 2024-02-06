package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;

import main.java.memoranda.*;


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


    void newClassButtonHelper(JButton button){
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false); // Necessary for some Look and Feels to honor background color
        button.setOpaque(true); // Make the button paint its background
        button.setBackground(new Color(0, 100, 0)); // Dark green background
        button.setForeground(Color.WHITE); // White text
        button.setPreferredSize(new Dimension(100, 30)); // Set the preferred size to create a rectangle shape
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
                NewclassPopup popup = new NewclassPopup();
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
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JLabel instructorNameLabel;


        JLabel classNameLabel = new JLabel("Class: " + course.getClassName());
        if(course.getInstructorName().equals("")){
            instructorNameLabel = new JLabel("Instructor: Not Assigned");
        } else {
            instructorNameLabel = new JLabel("Instructor: " + course.getInstructorName());
        }

        JLabel classSizeLabel = new JLabel("Size: " + course.getCurrentClassSize() + "/" + course.getMaxClassSize());


        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(classNameLabel);
        infoPanel.add(instructorNameLabel);
        infoPanel.add(classSizeLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }
}