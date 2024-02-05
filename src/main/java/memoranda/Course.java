package main.java.memoranda;

import org.json.JSONArray;
import org.json.JSONObject;


public class Course {

    private String className;
    private int classLength;
    private int maxClassSize;
    private int classID;
    private boolean isPublic;
    private JSONArray studentRoster;

    /**
     * Create  course from a JSON object.
     * @param courseJSON entry from classes.json
     */
    public Course(JSONObject courseJSON) {

        this.className = courseJSON.getString("className");
        this.classLength = courseJSON.getInt("classLength");
        this.maxClassSize = courseJSON.getInt("maxClassSize");
        this.classID = courseJSON.getInt("classID");
        this.isPublic = courseJSON.getBoolean("isPublic");
        this.studentRoster = courseJSON.getJSONArray("roster");

    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
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
