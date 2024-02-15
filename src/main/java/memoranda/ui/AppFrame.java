package main.java.memoranda.ui;

import main.java.memoranda.User;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Vector;


/**
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: AppFrame.java,v 1.33 2005/07/05 08:17:24 alexeya Exp $*/

public class AppFrame extends JFrame {

    JPanel contentPane;
    JMenuBar menuBar = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane splitPane = new JSplitPane();

    JMenu jMenuEdit = new JMenu();
    JMenu jMenuFormat = new JMenu();
    JMenu jMenuInsert = new JMenu();

    public WorkPanel workPanel = new WorkPanel();

    static Vector exitListeners = new Vector();


    // Minimize Action Method Located
    public Action minimizeAction = new AbstractAction("Close the window") {
        public void actionPerformed(ActionEvent e) {
            doMinimize();
        }
    };

    public Action preferencesAction = new AbstractAction("Preferences") {
        public void actionPerformed(ActionEvent e) {
            showPreferences();
        }
    };


    JMenuItem jMenuFileMin = new JMenuItem(minimizeAction);
    JMenuItem jMenuEditPref = new JMenuItem(preferencesAction);

    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpGuide = new JMenuItem();
    JMenuItem jMenuHelpWeb = new JMenuItem();
    JMenuItem jMenuHelpBug = new JMenuItem();
    JMenuItem jMenuHelpAbout = new JMenuItem();

    //Construct the frame
    public AppFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            new ExceptionDialog(e);
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setIconImage(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/jnotes16.png")))
                .getImage());
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);

        this.setTitle("Gymoranda - " + User.getUserType() + " - " + User.getUsername());
        //Added a space to App.VERSION_INFO to make it look some nicer
        statusBar.setText(" Version:" + App.VERSION_INFO + " (Build "
                + App.BUILD_INFO + " )");

        jMenuFile.setText(Local.getString("File"));
        jMenuFileExit.setText(Local.getString("Exit"));
        jMenuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });
        jMenuHelp.setText(Local.getString("Help"));

        jMenuHelpGuide.setText(Local.getString("Online user's guide"));
        jMenuHelpGuide.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/help.png"))));
        jMenuHelpGuide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpGuide_actionPerformed(e);
            }
        });

        jMenuHelpWeb.setText(Local.getString("Memoranda web site"));
        jMenuHelpWeb.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/web.png"))));
        jMenuHelpWeb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpWeb_actionPerformed(e);
            }
        });

        jMenuHelpBug.setText(Local.getString("Report a bug"));
        jMenuHelpBug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpBug_actionPerformed(e);
            }
        });

        jMenuHelpAbout.setText(Local.getString("About Gymoranda"));
        jMenuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpAbout_actionPerformed(e);
            }
        });


        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(3);

        splitPane.setDividerLocation(28);


        splitPane.setDividerLocation(28);





        jMenuFileMin.setText(Local.getString("Close the window"));
        jMenuFileMin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,
                InputEvent.ALT_MASK));

        jMenuFile.add(jMenuEditPref);

        jMenuFile.add(jMenuFileMin);

        jMenuFile.add(jMenuFileExit);

        jMenuHelp.add(jMenuHelpGuide);
        jMenuHelp.add(jMenuHelpWeb);
        jMenuHelp.add(jMenuHelpBug);
        jMenuHelp.addSeparator();
        jMenuHelp.add(jMenuHelpAbout);

        menuBar.add(jMenuFile);
        menuBar.add(jMenuHelp);
        this.setJMenuBar(menuBar);


        contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.add(workPanel, JSplitPane.BOTTOM);

        splitPane.setBorder(null);
        workPanel.setBorder(null);

        setEnabledEditorMenus(false);


        java.awt.event.ActionListener setMenusDisabled = new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEnabledEditorMenus(false);
            }
        };

        this.workPanel.dailyItemsPanel.taskB
                .addActionListener(setMenusDisabled);
        this.workPanel.dailyItemsPanel.alarmB.addActionListener(
                setMenusDisabled);

        this.workPanel.tasksB.addActionListener(setMenusDisabled);
        this.workPanel.eventsB.addActionListener(setMenusDisabled);
        this.workPanel.filesB.addActionListener(setMenusDisabled);
        this.workPanel.agendaB.addActionListener(setMenusDisabled);

        this.workPanel.notesB.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setEnabledEditorMenus(true);
                    }
                });

        Object fwo = Context.get("FRAME_WIDTH");
        Object fho = Context.get("FRAME_HEIGHT");
        if ((fwo != null) && (fho != null)) {
            int w = Integer.parseInt((String) fwo);
            int h = Integer.parseInt((String) fho);
            this.setSize(w, h);
        } else
            this.setExtendedState(Frame.MAXIMIZED_BOTH);

        Object xo = Context.get("FRAME_XPOS");
        Object yo = Context.get("FRAME_YPOS");
        if ((xo != null) && (yo != null)) {
            int x = Integer.parseInt((String) xo);
            int y = Integer.parseInt((String) yo);
            this.setLocation(x, y);
        }

        // Force Home to open on start-up
        // Conditional is no longer needed as pan cannot be NULL
        workPanel.selectPanel("AGENDA");


    }

    protected void jMenuHelpBug_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.BUGS_TRACKER_URL);
    }

    protected void jMenuHelpWeb_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.WEBSITE_URL);
    }

    protected void jMenuHelpGuide_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.GUIDE_URL);
    }

    //File | Exit action performed
    public void doExit() {
        if (Configuration.get("ASK_ON_EXIT").equals("yes")) {
            Dimension frmSize = this.getSize();
            Point loc = this.getLocation();

            ExitConfirmationDialog dlg = new ExitConfirmationDialog(this, Local.getString("Exit"));
            dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
            dlg.setVisible(true);
            if (dlg.CANCELLED) return;
        }

        Context.put("FRAME_WIDTH", this.getWidth());
        Context.put("FRAME_HEIGHT", this.getHeight());
        Context.put("FRAME_XPOS", this.getLocation().x);
        Context.put("FRAME_YPOS", this.getLocation().y);
        exitNotify();
        System.exit(0);
    }


    // Function to actually minimize the window
    public void doMinimize() {
        exitNotify();
        App.minimizeWindow();
    }

    public void doClose() {
        exitNotify();
        App.closeWindow();
    }

    //Help | About action performed
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
        AppFrame_AboutBox dlg = new AppFrame_AboutBox(this);
        Dimension dlgSize = dlg.getSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setVisible(true);
    }

    // Function to process WindowEvents, i.e. minimize
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (Configuration.get("ON_CLOSE").equals("exit"))
                doExit();
            else
                doClose();
        } else if ((e.getID() == WindowEvent.WINDOW_ICONIFIED)) {
            super.processWindowEvent(new WindowEvent(this,
                    WindowEvent.WINDOW_CLOSED));
            doMinimize();
        } else
            super.processWindowEvent(e);
    }

    public static void addExitListener(ActionListener al) {
        exitListeners.add(al);
    }


    private static void exitNotify() {
        for (int i = 0; i < exitListeners.size(); i++)
            ((ActionListener) exitListeners.get(i)).actionPerformed(null);
    }

    public void setEnabledEditorMenus(boolean enabled) {
        this.jMenuEdit.setEnabled(enabled);
        this.jMenuFormat.setEnabled(enabled);
        this.jMenuInsert.setEnabled(enabled);
    }

    public void showPreferences() {
        PreferencesDialog dlg = new PreferencesDialog(this);
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }



}