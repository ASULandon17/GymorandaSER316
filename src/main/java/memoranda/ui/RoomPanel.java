package main.java.memoranda.ui;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
import main.java.memoranda.User;

public class RoomPanel extends JPanel {
	static final int ROWS = 12;
	static final int COLUMNS = 5;
	static final int ROW_HEIGHT = 30;
	
	private BorderLayout borderLayout1 = new BorderLayout();
	private JPanel imagePanel = new JPanel();
	private JPanel tablePanel = new JPanel();
	private JPanel desertImagePanel = new JPanel();
	private JPanel jungleImagePanel = new JPanel();
	private JPanel arcticImagePanel = new JPanel();
	private JPanel beachImagePanel = new JPanel();
	private JPanel desertTablePanel = new JPanel();
	private JPanel jungleTablePanel = new JPanel();
	private JPanel arcticTablePanel = new JPanel();
	private JPanel beachTablePanel = new JPanel();
	private JPanel desertTextPanel = new JPanel();
	private JPanel jungleTextPanel = new JPanel();
	private JPanel arcticTextPanel = new JPanel();
	private JPanel beachTextPanel = new JPanel();
	private JLabel desertImageLabel = new JLabel();
	private JLabel jungleImageLabel = new JLabel();
	private JLabel arcticImageLabel = new JLabel();
	private JLabel beachImageLabel = new JLabel();
	private JLabel desertTextLabel = new JLabel("Desert Room");
	private JLabel jungleTextLabel = new JLabel("Jungle Room");
	private JLabel arcticTextLabel = new JLabel("Arctic Room");
	private JLabel beachTextLabel = new JLabel("Beach Room");
	private Font textFont = new Font("Verdana", Font.PLAIN, 14);
	private ImageIcon desertIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/desert.png"));
	private ImageIcon jungleIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/jungle.png"));
	private ImageIcon arcticIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/arctic.png"));
	private ImageIcon beachIcon = new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/beach.png"));
	
	private JPanel classSignUp = new JPanel();
	private JTextField signUpIdField = new JTextField();
	private JButton signUpButton = new JButton("Sign Up");
	
	private Object[] columnNames = {"Time", "ID", "Class Name", "Trainer", "Availability"};
	
	Object[][] desertData = getTableData(CurrentDate.get(), "Desert");
    Object[][] jungleData = getTableData(CurrentDate.get(), "Jungle");
    Object[][] arcticData = getTableData(CurrentDate.get(), "Arctic");
    Object[][] beachData = getTableData(CurrentDate.get(), "Beach"); 
    
    DefaultTableModel desertDT = new DefaultTableModel(desertData, columnNames);
    DefaultTableModel jungleDT = new DefaultTableModel(jungleData, columnNames);
    DefaultTableModel arcticDT = new DefaultTableModel(arcticData, columnNames);
    DefaultTableModel beachDT = new DefaultTableModel(beachData, columnNames);
	
    JTable desertTableM = new JTable(desertDT);
    JTable jungleTableM = new JTable(jungleDT);
    JTable arcticTableM = new JTable(arcticDT);
    JTable beachTableM = new JTable(beachDT);
    
	private DailyItemsPanel parentPanel = null;
	
	public RoomPanel(DailyItemsPanel parent) {
		try {
			parentPanel = parent;
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
	
	public void jbInit() throws Exception {
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
		
		//add room tables below images 
        //set row height
        desertTableM.setRowHeight(ROW_HEIGHT);
        jungleTableM.setRowHeight(ROW_HEIGHT);
        arcticTableM.setRowHeight(ROW_HEIGHT);
        beachTableM.setRowHeight(ROW_HEIGHT);
        
        //Add table UI 
        desertTablePanel.add(new JScrollPane(desertTableM));
        jungleTablePanel.add(new JScrollPane(jungleTableM));
        arcticTablePanel.add(new JScrollPane(arcticTableM));
        beachTablePanel.add(new JScrollPane(beachTableM));
        
        //Add class SignUp
        classSignUp.setBorder(new EmptyBorder(10, 10, 10, 10));
        classSignUp.setLayout(new GridLayout(1, 3, 5, 5));
        classSignUp.add(new JLabel("Class Id: "));
        classSignUp.add(signUpIdField);
        classSignUp.add(signUpButton);
        signUpButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUserUp();
                updateTables();
            }
        });
        
        this.add(classSignUp, BorderLayout.SOUTH);
		
        //Create listener that changes date when new calendar day is picked
		CurrentDate.addDateListener(new DateListener() {
		    @Override
            public void dateChange(CalendarDate d) {
                	
            //refresh tables for the given date
		        updateTables();
            }
        });
	}
	
	public void createTables() {

        
	}
	
	public void updateTables() {
	    
	    Object[][] desertData = getTableData(CurrentDate.get(), "Desert");
        Object[][] jungleData = getTableData(CurrentDate.get(), "Jungle");
        Object[][] arcticData = getTableData(CurrentDate.get(), "Arctic");
        Object[][] beachData = getTableData(CurrentDate.get(), "Beach");  
        
        desertDT.setDataVector(desertData, columnNames); 
        jungleDT.setDataVector(jungleData, columnNames);
        arcticDT.setDataVector(arcticData, columnNames);
        beachDT.setDataVector(beachData, columnNames);
        
       desertDT.fireTableDataChanged();
       jungleDT.fireTableDataChanged();
       arcticDT.fireTableDataChanged();
       beachDT.fireTableDataChanged();
	}
	
	/**
	 * This method gathers the data in order to create a table of classes for the day for a room.
	 * @param date
	 * @param roomName
	 * @return String[][] of data that fills out the table
	 */
	  public Object[][] getTableData(CalendarDate date, String roomName) {
	      //Grab room that table is for
	      System.out.println("getTableData Ran");
		  Room room = Rooms.getRoomByName(roomName);
		  //set up empty tableData object
		  Object[][] tableData = new Object[ROWS][COLUMNS];
		  // conditional to catch if there is no room so it will skip to filling out with no class
		  if(room == null) {
			  System.out.println("Error: No Room by this name");
		  } else {
		      //gather all classes in the room
    		  List<Integer> classList = room.getClassIds();
    		  if (classList != null) {
        		  //parse through each Id in class list, check if it the same date as selected on the calendar, update it in the correct slot on table
        		  for(Integer id : classList) {
        		      //get the course by id
        			  Course course = PersistentClass.getCourseById((int) id);
        			  //format class date so it can be compared
        			  CalendarDate formattedDate = new CalendarDate(course.getClassDay(), course.getClassMonth() - 1, course.getClassYear());
        			  //check if the date on the calendar is the same as the date of the class
        			  if (formattedDate.equals(date)) {
        				  //send the class to helper function to fill out it's row in the table
        				  tableData = fillOutRow(tableData, course.getClassHour() - 8, course.getClassHour(), course.getClassName(), 
        				          course.getInstructorName(), course.getMaxClassSize() - course.getCurrentClassSize(), course.getClassId());
        				  //if the class is over an hour, fill out all of the time slots it takes up
        				  if(course.getClassLength() > 1) {
        					  for(int i = 1; i < course.getClassLength(); i++) {
        						  tableData = fillOutRow(tableData, course.getClassHour() - 8 + i, course.getClassHour() + i, course.getClassName(), 
        						          course.getInstructorName(), course.getMaxClassSize() - course.getCurrentClassSize(), course.getClassId());
        					  }
        				  }
        			  }
        		  }
    		  } else {
    		      System.out.println("No classes for " + roomName + " found");
    		  }
		  }
			  // fill out empty spots in the schedule with "No Class"
			  for(int i = 0; i < ROWS; i++) {
				  if ((tableData[i][0]) == null) {
					  for(int j = 0; j < COLUMNS; j++) {
					      if (j == 0 && i <= 4) {
					          tableData[i][j] = "" + (i+8) + ":00 AM";
					      } else if (j == 0) {
					          tableData[i][j] = "" + (i-4) + ":00 PM";
					      } else {
					          tableData[i][j] = "No Class";
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
	  public Object[][] fillOutRow(Object[][] table, int row, int hour, String className, String instructor, int spotsRemaining, int id) {
	      //returns the table with nothing done if the hour the class is schedule for is outside gym hours
	      if (hour < 8 || hour > 19) {
	          return table;
	      }
	      //formats AM and PM correctly
	      if (hour <= 12) {
	          table[row][0] = "" + hour + ":00 AM";
	      } else {
	          table[row][0] = "" + (hour-12) + ":00 PM";
	      }
	      //Add id to correct row
	      table[row][1] = id;
	      //Add classname to correct row
		  table[row][2] = className;
		  //Add instructor if one is present, default message if not
		  if (instructor != "") {
		      table[row][3] = instructor;
		  } else {
		      table[row][3] = "No instructor yet";
		  }
		  //Add correct number of spots remaining
		  table[row][4] = Integer.toString(spotsRemaining);
		  return table;
	  }
	  
	  public void signUserUp() {
	      //get course user tried to sign up for
	      int classId = Integer.parseInt(signUpIdField.getText());
	      Course course = PersistentClass.getCourseById((int) classId);
	      //if there's room in the class add the user
	      try {
	          if(course.getCurrentClassSize() < course.getMaxClassSize()) {
	              PersistentClass.addStudentToCourse(User.getUsername(), classId);
	          }
	      } catch (Exception ex){
	          JOptionPane.showMessageDialog(this, "There is no course with the given Id", "Input Error", JOptionPane.ERROR_MESSAGE);
	      }
	  }
	  
	  //get rid of this method that creates dummy rooms and classes after testing
	  public void addDataToClassesAndRoomsForTesting() {
	      Rooms.clearRooms();
	      PersistentClass.clearCourses();
	      Rooms.addRoom("Desert");
	      Rooms.addRoom("Jungle");
	      Rooms.addRoom("Arctic");
	      Rooms.addRoom("Beach");
	      PersistentClass.addNewClass("whale sharks", 2, 12, 60, true, 2024, 2, 7, 12);
	      PersistentClass.addNewClass("super Smash bros", 2, 10, 61, true, 2024, 2, 7, 14);
	      PersistentClass.addNewClass("Gyarados", 2, 10, 62, true, 2025, 2, 7, 14);
	      PersistentClass.addNewClass("Pikachu", 2, 14, 63, true, 2024, 2, 8, 16);
	      PersistentClass.addNewClass("blue fin tuna", 2, 10, 64, true, "craig", 2024, 2, 8, 14);
	      PersistentClass.addNewClass("Zapdos", 2, 10, 65, true, "craig", 2024, 3, 7, 14);
	      PersistentClass.addNewClass("Hitmonlee", 2, 30, 66, true, "Michael", 2024, 2, 7, 16);
	      PersistentClass.addNewClass("Hitmonchan", 2, 10, 67, true, "Michael", 2024, 2, 7, 8);
	      PersistentClass.addNewClass("Hitmontop", 1, 17, 68, true, "Michael", 2024, 2, 7, 10);
	      PersistentClass.addNewClass("Hitmontop 2", 1, 17, 69, true, "Sam", 2024, 2, 7, 10);
	      PersistentClass.addNewClass("charmander", 1, 40, 70, true, "Sam", 2024, 2, 7, 8);
	      Rooms.addClassToRoom("Desert", 60);
	      Rooms.addClassToRoom("Jungle", 61);
	      Rooms.addClassToRoom("Desert", 62);
	      Rooms.addClassToRoom("Desert", 63);
	      Rooms.addClassToRoom("Desert", 64);
	      Rooms.addClassToRoom("Desert", 65);
	      Rooms.addClassToRoom("Desert", 66);
	      Rooms.addClassToRoom("Desert", 67);
	      Rooms.addClassToRoom("Desert", 68);
	      Rooms.addClassToRoom("Beach", 69);
	      Rooms.addClassToRoom("Jungle", 70);
	  }
	  
	  //createSignUpButton()
	  
}