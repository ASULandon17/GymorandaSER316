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

    public static void addRoom(boolean hasClass, String roomName) {
        Room room = new Room(new JSONObject().put("hasClass", hasClass).put("roomName", roomName));
        rooms.add(room);
        saveRoomsToFile();
    }
    private static void saveRoomsToFile() {
        JSONArray jsonArray = new JSONArray();
        for (Room room : rooms) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hasClass", room.getHasClass());
            jsonObject.put("roomName", room.getRoomName()); // Ensure roomName is accessible, might need getter
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("Rooms.json")) {
            file.write(jsonArray.toString(4)); // Pretty print with an indent factor of 4
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void loadRoomsFromFile() {
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
