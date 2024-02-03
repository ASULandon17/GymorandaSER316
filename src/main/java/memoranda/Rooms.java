package main.java.memoranda;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Rooms {
    private static List<Room> rooms = new ArrayList<>();

    static {
        loadRoomsFromFile();
    }

    public static void clearRooms() {
        rooms.clear(); // Clear the in-memory list of rooms
        clearRoomsFile(); // Optionally clear the Rooms.json file
    }

    private static void clearRoomsFile() {
        try (FileWriter file = new FileWriter("Rooms.json")) {
            file.write(new JSONArray().toString()); // Write an empty array to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a room that has a class and classId
     * */
    public static void addRoom(boolean hasClass, String roomName, int classId) {
        rooms.add(new Room(hasClass, roomName, classId));
        saveRoomsToFile();
    }

    public static void addRoom( String roomName) {
        rooms.add(new Room(roomName));
        saveRoomsToFile();
    }


    private static void saveRoomsToFile() {
        JSONArray jsonArray = new JSONArray();
        for (Room room : rooms) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hasClass", room.getHasClass());
            jsonObject.put("roomName", room.getRoomName());
            // Only add classId if it is not null
            if (room.getClassId() != null) {
                jsonObject.put("classId", room.getClassId());
            }
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("Rooms.json")) {
            file.write(jsonArray.toString(4)); // Pretty print
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadRoomsFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("Rooms.json")));
            JSONArray jsonArray = new JSONArray(content);
            rooms.clear(); // Clear existing rooms before loading
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                rooms.add(new Room(jsonObject));
            }
        } catch (IOException e) {
            System.out.println("No existing Rooms.json found. A new one will be created.");
        }
    }

    public static List<Room> getRooms() {
        return new ArrayList<>(rooms); // Return a copy of the rooms list
    }
}
