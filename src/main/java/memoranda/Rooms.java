package main.java.memoranda;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Rooms class that stores a list of the current available rooms.
 */
public class Rooms {
    private static final List<Room> rooms = new ArrayList<>();

    static {
        loadRoomsFromFile();
    }

    /**
     * Clears the List and file.
     */
    public static void clearRooms() {
        rooms.clear(); // Clear the in-memory list of rooms
        clearRoomsFile(); // Optionally clear the Rooms.json file
    }

    /**
     * Clears the Rooms.json file.
     */
    private static void clearRoomsFile() {
        try (FileWriter file = new FileWriter("Rooms.json")) {
            file.write(new JSONArray().toString()); // Write an empty array to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a room with a class in it.
     *
     * @param hasClass does the room have a class in it
     * @param roomName name of the room
     * @param classId unique class id
     */
    public static void addRoom(boolean hasClass, String roomName, int classId) {
        if (getRoomByName(roomName) == null) { // Check if the room name already exists
            rooms.add(new Room(hasClass, roomName, classId));
            saveRoomsToFile();
        } else {
            //probably want to change this down the line when we implement the UI.
            System.out.println("Room already exists.");
        }
    }

    /**
     * Add an empty room by roomName.
     *
     * @param roomName name of the room
     */
    public static void addRoom(String roomName) {
        if (getRoomByName(roomName) == null) { // Check if the room name already exists
            rooms.add(new Room(roomName));
            saveRoomsToFile();
        } else {
            //probably want to change this down the line when we implement the UI.
            System.out.println("Room already exists.");
        }
    }

    /**
     * Adds classId, and sets hasClass to true based on roomName and classId.
     *
     * @param roomName name of the room
     * @param classId unique class id
     */
    public static void addClassToRoom(String roomName, int classId) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                room.addClassId(classId); // Set the classId for the room
                room.setHasClass(true);
                saveRoomsToFile(); // Save changes to the JSON file
                break; // Exit the loop once the room is found and updated
            }
        }
    }

    /**
     * Gets back a single room when searching for it by roomName.
     *
     * @param roomName name of the room
     * @return Room object
     */
    public static Room getRoomByName(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                return room; // Return the matching room
            }
        }
        return null; // Return null if no matching room is found
    }

    /**
     * Saves the Room list to the json file.
     */
    private static void saveRoomsToFile() {
        JSONArray jsonArray = new JSONArray();
        for (Room room : rooms) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hasClass", room.getHasClass());
            jsonObject.put("roomName", room.getRoomName());
            // Only add classId if it is not null
            if (!room.getClassIds().isEmpty()) {
                jsonObject.put("classId", new JSONArray(room.getClassIds()));
            }
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("Rooms.json")) {
            file.write(jsonArray.toString(4)); // Pretty print
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the rooms to the list from the JSON file.
     */
    public static void loadRoomsFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("Rooms.json")));
            JSONArray jsonArray = new JSONArray(content);
            rooms.clear(); // Clear existing rooms before loading
            if (jsonArray.length() == 0) {
                // If the JSON array is empty, initialize default rooms
                initializeDefaultRooms();
            } else {
                // Load rooms from the JSON array as usual
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    rooms.add(new Room(jsonObject));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing Rooms.json found. A new one will be created.");
        }
    }

    private static void initializeDefaultRooms() {
        rooms.clear();
        addRoom("Desert");
        addRoom("Jungle");
        addRoom("Arctic");
        addRoom("Beach");
        saveRoomsToFile();


    }

    /**
     * Returns the list of rooms.
     *
     * @return list of all rooms
     */
    public static List<Room> getRooms() {
        return new ArrayList<>(rooms); // Return a copy of the rooms list
    }
}
