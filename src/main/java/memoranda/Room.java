package main.java.memoranda;

import org.json.JSONObject;

public class Room {

    private boolean hasClass;
    private String roomName;

    // Constructor
    public Room(boolean isPublic, boolean hasClass, String roomName) {
        this.hasClass = hasClass;
        this.roomName = roomName;
    }


    public Room(JSONObject jsonObject){
        this.roomName = jsonObject.getString("roomName");
        this.hasClass = jsonObject.getBoolean("hasClass");
    }

    public void setName(String roomName){
        this.roomName = roomName;
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