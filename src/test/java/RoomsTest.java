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
        //Files.deleteIfExists(Paths.get("Rooms.json"));
    }

    @Test
    public void testAddRoom(){
        Rooms.addRoom(true, "Room101", 1);
        List<Room> rooms = Rooms.getRooms();
        assertEquals(1, rooms.size());
        Room room = rooms.get(0);
        assertTrue(room.getHasClass());
        assertEquals("Room101", room.getRoomName());
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
        Rooms.addRoom("Noclass");
        List<Room> rooms = Rooms.getRooms();
        assertEquals(1, rooms.size());
        Room room = rooms.get(0);
        assertFalse(room.getHasClass());
    }
}
