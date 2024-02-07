package main.java.memoranda.ui;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.ui.DailyItemsPanel;
import main.java.memoranda.PersistentClass;
import main.java.memoranda.Course;
import main.java.memoranda.Rooms;
import main.java.memoranda.Room;

public class RoomPanel extends JPanel {
	static final int ROWS = 12;
	static final int COLUMNS = 6;
	CalendarDate currentDate;
	
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel imagePanel = new JPanel();
	JPanel tablePanel = new JPanel();
	JPanel desertImagePanel = new JPanel();
	JPanel jungleImagePanel = new JPanel();
	JPanel arcticImagePanel = new JPanel();
	JPanel beachImagePanel = new JPanel();
	JPanel desertTablePanel = new JPanel();
	JPanel jungleTablePanel = new JPanel();
	JPanel arcticTablePanel = new JPanel();
	JPanel beachTablePanel = new JPanel();
	JPanel desertTextPanel = new JPanel();
	JPanel jungleTextPanel = new JPanel();
	JPanel arcticTextPanel = new JPanel();
	JPanel beachTextPanel = new JPanel();
	JLabel desertImageLabel = new JLabel();
	JLabel jungleImageLabel = new JLabel();
	JLabel arcticImageLabel = new JLabel();
	JLabel beachImageLabel = new JLabel();
	JLabel desertTextLabel = new JLabel("Desert Room");
	JLabel jungleTextLabel = new JLabel("Jungle Room");
	JLabel arcticTextLabel = new JLabel("Arctic Room");
	JLabel beachTextLabel = new JLabel("Beach Room");
	Font textFont = new Font("Verdana", Font.PLAIN, 14);
	ImageIcon desertIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/desert.png"));
	ImageIcon jungleIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/jungle.png"));
	ImageIcon arcticIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/arctic.png"));
	ImageIcon beachIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/beach.png"));
	
	String[] columnNames = {"Time", "Class Name", "Trainer", "Availability", "Sign Up"};
	Object[][] desertData = {{"10:00", "Beginner Jiu-Jitsu", "Dummy Trainer", "1", "20", "Sign-up Button"}, 
					   		{"11:00", "Advanced Karate", "Dummy Trainer 2", "2", "20", "Sign-up Button"},
					   		{"12:00", "Akido", "Dummy Trainer 3", "3", "20", "Sign-up Button"}};
	//Object[][] desertData = getTableData(currentDate.get(), "Desert");
	Object[][] jungleData = {{"10:00", "Kids Class", "Dummy Trainer", "1", "20", "Sign-up Button"}, 
			   				{"11:00", "Kickboxing", "Dummy Trainer 2", "2", "20", "Sign-up Button"},
			   				{"12:00", "Krav Maga", "Dummy Trainer 3", "3", "20", "Sign-up Button"}};
	Object[][] arcticData = {{"10:00", "Muay Thai", "Dummy Trainer", "1", "20", "Sign-up Button"}, 
			   				{"11:00", "Judo", "Dummy Trainer 2", "2", "20", "Sign-up Button"},
			   				{"12:00", "Meditation", "Dummy Trainer 3", "3", "20", "Sign-up Button"}};
	Object[][] beachData = {{"10:00", "Muay Thai", "Dummy Trainer", "1", "20", "Sign-up Button"}, 
							{"11:00", "Judo", "Dummy Trainer 2", "2", "20", "Sign-up Button"},
							{"12:00", "Meditation", "Dummy Trainer 3", "3", "20", "Sign-up Button"}};
	JTable desertTable = new JTable(desertData, columnNames);
	JTable jungleTable = new JTable(jungleData, columnNames);
	JTable arcticTable = new JTable(arcticData, columnNames);
	JTable beachTable = new JTable(arcticData, columnNames);
	
	
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
		imagePanel.setPreferredSize(new Dimension(250, 250));
		
		//divides image panel into 3 equal portions and adds the images
		imagePanel.add(desertImagePanel);
		imagePanel.add(jungleImagePanel);
		imagePanel.add(arcticImagePanel);
		imagePanel.add(beachImagePanel);
		desertImagePanel.setLayout(new GridBagLayout());
		jungleImagePanel.setLayout(new GridBagLayout());
		arcticImagePanel.setLayout(new GridBagLayout());
		beachImagePanel.setLayout(new GridBagLayout());
		desertImagePanel.add(desertImageLabel);
		jungleImagePanel.add(jungleImageLabel);
		arcticImagePanel.add(arcticImageLabel);
		beachImagePanel.add(beachImageLabel);
		desertImagePanel.setBackground(new Color(210, 85, 91, 100));
		jungleImagePanel.setBackground(new Color(42, 230, 138, 100));
		arcticImagePanel.setBackground(new Color(20, 190, 255, 100));
		beachImagePanel.setBackground(new Color(248, 240, 164, 100));
		desertImageLabel.setIcon(desertIcon);
		jungleImageLabel.setIcon(jungleIcon);
		arcticImageLabel.setIcon(arcticIcon);	
		beachImageLabel.setIcon(beachIcon);
		
		//divides table panel into 3 equal portions
		tablePanel.add(desertTablePanel);
		tablePanel.add(jungleTablePanel);
		tablePanel.add(arcticTablePanel);
		tablePanel.add(beachTablePanel);
		desertTablePanel.setLayout(new BorderLayout());
		jungleTablePanel.setLayout(new BorderLayout());
		arcticTablePanel.setLayout(new BorderLayout());
		beachTablePanel.setLayout(new BorderLayout());
		
		//Adds text portion at the top of table panel so Rooms can be named
		desertTablePanel.add(desertTextPanel, BorderLayout.NORTH);
		jungleTablePanel.add(jungleTextPanel, BorderLayout.NORTH);
		arcticTablePanel.add(arcticTextPanel, BorderLayout.NORTH);
		beachTablePanel.add(beachTextPanel, BorderLayout.NORTH);
		desertTextPanel.setLayout(new GridBagLayout());
		jungleTextPanel.setLayout(new GridBagLayout());
		arcticTextPanel.setLayout(new GridBagLayout());
		beachTextPanel.setLayout(new GridBagLayout());
		desertTextPanel.setBackground(new Color(210, 85, 91, 100));
		jungleTextPanel.setBackground(new Color(42, 230, 138, 100));
		arcticTextPanel.setBackground(new Color(20, 190, 255, 100));
		beachTextPanel.setBackground(new Color(248, 240, 164, 100));
		desertTextPanel.add(desertTextLabel);
		jungleTextPanel.add(jungleTextLabel);
		arcticTextPanel.add(arcticTextLabel);
		beachTextPanel.add(beachTextLabel);
		desertTextLabel.setFont(textFont);
		jungleTextLabel.setFont(textFont);
		arcticTextLabel.setFont(textFont);
		beachTextLabel.setFont(textFont);
		
		//Add table UI 
		desertTablePanel.add(new JScrollPane(desertTable));
		jungleTablePanel.add(new JScrollPane(jungleTable));
		arcticTablePanel.add(new JScrollPane(arcticTable));
		beachTablePanel.add(new JScrollPane(beachTable));
	}
	
	/**
	 * This method gathers the data in order to create a table of classes for the day for a room.
	 * @param date
	 * @param roomName
	 * @return String[][] of data that fills out the table
	 */
	
	  Object[][] getTableData(CalendarDate date, String roomName) {
		  Room room = Rooms.getRoomByName(roomName);
		  if(room.equals(null)) {
			  System.out.println("Error: No Room by this name");  //create error catching when done w/method & debugging
			  return null;
		  }
		  Object[][] tableData = new Object[ROWS][COLUMNS];
		  List<Integer> classList = room.getClassIds();
		  
		  //parse through each Id in class list, check if it the same date as selected on the calendar, update it in the correct slot on table
		  for(Integer id : classList) {
			  Course course = PersistentClass.getCourseById((int) id);
			  String courseDate = "" + Integer.toString(course.getClassDay()) + "/" + 
			                           Integer.toString(course.getClassMonth()) + "/" + 
			                           Integer.toString(course.getClassYear());
			  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  if(courseDate == sdf.format(new CurrentDate().get())) {
				  //send the class to helper function to fill out it's row in the table
				  tableData = fillOutRow(tableData, course.getClassHour() - 8, course.getClassHour(), course.getClassName(), 
				          course.getInstructorName(), course.getSpotsRemaining());
				  //if the class is over an hour, fill out all of the time slots it takes up
				  if(course.getClassLength() > 1) {
					  for(int i = 1; i < course.getClassLength(); i++) {
						  tableData = fillOutRow(tableData, course.getClassHour() - 8 + i, course.getClassHour(), course.getClassName(), 
						          course.getInstructorName(), course.getSpotsRemaining());
					  }
				  }
			  }
			  // fill out empty spots in the schedule with "No class"
			  for(int i = 0; i < ROWS; i++) {
				  if(tableData[i][0].isEmpty()) {
					  for(int j = 0; j < COLUMNS; j++) {
						  tableData[i][j] = "No class";
					  }
				  }
			  }
		  }
		  return tableData;
	  }
	  
	  /**
	   * A method to fill out each row of a table for a room which corresponds to 1 hour of a class
	   * @param table
	   * @param row
	   * @param time
	   * @param className
	   * @param instructor
	   * @param level
	   * @param spotsRemaining
	   * @return table; the table with the row filled out
	   */
	  
	  Object[][] fillOutRow(Object[][] table, int row, int time, String className, String instructor, int spotsRemaining) {
		  table[row][0] = Integer.toString(time);
		  table[row][1] = className;
		  table[row][2] = instructor;
		  table[row][4] = Integer.toString(spotsRemaining);
		  if(spotsRemaining != 0) {
			  table[row][5] = createSignUpButton();
		  } else {
			  table[row][5] = "Class if full!";
		  }
		  return table;
	  }
	  
	  //createSignUpButton()
	  
}