package main.java.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.Border;
import javax.swing.*;

import main.java.memoranda.EventsScheduler;
import main.java.memoranda.util.Configuration;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: App.java,v 1.28 2007/03/20 06:21:46 alexeya Exp $*/
public class App {
	// boolean packFrame = false;

	static AppFrame frame = null;
	
	public static final String GUIDE_URL = "http://memoranda.sourceforge.net/guide.html";
	public static final String BUGS_TRACKER_URL = "http://sourceforge.net/tracker/?group_id=90997&atid=595566";
	public static final String WEBSITE_URL = "http://memoranda.sourceforge.net";

	private JFrame splash = null;
	private JFrame login = null;

	/*========================================================================*/ 
	/* Note: Please DO NOT edit the version/build info manually!
       The actual values are substituted by the Ant build script using 
       'version' property and datestamp.*/

	public static final String VERSION_INFO = "@VERSION@";
	public static final String BUILD_INFO = "@BUILD@";
	
	/*========================================================================*/

	public static AppFrame getFrame() {
		return frame;
	}

	public void show() {
		if (frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		} else
			init();
	}

	public App(boolean fullmode) {
		super();
		if (fullmode)
			fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
		/* DEBUG */
		if (!fullmode)
			System.out.println("Minimized mode");
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			showSplash();
		System.out.println(VERSION_INFO);
		System.out.println(Configuration.get("LOOK_AND_FEEL"));
		try {
			if (Configuration.get("LOOK_AND_FEEL").equals("system"))
				UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
			else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
				UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());					
			else if (
				Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
				UIManager.setLookAndFeel(
					Configuration.get("LOOK_AND_FEEL").toString());

		} catch (Exception e) {		    
			new ExceptionDialog(e, "Error when initializing a pluggable look-and-feel. Default LF will be used.", "Make sure that specified look-and-feel library classes are on the CLASSPATH.");
		}
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
			String fdow;
			if (Calendar.getInstance().getFirstDayOfWeek() == 2)
				fdow = "mon";
			else
				fdow = "sun";
			Configuration.put("FIRST_DAY_OF_WEEK", fdow);
			Configuration.saveConfig();
			/* DEBUG */
			System.out.println("[DEBUG] first day of week is set to " + fdow);
		}

		EventsScheduler.init();
		frame = new AppFrame();
		//if (fullmode) {
		//	init();
		//}
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			splash.dispose();

		showLogin();
	}

	void init() {
		/*
		 * if (packFrame) { frame.pack(); } else { frame.validate(); }
		 * 
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 * 
		 * Dimension frameSize = frame.getSize(); if (frameSize.height >
		 * screenSize.height) { frameSize.height = screenSize.height; } if
		 * (frameSize.width > screenSize.width) { frameSize.width =
		 * screenSize.width; }
		 * 
		 * 
		 * Make the window fullscreen - On Request of users This seems not to
		 * work on sun's version 1.4.1_01 Works great with 1.4.2 !!! So update
		 * your J2RE or J2SDK.
		 */
		/* Used to maximize the screen if the JVM Version if 1.4 or higher */
		/* --------------------------------------------------------------- */
		double JVMVer =
			Double
				.valueOf(System.getProperty("java.version").substring(0, 3))
				.doubleValue();

		frame.pack();
		if (JVMVer >= 1.4) {
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		} else {
			frame.setExtendedState(Frame.NORMAL);
		}
		/* --------------------------------------------------------------- */
		/* Added By Jeremy Whitlock (jcscoobyrs) 07-Nov-2003 at 15:54:24 */

		// Not needed ???
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();

	}

	public static void minimizeWindow() {
		if (frame == null)
			return;
		//frame.setVisible(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void closeWindow() {
		if (frame == null)
			return;
		frame.dispose();
		System.exit(0);
	}

	/**
	 * Method showSplash.
	 */
	private void showSplash() {
		splash = new JFrame();
		ImageIcon spl =
			new ImageIcon(App.class.getResource("/ui/Gymoranda.png"));
		JLabel l = new JLabel();
		l.setSize(400, 300);
		l.setIcon(spl);
		splash.getContentPane().add(l);
		splash.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		splash.setLocation(
			(screenSize.width - 400) / 2,
			(screenSize.height - 300) / 2);
		splash.setUndecorated(true);
		splash.setVisible(true);
	}

	private void showLogin(){
		login = new JFrame("Login");
        login.setLayout(new FlowLayout()); // Simple layout, adjust as needed
        login.setSize(400, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        login.setLocation((screenSize.width - 400) / 2, (screenSize.height - 300) / 2);


		Border padding = BorderFactory.createEmptyBorder(10, 30, 10, 30);

		JPanel togglePanel = new JPanel(new FlowLayout());
		JToggleButton loginToggle = new JToggleButton("Login", true);
		JToggleButton signupToggle = new JToggleButton("Signup", false);
		ButtonGroup toggleGroup = new ButtonGroup();
		toggleGroup.add(loginToggle);
		toggleGroup.add(signupToggle);
		togglePanel.add(loginToggle);
		togglePanel.add(signupToggle);
	
		// CardLayout for switching between login and signup
		CardLayout cardLayout = new CardLayout();
		JPanel cardPanel = new JPanel(cardLayout);
	
		// Login panel
		JPanel loginPanel = new JPanel(new GridLayout(0, 1));
		loginPanel.setBorder(padding);
		JTextField loginUsername = new JTextField();
		JPasswordField loginPassword = new JPasswordField();
		JButton loginButton = new JButton("Login");
		loginPanel.add(new JLabel("Username:"));
		loginPanel.add(loginUsername);
		loginPanel.add(new JLabel("Password:"));
		loginPanel.add(loginPassword);
		loginPanel.add(loginButton);
	
		// Signup panel
		JPanel signupPanel = new JPanel(new GridLayout(0, 1));
		signupPanel.setBorder(padding);
		JTextField signupUsername = new JTextField();
		JPasswordField signupPassword = new JPasswordField();
		JComboBox<String> userType = new JComboBox<>(new String[]{"Member", "Trainer", "Owner"});
    	JButton signupButton = new JButton("Signup");
    	signupPanel.add(new JLabel("Username:"));
    	signupPanel.add(signupUsername);
    	signupPanel.add(new JLabel("Password:"));
    	signupPanel.add(signupPassword);
    	signupPanel.add(new JLabel("User Type:"));
    	signupPanel.add(userType);
    	signupPanel.add(signupButton);
	
		// Add panels to CardLayout
		cardPanel.add(loginPanel, "Login");
		cardPanel.add(signupPanel, "Signup");
	
		// Toggle action listeners
		loginToggle.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
		signupToggle.addActionListener(e -> cardLayout.show(cardPanel, "Signup"));
	
		// Login and Signup actions
		loginButton.addActionListener(e -> {
			// Perform login operation
			init(); // For simplicity, directly calling init(); Implement actual login logic here
			login.dispose();
		});
	
		signupButton.addActionListener(e -> {
			// Implement actual signup logic here
			init();
			login.dispose();
		});
	
		login.setLayout(new BorderLayout());
		login.add(togglePanel, BorderLayout.NORTH);
		login.add(cardPanel, BorderLayout.CENTER);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);

	}
}