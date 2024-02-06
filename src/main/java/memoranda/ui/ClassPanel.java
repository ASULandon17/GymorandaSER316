package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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

        Border roundedLineBorder = new LineBorder(Color.BLACK, 1, true);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        CompoundBorder compoundBorder = new CompoundBorder(roundedLineBorder, paddingBorder);
        card.setBorder(compoundBorder);
        JLabel instructorNameLabel;


        JLabel classNameLabel = new JLabel("Class: " + course.getClassName());
        if(course.getInstructorName().equals("")){
            instructorNameLabel = new JLabel("Instructor: Not Assigned");
        } else {
            instructorNameLabel = new JLabel("Instructor: " + course.getInstructorName());
        }

        JLabel classSizeLabel = new JLabel("Size: " + course.getCurrentClassSize() + "/" + course.getMaxClassSize());

        JLabel classLength = new JLabel("Length:" + course.getClassLength() + " hours.");
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));

        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        infoPanel.add(classNameLabel);
        infoPanel.add(instructorNameLabel);
        infoPanel.add(classSizeLabel);
        infoPanel.add(classLength);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }
}