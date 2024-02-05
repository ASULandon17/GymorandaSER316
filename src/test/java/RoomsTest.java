import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.java.memoranda.Rooms;

import main.java.memoranda.Room;

import java.util.List;


public class RoomsTest {

    @Before
    public void setUp(){
        Rooms.clearRooms();
    }
    @After
    public void tearDown() throws Exception{
        Rooms.clearRooms();
    }

    @Test
    public void testAddRoom(){
        Rooms.addRoom(true, "Room101", 1);
        List<Room> rooms = Rooms.getRooms();
        assertEquals(1, rooms.size());
        Room room = rooms.get(0);
        assertTrue("Room should have a class", room.getHasClass());
        assertEquals("Room name should match", "Room101", room.getRoomName());
        assertTrue("Room should contain the class ID added", room.getClassIds().contains(1));
    }

    @Test
    public void testAddRoomWithMultipleClassIds() {
        // First, add a room with a single class ID
        Rooms.addRoom(true, "MulticlassRoom", 101);
        // Then, add another class ID to the same room
        Rooms.addClassToRoom("MulticlassRoom", 102);

        Room room = Rooms.getRoomByName("MulticlassRoom");
        assertNotNull("Room should not be null", room);
        assertTrue("Room should have classes", room.getHasClass());
        assertEquals("Room should have 2 class IDs", 2, room.getClassIds().size());
        assertTrue("Room should contain both class IDs", room.getClassIds().containsAll(List.of(101, 102)));
    }

    @Test
    public void testSaveAndLoadRooms() {
        Rooms.addRoom(true, "Room102", 2);
        Rooms.addRoom(true, "Room103", 3);

        // Reload rooms from the file
        Rooms.loadRoomsFromFile();

        List<Room> rooms = Rooms.getRooms();
        assertEquals(2, rooms.size());

        // Check for properties of the first room as an example
        Room room = rooms.stream().filter(r -> r.getRoomName().equals("Room102")).findFirst().orElse(null);
        assertNotNull(room);
        assertTrue(room.getHasClass());
    }

    @Test
    public void testAddRoomNoClass(){
        Rooms.addRoom("NoclassRoom");
        List<Room> rooms = Rooms.getRooms();
        assertEquals("Only one room should be added", 1, rooms.size());
        Room room = rooms.get(0);
        assertFalse("Room should not have a class", room.getHasClass());
        assertTrue("Class IDs list should be empty", room.getClassIds().isEmpty());
    }

    @Test
    public void testAddRoomNoDuplicates() {
        Rooms.addRoom("Library");
        Rooms.addRoom("Library");
        assertEquals("Only one room should be added", 1, Rooms.getRooms().size());
    }

    @Test
    public void testAddClassToRoom() {
        String roomName = "Lab1";
        Rooms.addRoom(roomName); // Initially adding room without a class
        Rooms.addClassToRoom(roomName, 102); // Adding a class ID to the room
        Room room = Rooms.getRoomByName(roomName);

        assertNotNull("Room should exist after adding a class ID", room);
        assertTrue("Room should be marked as having a class", room.getHasClass());
        assertTrue("Room should contain the class ID added", room.getClassIds().contains(102));
    }
    @Test
    public void testGetRoomByName() {
        String roomName = "Auditorium";
        Rooms.addRoom(roomName);
        assertNotNull("Room should be found by name", Rooms.getRoomByName(roomName));
    }

    @Test
    public void testRoomListIntegrity() {
        Rooms.addRoom("Room101");
        Rooms.addRoom(true, "Room102", 202);
        List<Room> rooms = Rooms.getRooms();
        assertEquals("There should be exactly 2 rooms added", 2, rooms.size());
    }
}
