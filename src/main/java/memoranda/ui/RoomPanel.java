package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class RoomPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel imagePanel = new JPanel();
	JPanel tablePanel = new JPanel();
	JPanel desertImagePanel = new JPanel();
	JPanel jungleImagePanel = new JPanel();
	JPanel arcticImagePanel = new JPanel();
	JPanel desertTablePanel = new JPanel();
	JPanel jungleTablePanel = new JPanel();
	JPanel arcticTablePanel = new JPanel();
	JPanel desertTextPanel = new JPanel();
	JPanel jungleTextPanel = new JPanel();
	JPanel arcticTextPanel = new JPanel();
	JLabel desertImageLabel = new JLabel();
	JLabel jungleImageLabel = new JLabel();
	JLabel arcticImageLabel = new JLabel();
	JLabel desertTextLabel = new JLabel("Desert Room");
	JLabel jungleTextLabel = new JLabel("Jungle Room");
	JLabel arcticTextLabel = new JLabel("Arctic Room");
	Font textFont = new Font("Verdana", Font.PLAIN, 14);
	ImageIcon desertIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/desert.png"));
	ImageIcon jungleIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/jungle.png"));
	ImageIcon arcticIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/arctic.png"));
	
	String[] columnNames = {"Time", "Trainer", "Level", "Availability", "Sign Up"};
	Object[][] data = {{"10:00", "Dummy Trainer", "1", "20", "Sign-up Button"}, 
					   {"11:00", "Dummy Trainer 2", "2", "20", "Sign-up Button"},
					   {"12:00", "Dummy Trainer 3", "3", "20", "Sign-up Button"}};
	JTable desertTable = new JTable(data, columnNames);
	JTable jungleTable = new JTable();
	JTable arcticTable = new JTable();
	
	
	DailyItemsPanel parentPanel = null;
	
	public RoomPanel(DailyItemsPanel parent) {
		try {
			parentPanel = parent;
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
	
	void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		
		//divides the page between image portion and table portion and adds to main Panel
		imagePanel.setLayout(new GridLayout());
		tablePanel.setLayout(new GridLayout());
		this.add(imagePanel, BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
		imagePanel.setPreferredSize(new Dimension(300, 300));
		
		//divides image panel into 3 equal portions and adds the images
		imagePanel.add(desertImagePanel);
		imagePanel.add(jungleImagePanel);
		imagePanel.add(arcticImagePanel);
		desertImagePanel.setLayout(new GridBagLayout());
		jungleImagePanel.setLayout(new GridBagLayout());
		arcticImagePanel.setLayout(new GridBagLayout());
		desertImagePanel.add(desertImageLabel);
		jungleImagePanel.add(jungleImageLabel);
		arcticImagePanel.add(arcticImageLabel);
		desertImagePanel.setBackground(new Color(210, 85, 91, 100));
		jungleImagePanel.setBackground(new Color(42, 230, 138, 100));
		arcticImagePanel.setBackground(new Color(20, 190, 255, 100));
		desertImageLabel.setIcon(desertIcon);
		jungleImageLabel.setIcon(jungleIcon);
		arcticImageLabel.setIcon(arcticIcon);	
		
		//divides table panel into 3 equal portions
		tablePanel.add(desertTablePanel);
		tablePanel.add(jungleTablePanel);
		tablePanel.add(arcticTablePanel);
		desertTablePanel.setLayout(new BorderLayout());
		jungleTablePanel.setLayout(new BorderLayout());
		arcticTablePanel.setLayout(new BorderLayout());
		
		//Adds text portion at the top of table panel so Rooms can be named
		desertTablePanel.add(desertTextPanel, BorderLayout.NORTH);
		jungleTablePanel.add(jungleTextPanel, BorderLayout.NORTH);
		arcticTablePanel.add(arcticTextPanel, BorderLayout.NORTH);
		desertTextPanel.setLayout(new GridBagLayout());
		jungleTextPanel.setLayout(new GridBagLayout());
		arcticTextPanel.setLayout(new GridBagLayout());
		desertTextPanel.setBackground(new Color(210, 85, 91, 100));
		jungleTextPanel.setBackground(new Color(42, 230, 138, 100));
		arcticTextPanel.setBackground(new Color(20, 190, 255, 100));
		desertTextPanel.add(desertTextLabel);
		jungleTextPanel.add(jungleTextLabel);
		arcticTextPanel.add(arcticTextLabel);
		desertTextLabel.setFont(textFont);
		jungleTextLabel.setFont(textFont);
		arcticTextLabel.setFont(textFont);
		
		//Add table UI 
		desertTablePanel.add(new JScrollPane(desertTable));
		
	}
}