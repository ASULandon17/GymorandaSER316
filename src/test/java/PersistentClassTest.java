package test.java;

import main.java.memoranda.PersistentClass;
import main.java.memoranda.User;
import main.java.memoranda.UserType;

import org.json.*;
import org.junit.*;


import static org.junit.Assert.*;

/**
 * This class contains unit tests for PersistentClass.java
 */
public class PersistentClassTest extends JSONTest {


    @Test
    public void testLoadClassesFromFile() {
        PersistentClass.addNewClass("spin class", 2, 10, 1, true);

        PersistentClass.loadClassesFromFile();

    }
    @Test
    public void testAddNewClassWithoutTrainer () {

        assertTrue(PersistentClass.addNewClass("swim class", 2, 10, 2, true));

    }

    @Test
    public void testAddInstructorToClass() {
        PersistentClass.addNewClass("spin class", 2, 10, 1, true);
        PersistentClass.addNewClass("swim class", 2, 10, 2, true);
        assertEquals(0, PersistentClass.addInstructorToCourse("test", 1));
        assertEquals(0, PersistentClass.addInstructorToCourse("test", 2));

    }

    @Test
    public void testAddNewClassWithTrainer () {

        assertTrue(PersistentClass.addNewClass("HIIT Class", 3, 12, 3,
                true,"trainerTest"));
    }

    @Test
    public void testAddStudentToClass() {
        PersistentClass.addStudentToCourse("test", 1);
        PersistentClass.addStudentToCourse("test2", 1);
    }

    @Test
    public void testAddStudentsToMaxCapacity () {
        // add ten students
        PersistentClass.addNewClass("swim class", 2, 10, 1, true);

        for (int i = 0; i < 10; i++) {
            PersistentClass.addStudentToCourse("test" + i, 1);
        }

        assertEquals("Class is wrong size", 10, PersistentClass.getClassSize(1));
    }

    @Test
    public void testAddStudentsAfterMaxCapacity () {
        // add ten students
        PersistentClass.addNewClass("swim class", 2, 10, 1, true);

        for (int i = 0; i < 10; i++) {
            PersistentClass.addStudentToCourse("test" + i, 1);
        }

        // try adding another one
       assertEquals(3, PersistentClass.addStudentToCourse("chuck", 1)) ;
    }

    @Test
    public void testGetClassSize () {
        PersistentClass.addNewClass("swim class", 2, 10, 1, true);

        for (int i = 0; i < 10; i++) {
            PersistentClass.addStudentToCourse("test" + i, 1);
        }

        PersistentClass.addNewClass("spin class", 2, 10, 1, true);

        assertEquals(10, PersistentClass.getClassSize(1));
        assertEquals(0, PersistentClass.getClassSize(2));
    }

}
