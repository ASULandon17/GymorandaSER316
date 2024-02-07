package main.java.memoranda;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class provides course objects that encapsulate
 * the classes.json data to increase interoperability.
 */
public class Course {

    private String className;
    private String instructorName;
    private int classLength;
    private int maxClassSize;
    private int currentClassSize;
    private int classId;
    private int year;
    private int month;
    private int day;
    private int hour; //using 24hr clock format for hour

    private boolean isPublic;
    private final JSONArray roster;

    /**
     * Create  course from a JSON object.
     * @param courseJson entry from classes.json
     */
    public Course(JSONObject courseJson) {

        this.className = courseJson.getString("className");
        this.instructorName = courseJson.getString("instructorName");
        this.classLength = courseJson.getInt("classLength");
        this.maxClassSize = courseJson.getInt("maxClassSize");
        this.classId = courseJson.getInt("classID");
        this.isPublic = courseJson.getBoolean("isPublic");
        this.roster = courseJson.getJSONArray("roster");
        this.year = courseJson.getInt("year");
        this.month = courseJson.getInt("month");
        this.day = courseJson.getInt("day");
        this.hour = courseJson.getInt("hour");
        this.currentClassSize = 0;

    }

    /**
     * Constructor for Course if instructor is not known.
     * @param className name of class
     * @param classLength length of class in hours
     * @param maxClassSize max students
     * @param classId unique id
     * @param classIsPublic public or private class
     * @param year year of date class is scheduled
     * @param month month of date class is scheduled
     * @param day day of date class is scheduled
     * @param hour hour of date class is scheduled
     */
    public Course(String className, int classLength,
                  int maxClassSize, int classId, boolean classIsPublic,
                  int year, int month, int day, int hour) {

        this.className = className;
        this.classLength = classLength;
        this.maxClassSize = maxClassSize;
        this.currentClassSize = 0;
        this.classId = classId;
        this.isPublic = classIsPublic;
        this.instructorName = ""; // leave as empty string until updated by owner
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        
        this.roster = new JSONArray(); // no students yet
    }

    /**
     * Constructor for Course if instructor is known.
     * @param className name of class
     * @param classLength length of class in hours
     * @param maxClassSize max students
     * @param classId unique id
     * @param classIsPublic public or private class
     * @param instructorName instructor username
     * @param year year of date class is scheduled
     * @param month month of date class is scheduled
     * @param day day of date class is scheduled
     * @param hour hour of date class is scheduled
     */
    public Course(String className, int classLength, int maxClassSize, int classId,
                  boolean classIsPublic, String instructorName, 
                  int year, int month, int day, int hour) {

        this.className = className;
        this.classLength = classLength;
        this.maxClassSize = maxClassSize;
        this.currentClassSize = 0;
        this.classId = classId;
        this.isPublic = classIsPublic;
        this.instructorName = instructorName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        
        this.roster = new JSONArray(); // no students yet
    }

    /**
     * Helper method to see if a student is already on a class roster.
     * @param userName student username
     * @return true or false
     */
    public boolean isStudentRegistered(String userName) {

        for (int i = 0; i < roster.length(); i++) {

            if (roster.getJSONObject(i).getString("userName").equals(userName)) {
                return true; // student username is registered for course
            }
        }

        return false; // student not found in roster
    }


    /**
     * Provides functionality to add student to class roster.
     * @param username student username
     */
    public void addStudentToRoster(String username) {

        // make sure student isn't already on roster
        if (!isStudentRegistered(username)) {

            roster.put(new JSONObject().put("userName", username));
            currentClassSize++;


        } else {
            System.out.println("Student is already on the roster");
        }
    }

    /**
     * Provides functionality to remove student from a class roster.
     * @param userName username of student
     */
    public void removeStudentFromRoster(String userName) {

        if (isStudentRegistered(userName)) {

            for (int i = 0; i < roster.length(); i++) {

                // find the student index and remove them
                if (roster.getJSONObject(i).getString("userName").equals(userName)) {
                    roster.remove(i);
                    currentClassSize--;
                    return;
                }
            }
        }

    }

    public JSONArray getRoster() {
        return this.roster;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public void setCurrentClassSize(int size) {
        this.currentClassSize = size;
    }

    public int getCurrentClassSize() {
        return this.currentClassSize;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorName() {
        return this.instructorName;
    }


    public void setClassLength(int classLength) {
        this.classLength = classLength;
    }

    public int getClassLength() {
        return this.classLength;
    }


    public void setMaxClassSize(int maxClassSize) {
        this.maxClassSize = maxClassSize;
    }

    public int getMaxClassSize() {
        return this.maxClassSize;
    }


    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getClassId() {
        return this.classId;
    }


    public void setPublic(boolean trueOrFalse) {

        this.isPublic = trueOrFalse;
    }

    public boolean getPublic() {
        return this.isPublic;
    }

    public int getClassYear() {
        return this.year;
    }
    
    public void setClassYear(int year) {
        this.year = year;
    }

    public int getClassMonth() {
        return this.month;
    }
    
    public void setClassMonth(int month) {
        this.month = month;
    }

    public int getClassDay() {
        return this.day;
    }
    
    public void setClassDay(int day) {
        this.day = day;
    }

    public int getClassHour() {
        return this.hour;
    }
    
    public void setClassHour(int hour) {
        this.hour = hour;
    }
}
