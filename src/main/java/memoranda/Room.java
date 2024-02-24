package main.java.memoranda;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Room class for individual rooms.
 */
public class Room {

    private boolean hasClass;
    private String roomName;
    private List<Integer> classIds;

    /**
     * Constructor to set a room up that has a class.
     *
     * @param hasClass does a room have a class assigned to it
     * @param roomName name of the room
     * @param classId  unique class id
     */
    public Room(boolean hasClass, String roomName, int classId) {
        this.hasClass = hasClass;
        this.roomName = roomName;
        this.classIds = new ArrayList<>();
        this.classIds.add(classId);
    }

    /**
     * Constructor to set a room up with no class, just a roomName.
     *
     * @param roomName name of the room
     */
    public Room(String roomName) {
        this.hasClass = false;
        this.roomName = roomName;
        this.classIds = new ArrayList<>();
    }


    /**
     * Constructor to set rooms from a JsonObject.
     *
     * @param jsonObject json object to build Room object from
     */
    public Room(JSONObject jsonObject) {
        this.roomName = jsonObject.getString("roomName");
        this.hasClass = jsonObject.getBoolean("hasClass");
        //this.classId = jsonObject.has("classId") ? jsonObject.getInt("classId") : null;
        if (jsonObject.has("classId")) {
            this.classIds = new ArrayList<>();
            JSONArray classIdList = jsonObject.getJSONArray("classId");
            for (int i = 0; i < classIdList.length(); i++) {
                this.classIds.add(classIdList.getInt(i));
            }
        }
    }

    public void setName(String roomName) {
        this.roomName = roomName;
    }

    public List<Integer> getClassIds() {
        return this.classIds;
    }

    public void addClassId(Integer classId) {
        this.classIds.add(classId);
    }

    public boolean getHasClass() {
        return this.hasClass;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setHasClass(boolean hasClass) {
        this.hasClass = hasClass;
    }
}