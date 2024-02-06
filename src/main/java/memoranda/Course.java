package main.java.memoranda;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class provides course objects that encapsulate the classes.json data to increase interoperability.
 */
public class Course {

    private String className;
    private String instructorName;
    private int classLength;
    private int maxClassSize;
    private int currentClassSize;
    private int classID;

    private boolean isPublic;
    private final JSONArray roster;

    /**
     * Create  course from a JSON object.
     * @param courseJSON entry from classes.json
     */
    public Course(JSONObject courseJSON) {

        this.className = courseJSON.getString("className");
        this.instructorName = courseJSON.getString("instructorName");
        this.classLength = courseJSON.getInt("classLength");
        this.maxClassSize = courseJSON.getInt("maxClassSize");
        this.classID = courseJSON.getInt("classID");
        this.isPublic = courseJSON.getBoolean("isPublic");
        this.roster = courseJSON.getJSONArray("roster");
        this.currentClassSize = 0;

    }

    /**
     * Constructor for Course if instructor is not known.
     * @param className name of class
     * @param classLength length of class in hours
     * @param maxClassSize max students
     * @param classID unique id
     * @param classIsPublic public or private class
     */
    public Course(String className, int classLength, int maxClassSize, int classID, boolean classIsPublic) {

        this.className = className;
        this.classLength = classLength;
        this.maxClassSize = maxClassSize;
        this.currentClassSize = 0;
        this.classID = classID;
        this.isPublic = classIsPublic;
        this.instructorName = ""; // leave as empty string until updated by owner
        this.roster = new JSONArray(); // no students yet
    }

    /**
     * Constructor for Course if instructor is known.
     * @param className name of class
     * @param classLength length of class in hours
     * @param maxClassSize max students
     * @param classID unique id
     * @param classIsPublic public or private class
     * @param instructorName instructor username
     */
    public Course(String className, int classLength, int maxClassSize, int classID,
                  boolean classIsPublic, String instructorName) {

        this.className = className;
        this.classLength = classLength;
        this.maxClassSize = maxClassSize;
        this.currentClassSize = 0;
        this.classID = classID;
        this.isPublic = classIsPublic;
        this.instructorName = instructorName;
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

            for (int i = 0; i< roster.length(); i++) {

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

    public void setClassID(int classID) {
        this.classID = classID;
    }
    public int getClassID() {
        return this.classID;
    }

    public void setPublic(boolean trueOrFalse) {

        this.isPublic = trueOrFalse;
    }

    public boolean getPublic() {
        return this.isPublic;
    }





}
