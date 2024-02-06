package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/*$Id: EventsPanel.java,v 1.25 2005/02/19 10:06:25 rawsushi Exp $*/
public class EventsPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    //JButton historyBackB = new JButton();
    JToolBar eventsToolBar = new JToolBar();
    //JButton historyForwardB = new JButton();
    //JButton newEventB = new JButton();
    //JButton editEventB = new JButton();
    //JButton removeEventB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    //ClassGrid eventsTable = new ClassGrid();
    //JPopupMenu eventPPMenu = new JPopupMenu();
    //JMenuItem ppEditEvent = new JMenuItem();
    //JMenuItem ppRemoveEvent = new JMenuItem();
    //JMenuItem ppNewEvent = new JMenuItem();
    //DailyItemsPanel parentPanel = null;

    JButton newClass = new JButton();

    public EventsPanel(DailyItemsPanel _parentPanel) {
        try {
            //parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInitHelper(JButton button) {
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setRequestFocusEnabled(false);
        button.setPreferredSize(new Dimension(24, 24));
        button.setMinimumSize(new Dimension(24, 24));
        button.setMaximumSize(new Dimension(24, 24));
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
       // historyBackB.setVisible(false);
        //historyForwardB.setVisible(false);
        //newEventB.setVisible(false);
        //editEventB.setVisible(false);
        //removeEventB.setVisible(false);
        //historyBackB.setAction(History.historyBackAction);
        //historyBackB.setToolTipText(Local.getString("History back"));
        //jbInitHelper(historyBackB);
        //historyBackB.setText("");

        //historyForwardB.setAction(History.historyForwardAction);
        //historyForwardB.setToolTipText(Local.getString("History forward"));
        //jbInitHelper(historyForwardB);
        //historyForwardB.setText("");

        //newEventB.setIcon(
        //    new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
        //newEventB.setEnabled(true);
        //jbInitHelper(newEventB);
        //newEventB.setToolTipText(Local.getString("New class"));

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

        //jbInitHelper(editEventB);
        //editEventB.setToolTipText(Local.getString("Edit event"));
        //editEventB.setEnabled(true);
        //editEventB.setIcon(
        //    new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));

       // jbInitHelper(removeEventB);
        //removeEventB.setToolTipText(Local.getString("Remove event"));
        //removeEventB.setIcon(
        //    new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
       // eventsTable.setMaximumSize(new Dimension(32767, 32767));
        //eventsTable.setRowHeight(24);
        //eventPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        //ppEditEvent.setFont(new java.awt.Font("Dialog", 1, 11));
       // ppEditEvent.setText(Local.getString("Edit event") + "...");
        //ppEditEvent.setEnabled(false);
        //ppEditEvent.setIcon(
        //    new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));
        //ppRemoveEvent.setFont(new java.awt.Font("Dialog", 1, 11));
       // ppRemoveEvent.setText(Local.getString("Remove event"));
        //ppRemoveEvent.setIcon(
        //    new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));
       // ppRemoveEvent.setEnabled(false);
        //ppNewEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        //ppNewEvent.setText(Local.getString("New event") + "...");

       // ppNewEvent.setIcon(
        //    new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
       // scrollPane.getViewport().add(eventsTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
       // eventsToolBar.add(historyBackB, null);
       // eventsToolBar.add(historyForwardB, null);
        eventsToolBar.addSeparator(new Dimension(8, 24));

        //eventsToolBar.add(newEventB, null);
       // eventsToolBar.add(removeEventB, null);
        eventsToolBar.addSeparator(new Dimension(8, 24));
       // eventsToolBar.add(editEventB, null);
        eventsToolBar.add(newClass, null);
        this.add(eventsToolBar, BorderLayout.NORTH);


}}