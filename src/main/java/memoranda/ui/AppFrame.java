package main.java.memoranda.ui;

import main.java.memoranda.History;
import main.java.memoranda.User;
import main.java.memoranda.ui.htmleditor.HTMLEditor;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
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
    HTMLEditor editor = workPanel.dailyItemsPanel.editorPanel.editor;

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



    JMenuItem jMenuFileNewNote = new JMenuItem(workPanel.dailyItemsPanel.editorPanel.newAction);



    JMenuItem jMenuFileExportNote = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.exportAction);
    JMenuItem jMenuFileMin = new JMenuItem(minimizeAction);


    JMenuItem jMenuEditUndo = new JMenuItem(editor.undoAction);
    JMenuItem jMenuEditRedo = new JMenuItem(editor.redoAction);
    JMenuItem jMenuEditCut = new JMenuItem(editor.cutAction);
    JMenuItem jMenuEditCopy = new JMenuItem(editor.copyAction);
    JMenuItem jMenuEditPaste = new JMenuItem(editor.pasteAction);
    JMenuItem jMenuEditPasteSpec = new JMenuItem(editor.stylePasteAction);
    JMenuItem jMenuEditSelectAll = new JMenuItem(editor.selectAllAction);
    JMenuItem jMenuEditFind = new JMenuItem(editor.findAction);

    JMenu jMenuGo = new JMenu();
    JMenuItem jMenuInsertImage = new JMenuItem(editor.imageAction);
    JMenuItem jMenuInsertTable = new JMenuItem(editor.tableAction);
    JMenuItem jMenuInsertLink = new JMenuItem(editor.linkAction);
    JMenu jMenuInsertList = new JMenu();
    JMenuItem jMenuInsertListUL = new JMenuItem(editor.ulAction);
    JMenuItem jMenuInsertListOL = new JMenuItem(editor.olAction);
    JMenuItem jMenuInsertBR = new JMenuItem(editor.breakAction);
    JMenuItem jMenuInsertHR = new JMenuItem(editor.insertHRAction);
    JMenuItem jMenuInsertChar = new JMenuItem(editor.insCharAction);
    JMenuItem jMenuInsertDate = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.insertDateAction);
    JMenuItem jMenuInsertTime = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.insertTimeAction);
    JMenuItem jMenuInsertFile = new JMenuItem(
            workPanel.dailyItemsPanel.editorPanel.importAction);

    JMenu jMenuFormatPStyle = new JMenu();
    JMenuItem jMenuFormatP = new JMenuItem(editor.new BlockAction(editor.T_P,
            ""));
    JMenuItem jMenuFormatH1 = new JMenuItem(editor.new BlockAction(editor.T_H1,
            ""));
    JMenuItem jMenuFormatH2 = new JMenuItem(editor.new BlockAction(editor.T_H2,
            ""));
    JMenuItem jMenuFormatH3 = new JMenuItem(editor.new BlockAction(editor.T_H3,
            ""));
    JMenuItem jMenuFormatH4 = new JMenuItem(editor.new BlockAction(editor.T_H4,
            ""));
    JMenuItem jMenuFormatH5 = new JMenuItem(editor.new BlockAction(editor.T_H5,
            ""));
    JMenuItem jMenuFormatH6 = new JMenuItem(editor.new BlockAction(editor.T_H6,
            ""));
    JMenuItem jMenuFormatPRE = new JMenuItem(editor.new BlockAction(
            editor.T_PRE, ""));
    JMenuItem jMenuFormatBLCQ = new JMenuItem(editor.new BlockAction(
            editor.T_BLOCKQ, ""));
    JMenu jjMenuFormatChStyle = new JMenu();
    JMenuItem jMenuFormatChNorm = new JMenuItem(editor.new InlineAction(
            editor.I_NORMAL, ""));
    JMenuItem jMenuFormatChEM = new JMenuItem(editor.new InlineAction(
            editor.I_EM, ""));
    JMenuItem jMenuFormatChSTRONG = new JMenuItem(editor.new InlineAction(
            editor.I_STRONG, ""));
    JMenuItem jMenuFormatChCODE = new JMenuItem(editor.new InlineAction(
            editor.I_CODE, ""));
    JMenuItem jMenuFormatChCite = new JMenuItem(editor.new InlineAction(
            editor.I_CITE, ""));
    JMenuItem jMenuFormatChSUP = new JMenuItem(editor.new InlineAction(
            editor.I_SUPERSCRIPT, ""));
    JMenuItem jMenuFormatChSUB = new JMenuItem(editor.new InlineAction(
            editor.I_SUBSCRIPT, ""));
    JMenuItem jMenuFormatChCustom = new JMenuItem(editor.new InlineAction(
            editor.I_CUSTOM, ""));
    JMenuItem jMenuFormatChB = new JMenuItem(editor.boldAction);
    JMenuItem jMenuFormatChI = new JMenuItem(editor.italicAction);
    JMenuItem jMenuFormatChU = new JMenuItem(editor.underAction);
    JMenu jMenuFormatAlign = new JMenu();
    JMenuItem jMenuFormatAlignL = new JMenuItem(editor.lAlignAction);
    JMenuItem jMenuFormatAlignC = new JMenuItem(editor.cAlignAction);
    JMenuItem jMenuFormatAlignR = new JMenuItem(editor.rAlignAction);
    JMenu jMenuFormatTable = new JMenu();
    JMenuItem jMenuFormatTableInsR = new JMenuItem(editor.insertTableRowAction);
    JMenuItem jMenuFormatTableInsC = new JMenuItem(editor.insertTableCellAction);
    JMenuItem jMenuFormatProperties = new JMenuItem(editor.propsAction);
    JMenuItem jMenuGoHBack = new JMenuItem(History.historyBackAction);
    JMenuItem jMenuGoFwd = new JMenuItem(History.historyForwardAction);

    JMenuItem jMenuGoDayBack = new JMenuItem(
            workPanel.dailyItemsPanel.calendar.dayBackAction);
    JMenuItem jMenuGoDayFwd = new JMenuItem(
            workPanel.dailyItemsPanel.calendar.dayForwardAction);
    JMenuItem jMenuGoToday = new JMenuItem(
            workPanel.dailyItemsPanel.calendar.todayAction);

    JMenuItem jMenuEditPref = new JMenuItem(preferencesAction);

    JMenu jMenuInsertSpecial = new JMenu();

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
        this.setIconImage(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/jnotes16.png"))
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
        jMenuHelpGuide.setIcon(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/help.png")));
        jMenuHelpGuide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpGuide_actionPerformed(e);
            }
        });

        jMenuHelpWeb.setText(Local.getString("Memoranda web site"));
        jMenuHelpWeb.setIcon(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/web.png")));
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


        jMenuFileExportNote.setText(Local.getString("Export current note")
                + "...");


        jMenuFileMin.setText(Local.getString("Close the window"));
        jMenuFileMin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,
                InputEvent.ALT_MASK));

        jMenuEdit.setText(Local.getString("Edit"));

        jMenuEditUndo.setText(Local.getString("Undo"));
        jMenuEditUndo.setToolTipText(Local.getString("Undo"));
        jMenuEditRedo.setText(Local.getString("Redo"));
        jMenuEditRedo.setToolTipText(Local.getString("Redo"));
        jMenuEditCut.setText(Local.getString("Cut"));
        jMenuEditCut.setToolTipText(Local.getString("Cut"));
        jMenuEditCopy.setText((String) Local.getString("Copy"));
        jMenuEditCopy.setToolTipText(Local.getString("Copy"));
        jMenuEditPaste.setText(Local.getString("Paste"));
        jMenuEditPaste.setToolTipText(Local.getString("Paste"));
        jMenuEditPasteSpec.setText(Local.getString("Paste special"));
        jMenuEditPasteSpec.setToolTipText(Local.getString("Paste special"));
        jMenuEditSelectAll.setText(Local.getString("Select all"));

        jMenuEditFind.setText(Local.getString("Find & replace") + "...");

        jMenuEditPref.setText(Local.getString("Preferences") + "...");

        jMenuInsert.setText(Local.getString("Insert"));

        jMenuInsertImage.setText(Local.getString("Image") + "...");
        jMenuInsertImage.setToolTipText(Local.getString("Insert Image"));
        jMenuInsertTable.setText(Local.getString("Table") + "...");
        jMenuInsertTable.setToolTipText(Local.getString("Insert Table"));
        jMenuInsertLink.setText(Local.getString("Hyperlink") + "...");
        jMenuInsertLink.setToolTipText(Local.getString("Insert Hyperlink"));
        jMenuInsertList.setText(Local.getString("List"));

        jMenuInsertListUL.setText(Local.getString("Unordered"));
        jMenuInsertListUL.setToolTipText(Local.getString("Insert Unordered"));
        jMenuInsertListOL.setText(Local.getString("Ordered"));

        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));

        jMenuInsertListOL.setToolTipText(Local.getString("Insert Ordered"));

        jMenuInsertChar.setText(Local.getString("Special character") + "...");
        jMenuInsertChar.setToolTipText(Local.getString(
                "Insert Special character"));
        jMenuInsertDate.setText(Local.getString("Current date"));
        jMenuInsertTime.setText(Local.getString("Current time"));
        jMenuInsertFile.setText(Local.getString("File") + "...");

        jMenuFormat.setText(Local.getString("Format"));
        jMenuFormatPStyle.setText(Local.getString("Paragraph style"));
        jMenuFormatP.setText(Local.getString("Paragraph"));
        jMenuFormatH1.setText(Local.getString("Header") + " 1");
        jMenuFormatH2.setText(Local.getString("Header") + " 2");
        jMenuFormatH3.setText(Local.getString("Header") + " 3");
        jMenuFormatH4.setText(Local.getString("Header") + " 4");
        jMenuFormatH5.setText(Local.getString("Header") + " 5");
        jMenuFormatH6.setText(Local.getString("Header") + " 6");
        jMenuFormatPRE.setText(Local.getString("Preformatted text"));
        jMenuFormatBLCQ.setText(Local.getString("Blockquote"));
        jjMenuFormatChStyle.setText(Local.getString("Character style"));
        jMenuFormatChNorm.setText(Local.getString("Normal"));
        jMenuFormatChEM.setText(Local.getString("Emphasis"));
        jMenuFormatChSTRONG.setText(Local.getString("Strong"));
        jMenuFormatChCODE.setText(Local.getString("Code"));
        jMenuFormatChCite.setText(Local.getString("Cite"));
        jMenuFormatChSUP.setText(Local.getString("Superscript"));
        jMenuFormatChSUB.setText(Local.getString("Subscript"));
        jMenuFormatChCustom.setText(Local.getString("Custom style") + "...");
        jMenuFormatChB.setText(Local.getString("Bold"));
        jMenuFormatChB.setToolTipText(Local.getString("Bold"));
        jMenuFormatChI.setText(Local.getString("Italic"));
        jMenuFormatChI.setToolTipText(Local.getString("Italic"));
        jMenuFormatChU.setText(Local.getString("Underline"));
        jMenuFormatChU.setToolTipText(Local.getString("Underline"));
        jMenuFormatAlign.setText(Local.getString("Alignment"));
        jMenuFormatAlignL.setText(Local.getString("Left"));
        jMenuFormatAlignL.setToolTipText(Local.getString("Left"));
        jMenuFormatAlignC.setText(Local.getString("Center"));
        jMenuFormatAlignC.setToolTipText(Local.getString("Center"));
        jMenuFormatAlignR.setText(Local.getString("Right"));
        jMenuFormatAlignR.setToolTipText(Local.getString("Right"));
        jMenuFormatTable.setText(Local.getString("Table"));
        jMenuFormatTableInsR.setText(Local.getString("Insert row"));
        jMenuFormatTableInsC.setText(Local.getString("Insert cell"));
        jMenuFormatProperties.setText(Local.getString("Object properties")
                + "...");
        jMenuFormatProperties.setToolTipText(Local.getString(
                "Object properties"));

        jMenuGo.setText(Local.getString("Go"));
        jMenuGoHBack.setText(Local.getString("History back"));
        jMenuGoHBack.setToolTipText(Local.getString("History back"));
        jMenuGoFwd.setText(Local.getString("History forward"));
        jMenuGoFwd.setToolTipText(Local.getString("History forward"));
        jMenuGoDayBack.setText(Local.getString("One day back"));
        jMenuGoDayFwd.setText(Local.getString("One day forward"));
        jMenuGoToday.setText(Local.getString("To today"));

        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertBR.setToolTipText(Local.getString("Insert break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));
        jMenuInsertHR.setToolTipText(Local.getString("Insert Horizontal rule"));


        jMenuFile.add(jMenuFileNewNote);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExportNote);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuEditPref);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileMin);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExit);

        jMenuHelp.add(jMenuHelpGuide);
        jMenuHelp.add(jMenuHelpWeb);
        jMenuHelp.add(jMenuHelpBug);
        jMenuHelp.addSeparator();
        jMenuHelp.add(jMenuHelpAbout);

        menuBar.add(jMenuFile);
        menuBar.add(jMenuEdit);
        menuBar.add(jMenuInsert);
        menuBar.add(jMenuFormat);
        menuBar.add(jMenuGo);
        menuBar.add(jMenuHelp);
        this.setJMenuBar(menuBar);

        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.add(workPanel, JSplitPane.BOTTOM);
        jMenuEdit.add(jMenuEditUndo);
        jMenuEdit.add(jMenuEditRedo);
        jMenuEdit.addSeparator();
        jMenuEdit.add(jMenuEditCut);
        jMenuEdit.add(jMenuEditCopy);
        jMenuEdit.add(jMenuEditPaste);
        jMenuEdit.add(jMenuEditPasteSpec);
        jMenuEdit.addSeparator();
        jMenuEdit.add(jMenuEditSelectAll);
        jMenuEdit.addSeparator();
        jMenuEdit.add(jMenuEditFind);

        jMenuInsert.add(jMenuInsertImage);
        jMenuInsert.add(jMenuInsertTable);
        jMenuInsert.add(jMenuInsertLink);
        jMenuInsert.add(jMenuInsertList);

        jMenuInsertList.add(jMenuInsertListUL);
        jMenuInsertList.add(jMenuInsertListOL);
        jMenuInsert.addSeparator();
        jMenuInsert.add(jMenuInsertBR);
        jMenuInsert.add(jMenuInsertHR);
        jMenuInsert.add(jMenuInsertChar);
        jMenuInsert.addSeparator();
        jMenuInsert.add(jMenuInsertDate);
        jMenuInsert.add(jMenuInsertTime);
        jMenuInsert.addSeparator();
        jMenuInsert.add(jMenuInsertFile);

        jMenuFormat.add(jMenuFormatPStyle);
        jMenuFormat.add(jjMenuFormatChStyle);
        jMenuFormat.add(jMenuFormatAlign);
        jMenuFormat.addSeparator();
        jMenuFormat.add(jMenuFormatTable);
        jMenuFormat.addSeparator();
        jMenuFormat.add(jMenuFormatProperties);
        jMenuFormatPStyle.add(jMenuFormatP);
        jMenuFormatPStyle.addSeparator();
        jMenuFormatPStyle.add(jMenuFormatH1);
        jMenuFormatPStyle.add(jMenuFormatH2);
        jMenuFormatPStyle.add(jMenuFormatH3);
        jMenuFormatPStyle.add(jMenuFormatH4);
        jMenuFormatPStyle.add(jMenuFormatH5);
        jMenuFormatPStyle.add(jMenuFormatH6);
        jMenuFormatPStyle.addSeparator();
        jMenuFormatPStyle.add(jMenuFormatPRE);
        jMenuFormatPStyle.add(jMenuFormatBLCQ);
        jjMenuFormatChStyle.add(jMenuFormatChNorm);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChB);
        jjMenuFormatChStyle.add(jMenuFormatChI);
        jjMenuFormatChStyle.add(jMenuFormatChU);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChEM);
        jjMenuFormatChStyle.add(jMenuFormatChSTRONG);
        jjMenuFormatChStyle.add(jMenuFormatChCODE);
        jjMenuFormatChStyle.add(jMenuFormatChCite);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChSUP);
        jjMenuFormatChStyle.add(jMenuFormatChSUB);
        jjMenuFormatChStyle.addSeparator();
        jjMenuFormatChStyle.add(jMenuFormatChCustom);
        jMenuFormatAlign.add(jMenuFormatAlignL);
        jMenuFormatAlign.add(jMenuFormatAlignC);
        jMenuFormatAlign.add(jMenuFormatAlignR);
        jMenuFormatTable.add(jMenuFormatTableInsR);
        jMenuFormatTable.add(jMenuFormatTableInsC);
        jMenuGo.add(jMenuGoHBack);
        jMenuGo.add(jMenuGoFwd);
        jMenuGo.addSeparator();
        jMenuGo.add(jMenuGoDayBack);
        jMenuGo.add(jMenuGoDayFwd);
        jMenuGo.add(jMenuGoToday);

        splitPane.setBorder(null);
        workPanel.setBorder(null);

        setEnabledEditorMenus(true);


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

    public static Collection getExitListeners() {
        return exitListeners;
    }

    private static void exitNotify() {
        for (int i = 0; i < exitListeners.size(); i++)
            ((ActionListener) exitListeners.get(i)).actionPerformed(null);
    }

    public void setEnabledEditorMenus(boolean enabled) {
        this.jMenuEdit.setEnabled(enabled);
        this.jMenuFormat.setEnabled(enabled);
        this.jMenuInsert.setEnabled(enabled);
        this.jMenuFileNewNote.setEnabled(enabled);
        this.jMenuFileExportNote.setEnabled(enabled);
    }

    public void showPreferences() {
        PreferencesDialog dlg = new PreferencesDialog(this);
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }



}