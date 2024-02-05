package main.java.memoranda;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Room class for individual rooms.
 * */
public class Room {

    private boolean hasClass;
    private String roomName;
    private List<Integer> classIds;

    /**
     * Constructor to set a room up that has a class.
     * @param hasClass
     * @param roomName
     * @param classId
     */
    public Room(boolean hasClass, String roomName, int classId) {
        this.hasClass = hasClass;
        this.roomName = roomName;
        this.classIds = new ArrayList<>();
        this.classIds.add(classId);
    }

    /**
     * Constructor to set a room up with no class, just a roomName.
     * @param roomName
     */
    public Room(String roomName){
        this.hasClass = false;
        this.roomName = roomName;
        this.classIds = new ArrayList<>();
    }


    /**
     * Constructor to set rooms from a JsonObject
     * @param jsonObject
     */
    public Room(JSONObject jsonObject){
        this.roomName = jsonObject.getString("roomName");
        this.hasClass = jsonObject.getBoolean("hasClass");
        //this.classId = jsonObject.has("classId") ? jsonObject.getInt("classId") : null;
        if(jsonObject.has("classId")) {
        	this.classIds = new ArrayList<>(Arrays.asList(jsonObject.getJSONArray("classId")));
        	
        }
    }

    public void setName(String roomName){
        this.roomName = roomName;
    }

    public List<Integer> getClassIds(){
        return this.classIds;
    }

    public void addClassId(Integer classId){
        this.classIds.add(classId);
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