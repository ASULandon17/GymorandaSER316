package main.java.memoranda.ui;

// Swap placement of imports to fix CheckStyle error
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.AgendaGenerator;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/*$Id: AgendaPanel.java,v 1.11 2005/02/15 16:58:02 rawsushi Exp $*/
public class AgendaPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar toolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JEditorPane viewer = new JEditorPane("text/html", "");
    JScrollPane scrollPane = new JScrollPane();


    DailyItemsPanel parentPanel = null;

    Collection expandedTasks;
    String gotoTask = null;
    boolean isActive = true;

    public AgendaPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;

            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        expandedTasks = new ArrayList();

        toolBar.setFloatable(false);
        viewer.setEditable(false);
        viewer.setOpaque(false);
        viewer.addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {

                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    String d = e.getDescription();
                    // Removed unused code
                    if (d.startsWith("memoranda:changeBelt")) {
                        final JFrame parent = new JFrame();
                        Object[] options = BeltValue.values();
                        Object selectionObject = JOptionPane.showInputDialog(parent, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, User.getBeltRank());
                        // Nothing selected
                        if (selectionObject == null) {
                            return;
                        }

                        //todo: maybe ref to class panel here?

                        User.setBeltRank((BeltValue) selectionObject);
                        refresh(CurrentDate.get());
                        JOptionPane.showMessageDialog(null,Local.getString("Belt rank successfully changed to: " + selectionObject));

                    } else if (d.startsWith("memoranda:changeTraining")) {
                        final JFrame parent = new JFrame();
                        Object[] options = BeltValue.values();
                        Object selectionObject = JOptionPane.showInputDialog(parent, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, User.getTrainingRank());
                        // Nothing selected
                        if (selectionObject == null) {
                            return;
                        }

                        User.setTrainingRank((BeltValue) selectionObject);
                        refresh(CurrentDate.get());
                        JOptionPane.showMessageDialog(null,Local.getString("Training rank successfully changed to: " + selectionObject));

                    }

                }
            }
        });
        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");

        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);

        scrollPane.getViewport().add(viewer, null);
        this.add(scrollPane, BorderLayout.CENTER);
        toolBar.add(historyBackB, null);
        toolBar.add(historyForwardB, null);
        toolBar.addSeparator(new Dimension(8, 24));

        this.add(toolBar, BorderLayout.NORTH);

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                if (isActive) {
                    refresh(d);
                }
            }
        });
        CurrentProject.addProjectListener(new ProjectListener() {

            public void projectChange(
                    Project prj,
                    NoteList nl,
                    TaskList tl,
                    ResourcesList rl) {
            }

            public void projectWasChanged() {
                if (isActive) {
                    refresh(CurrentDate.get());
                }
            }
        });

        EventsScheduler.addListener(new EventNotificationListener() {
            public void eventIsOccured(main.java.memoranda.Event ev) {
                if (isActive) {
                    refresh(CurrentDate.get());
                }
            }

            public void eventsChanged() {
                if (isActive) {
                    refresh(CurrentDate.get());
                }
            }
        });
        refresh(CurrentDate.get());
    }

    public void refresh(CalendarDate date) {
        viewer.setText(AgendaGenerator.getAgenda(date,expandedTasks));
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (gotoTask != null) {
                    viewer.scrollToReference(gotoTask);
                    scrollPane.setViewportView(viewer);
                    Util.debug("Set view port to " + gotoTask);
                }
            }
        });

        Util.debug("Summary updated.");
    }

    public void setActive(boolean isa) {
        isActive = isa;
    }

}
