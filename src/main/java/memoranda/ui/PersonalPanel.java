package main.java.memoranda.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;


public class PersonalPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();



    public PersonalPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {

    }







    


}