package main.java.memoranda;

import org.json.JSONObject;

/**
 * Room class for individual rooms.
 * */
public class Room {

    private boolean hasClass;
    private String roomName;
    private Integer classId;

    /**
     * Constructor to set a room up that has a class.
     * @param hasClass
     * @param roomName
     * @param classId
     */
    public Room(boolean hasClass, String roomName, int classId) {
        this.hasClass = hasClass;
        this.roomName = roomName;
        this.classId = classId;
    }

    /**
     * Constructor to set a room up with no class, just a roomName.
     * @param roomName
     */
    public Room(String roomName){
        this.hasClass = false;
        this.roomName = roomName;
        this.classId = null;
    }


    /**
     * Constructor to set rooms from a JsonObject
     * @param jsonObject
     */
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

    public void setClassId(Integer classId){
        this.classId = classId;
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