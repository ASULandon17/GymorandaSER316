package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import main.java.memoranda.*;

/*$Id: EventsPanel.java,v 1.25 2005/02/19 10:06:25 rawsushi Exp $*/
public class ClassPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar eventsToolBar = new JToolBar();
    JScrollPane scrollPane = new JScrollPane();

    JButton newClass = new JButton();

    public ClassPanel(DailyItemsPanel _parentPanel) {
        try {
            //parentPanel = _parentPanel;
            jbInit();
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


}}