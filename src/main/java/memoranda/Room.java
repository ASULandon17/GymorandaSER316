package main.java.memoranda;

import org.json.JSONObject;

public class Room {

    private boolean hasClass;
    private String roomName;
    private Integer classId;

    // Constructor
    public Room(boolean hasClass, String roomName, int classId) {
        this.hasClass = hasClass;
        this.roomName = roomName;
        this.classId = classId;
    }

    /**
     * Function to add a room without a class
     */
    public Room(String roomName){
        this.hasClass = false;
        this.roomName = roomName;
        this.classId = null;
    }


    public Room(JSONObject jsonObject){
        this.roomName = jsonObject.getString("roomName");
        this.hasClass = jsonObject.getBoolean("hasClass");
        this.classId = jsonObject.has("classId") ? jsonObject.getInt("classId") : null;
    }

    public void setName(String roomName){
        this.roomName = roomName;
    }

    public Integer getClassId(){
        return this.classId;
    }

    public boolean getHasClass() {
        return this.hasClass;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public void setHasClass(boolean hasClass) {
        this.hasClass = hasClass;
    }
}