package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import main.java.memoranda.Room;
import main.java.memoranda.Rooms;
import main.java.memoranda.User;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.ui.DailyItemsPanel;


public class RoomPanel extends JPanel {
    static final int ROWS = 12;
    static final int COLUMNS = 5;
    static final int ROW_HEIGHT = 30;
    
    //used for main area set up
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel imagePanel = new JPanel();
    private JPanel tablePanel = new JPanel();
    
    //used for image area
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
    
    //used for sign up button area
    private JPanel classSignUp = new JPanel();
    private JPanel centerPanelSignUp = new JPanel();
    private JTextField signUpIdField = new JTextField();
    private JButton signUpButton = new JButton("Sign Up");
    
    //used for tables
    private Object[] columnNames = {"Time", "ID", "Class Name", "Trainer", "Availability"};
    private Object[][] desertData = getTableData(CurrentDate.get(), "Desert");
    private Object[][] jungleData = getTableData(CurrentDate.get(), "Jungle");
    private Object[][] arcticData = getTableData(CurrentDate.get(), "Arctic");
    private Object[][] beachData = getTableData(CurrentDate.get(), "Beach"); 
    
    private DefaultTableModel desertDt = new DefaultTableModel(desertData, columnNames);
    private DefaultTableModel jungleDt = new DefaultTableModel(jungleData, columnNames);
    private DefaultTableModel arcticDt = new DefaultTableModel(arcticData, columnNames);
    private DefaultTableModel beachDt = new DefaultTableModel(beachData, columnNames);
    
    private JTable desertTableM = new JTable(desertDt);
    private JTable jungleTableM = new JTable(jungleDt);
    private JTable arcticTableM = new JTable(arcticDt);
    private JTable beachTableM = new JTable(beachDt);
    
    private DailyItemsPanel parentPanel = null;
    
    /**
     * Constructor method for RoomPanel.
     * @param parent parent panel that can be passed in
     */
    public RoomPanel(DailyItemsPanel parent) {
        try {
            parentPanel = parent;
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    /**
     * Method to initiate panel creation upon first call of class.
     * @throws Exception If exception is found it is thrown
     */
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
        centerPanelSignUp.setBorder(new EmptyBorder(10, 10, 10, 10));
        centerPanelSignUp.setLayout(new GridLayout(1, 3, 10, 10));
        centerPanelSignUp.add(new JLabel("Class Id: "));
        centerPanelSignUp.add(signUpIdField);
        centerPanelSignUp.add(signUpButton);
        classSignUp.setLayout(new GridBagLayout());
        classSignUp.add(centerPanelSignUp);
        this.add(classSignUp, BorderLayout.SOUTH);
        
        /**
         * Listener for when the sign up button is hit
         */
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUserUp();
                updateTables();
                signUpIdField.setText("");
            }
        });
        
        /**
         * Listener for when the calendar date is changed.
         */
        CurrentDate.addDateListener(new DateListener() {
            @Override
            public void dateChange(CalendarDate d) {
                    
                //refresh tables for the given date
                updateTables();
            }
        });
    }
    
    /**
     * A method for updating the tables when a change has occurred.
     */
    public void updateTables() {
        
        Object[][] desertData = getTableData(CurrentDate.get(), "Desert");
        Object[][] jungleData = getTableData(CurrentDate.get(), "Jungle");
        Object[][] arcticData = getTableData(CurrentDate.get(), "Arctic");
        Object[][] beachData = getTableData(CurrentDate.get(), "Beach");  
        
        desertDt.setDataVector(desertData, columnNames); 
        jungleDt.setDataVector(jungleData, columnNames);
        arcticDt.setDataVector(arcticData, columnNames);
        beachDt.setDataVector(beachData, columnNames);
        
        desertDt.fireTableDataChanged();
        jungleDt.fireTableDataChanged();
        arcticDt.fireTableDataChanged();
        beachDt.fireTableDataChanged();
    }
    
    /**
    * This method gathers the data in order to create a table of classes for the day for a room.
    * @param date date of room scheduled being populated
    * @param roomName Name of the room being filled out
    * @return String[][] of data that fills out the table
    */
    public Object[][] getTableData(CalendarDate date, String roomName) {
        //Grab room that table is for
        Room room = Rooms.getRoomByName(roomName);
        //set up empty tableData object
        Object[][] tableData = new Object[ROWS][COLUMNS];
        // conditional to catch if there is no room so it will skip to filling out with no class
        if (room == null) {
            System.out.println("No Room named: " + roomName);
        } else {
            //gather all classes in the room
            List<Integer> classList = room.getClassIds();
            if (classList != null) {
                //parse through each Id in class list, check if it the same date 
                //as selected on the calendar, update it in the correct slot on table
                for (Integer id : classList) {
                    //get the course by id
                    Course course = PersistentClass.getCourseById((int) id);
                    //format class date so it can be compared
                    CalendarDate formattedDate = new CalendarDate(course.getClassDay(),
                        course.getClassMonth() - 1, course.getClassYear());
                    //check if the date on the calendar is the same as the date of the class
                    if (formattedDate.equals(date)) {
                        //send the class to helper function to fill out it's row in the table
                        tableData = fillOutRow(tableData, course.getClassHour() - 8,
                                  course.getClassHour(), course.getClassName(), 
                                  course.getInstructorName(),
                                  course.getMaxClassSize() - course.getCurrentClassSize(),
                                  course.getClassId(),
                                  course.isStudentRegistered(User.getUsername()));
                        //if the class is over an hour, fill out all of the time slots it takes up
                        if (course.getClassLength() > 1) {
                            for (int i = 1; i < course.getClassLength(); i++) {
                                tableData = fillOutRow(tableData, course.getClassHour() - 8 + i,
                                      course.getClassHour() + i, 
                                      course.getClassName(), 
                                      course.getInstructorName(), 
                                      course.getMaxClassSize() - course.getCurrentClassSize(), 
                                      course.getClassId(),
                                      course.isStudentRegistered(User.getUsername()));
                            }
                        }
                    }
                }
            } else {
                System.out.println("No classes for " + roomName + " found");
            }
        }
        // fill out empty spots in the schedule with "No Class"
        for (int i = 0; i < ROWS; i++) {
            if ((tableData[i][0]) == null) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (j == 0 && i <= 4) {
                        tableData[i][j] = "" + (i + 8) + ":00 AM";
                    } else if (j == 0) {
                        tableData[i][j] = "" + (i - 4) + ":00 PM";
                    } else {
                        tableData[i][j] = "No Class";
                    }
                }
            }
        }
        return tableData;
    }
  
    /**
     * A method for inserting data into a row of the table.
     * @param table object[][] to be updated with row of data
     * @param row The row in the table that data needs to be entered
     * @param hour The hour the class will be held at
     * @param className The name of the class in question
     * @param instructor The name of the instructor that will be teaching
     * @param spotsRemaining spots left until the class is full
     * @param id id of the class
     * @return table The object[][] that has now been updated w/row of data
     */
    public Object[][] fillOutRow(Object[][] table, int row, int hour, String className, 
            String instructor, int spotsRemaining, int id, boolean studentInClass) {
        //returns the table with nothing done if hour is outside gym hours
        if (hour < 8 || hour > 19) {
            return table;
        }
        //formats AM and PM correctly
        if (hour <= 12) {
            table[row][0] = "" + hour + ":00 AM";
        } else {
            table[row][0] = "" + (hour - 12) + ":00 PM";
        }
        //Add id to correct row
        table[row][1] = id;
        //Add class name to correct row
        table[row][2] = className;
        //Add instructor if one is present, default message if not
        if (instructor != "") {
            table[row][3] = instructor;
        } else {
            table[row][3] = "No instructor yet";
        }
        //Add correct number of spots remaining
        table[row][4] = studentInClass ? "Enrolled!" : Integer.toString(spotsRemaining);
        return table;
    }
    /**
     * A method for signing a user up for the input class when the signup button is hit.
     */
    
    public void signUserUp() {
        //get course user tried to sign up for
        int classId = Integer.parseInt(signUpIdField.getText());
        Course course = PersistentClass.getCourseById((int) classId);
        //if there's room in the class add the user
        try {
            if (course.getCurrentClassSize() < course.getMaxClassSize()) {
                PersistentClass.addStudentToCourse(User.getUsername(), classId);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "There is no course with the given Id", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
}