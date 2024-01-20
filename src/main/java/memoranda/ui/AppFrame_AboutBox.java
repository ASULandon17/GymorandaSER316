package main.java.memoranda.ui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.java.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: AppFrame_AboutBox.java,v 1.13 2005/11/09 22:38:07 alexeya Exp $*/
public class AppFrame_AboutBox extends JDialog implements ActionListener {

  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel lblText = new JLabel();
  
  String product = "Version "+App.VERSION_INFO + " (Build " + App.BUILD_INFO + ")";
  String copyright = "Copyright (c) 2003, 2004 Memoranda team";
  String url = App.WEBSITE_URL;

  String[] others = {
                          
 };
    
  JLayeredPane layeredPane;
  ImageIcon image;
  JLabel imgLabel;
                                   
  public AppFrame_AboutBox(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    setSize(400, 300);
  }
  //Component initialization
  private void jbInit() throws Exception  {    
    
    
    image = new ImageIcon(AppFrame_AboutBox.class.getResource("/ui/Gymoranda.png"));
    this.setTitle(Local.getString("About Gymoranda"));
    setResizable(false);
    // Initialize Objects
    

    
    button1.setText(Local.getString("Ok"));
    button1.setBounds(150, 415, 95, 30);
    button1.addActionListener(this);
    button1.setPreferredSize(new Dimension(95, 30));
    button1.setBackground(new Color(69, 125, 186));
    button1.setForeground(Color.white);
    layeredPane = getLayeredPane();
    layeredPane.setPreferredSize(new Dimension(400, 300));
    imgLabel = new JLabel(image);
    imgLabel.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
    layeredPane.add(imgLabel, Integer.valueOf(1));
    layeredPane.add(lblText, Integer.valueOf(2));
    layeredPane.add(button1, Integer.valueOf(2));
    this.getContentPane().setBackground(new Color(251, 197, 63));
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }
  //Close the dialog
  void cancel() {
    dispose();
  }
  //Close the dialog on a button event
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
  }
}
