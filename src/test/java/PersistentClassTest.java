import memoranda.PersistentClass;
import main.java.memoranda.User;
import main.java.memoranda.UserType;

import org.json.*;
import org.junit.*;


import static org.junit.Assert.*;

public class PersistentClassTest extends JSONTest {

    JSONArray tempFiles = new JSONArray();


    @Before
    public void setup() {
        tempFiles = deleteJSONFiles();
        User.signUp("trainerTest", "password", UserType.TRAINER);
    }

    @Test
    public void testAddNewClassWithoutTrainer () {

        assertTrue(PersistentClass.addNewClass("swim class", 2, 10, 2));


    }

    @Test
    public void testAddInstructorToClass() {
        assertEquals(0, PersistentClass.addInstructorToCourse("test", 1));
        assertEquals(0, PersistentClass.addInstructorToCourse("test", 2));

    }

    @Test
    public void testAddNewClassWithTrainer () {

        assertTrue(PersistentClass.addNewClass("HIIT Class", 3, 12, 3, "trainerTest"));
    }

    @Test
    public void testAddStudentToClass() {
        PersistentClass.addStudentToCourse("test", 1);
        PersistentClass.addStudentToCourse("test2", 1);
    }

    @Test
    public void testAddStudentsToMaxCapacity () {
        // add ten students

        for (int i = 0; i < 10; i++) {
            PersistentClass.addStudentToCourse("test" + i, 1);
        }
    }

    @Test
    public void testAddStudentsAfterMaxCapacity () {
        // add ten students

        for (int i = 0; i < 10; i++) {
            PersistentClass.addStudentToCourse("test" + i, 1);
        }

        // try adding another one
       assertEquals(3, PersistentClass.addStudentToCourse("chuck", 1)) ;
    }

    @Test
    public void testGetClassSize () {
        assertEquals(10, PersistentClass.getClassSize(1));
        assertEquals(0, PersistentClass.getClassSize(2));
    }

    @After
    public void restoreFiles () {
        restoreJSONFiles(tempFiles);
    }
}
