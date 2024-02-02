import memoranda.PersistentClass;
import memoranda.User;
import main.java.memoranda.UserType;

import org.junit.*;

import java.io.File;


import static org.junit.Assert.*;

public class PersistentClassTest {


    public void deleteJSONFiles() {
        try{
            File doomedClasses = new File("classes.json");
            File doomedUsers = new File("users.json");

           if(doomedClasses.delete() && doomedUsers.delete()) {
               System.out.println("JSON files deleted succesfully for testing");
           } else{
               System.out.println("JSON files were not deleted");
           }


        }catch (SecurityException | NullPointerException e) {
            System.out.println("Program does not have access to delete this file");
        }





    }
    @Before
    public void setup() {
        deleteJSONFiles();
        User.signUp("trainerTest", "password", UserType.TRAINER);
    }

    @Test
    public void testAddNewClassWithoutTrainer () {
        assertTrue(PersistentClass.addNewClass("spin class", 2, 10, 1));



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
}
