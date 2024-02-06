package test.java;

import main.java.memoranda.PersistentClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class facilities the testing of both Course.java and PersistentClass.java.
 */
public class CourseTest {

    @Before
    public void setup() {
        PersistentClass.clearCourses();
    }

    @After
    public void teardown() {
        PersistentClass.clearCourses();
    }


    @Test
    public void testLoadingClassesFromFile() {

        // add two unique classes
        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);
        PersistentClass.addNewClass("super Smash bros", 2, 10, 61, true);
        // new classes should save to json on creation

        PersistentClass.loadClassesFromFile();


        assertEquals("Entries not saved properly",60, PersistentClass.getListOfCourses().getFirst().getClassID());
    }

    @Test
    public void testAddNewClassWithoutInstructor() {

        PersistentClass.addNewClass("super Smash bros", 2, 10, 60, true);

        assertEquals("first class not added", 1, PersistentClass.getListOfCourses().size());

        PersistentClass.addNewClass("another course", 2, 10, 69, true);

        assertEquals("second unique class not added",2, PersistentClass.getListOfCourses().size());

    }


    @Test
    public void testAddNewClassWithoutInstructorThatAlreadyExists() {

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        assertEquals("Duplicate class was added",1, PersistentClass.getListOfCourses().size());

    }

    @Test
    public void testAddNewClassWithInstructorThatAlreadyExists() {

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, "jeff");

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, "craig");


        assertEquals("Duplicate class was added and instructor was overwritten","jeff", PersistentClass.getCourseByID(60).getInstructorName());

    }

    @Test
    public void testAddInstructorToCourseWithoutInstructor() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        PersistentClass.addInstructorToCourse("steve", 60);

        assertEquals("Instructor not added to course","steve",
                PersistentClass.getCourseByID(60).getInstructorName());

    }

    @Test
    public void testAddInstructorToCourseWithInstructor() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60,true);

        PersistentClass.addInstructorToCourse("steve", 60);
        // try adding different instructor to same course
        PersistentClass.addInstructorToCourse("craig", 60);

        assertEquals("Instructor was overwritten","steve",
                PersistentClass.getCourseByID(60).getInstructorName());
    }


    @Test
    public void testAddInstructorToCourseThatDoesNotExist() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60,true);
        // try adding an instructor to a course that doesn't exist yet
        PersistentClass.addInstructorToCourse("craig", 62);
        // make sure program doesn't break

    }


    @Test
    public void testAddNewClassWithInstructor() {

        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord");


        assertEquals("Instructor is incorrect","scuba lord",
                PersistentClass.getCourseByID(99).getInstructorName());

    }

    @Test
    public void testAddStudentToEmptyCourse() {
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord");

        PersistentClass.addStudentToCourse("bob", 99);

        assertEquals("Student was not added to course", "bob",
                PersistentClass.getCourseByID(99).getRoster().getJSONObject(0).getString("userName"));

    }


    // add student to a course that's already registered
    @Test
    public void testAddStudentToCourseThatIsAlreadyRegistered() {
       // add a class and 1 student
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord");
        PersistentClass.addStudentToCourse("bob", 99);

        // try to add that student again
        PersistentClass.addStudentToCourse("bob", 99);

        // check that roster size is still 1
        assertEquals("Duplicate student was added to course", 1, PersistentClass.getListOfCourses().size());

    }

    @Test
    public void testAddStudentToCourseThatDoesNotExist() {
        // add a class
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord");
        // add a student to a course that doesn't exist
        PersistentClass.addStudentToCourse("bob", 22);

        assertEquals("Student was added to existing course", 0, PersistentClass.getCourseByID(99).getCurrentClassSize());
        assertNull("Course was created improperly", PersistentClass.getCourseByID(22));
    }

    @Test
    public void testAddStudentToEmptyClassFile() {
        // add a student to a course that doesn't exist
        PersistentClass.addStudentToCourse("bob", 22);
        // make sure course was not added
        assertNull("Course that doesn't exisit was added to file", PersistentClass.getCourseByID(22));
    }


    @Test
    public void testRemoveStudentFromRoster() {

        // add new class with two students:
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord");
        PersistentClass.addStudentToCourse("bob", 99);
        PersistentClass.addStudentToCourse("chuck", 99);

        // ensure class size is 2
        assertEquals("Class size is not correct", 2, PersistentClass.getCourseByID(99).getCurrentClassSize());

        // remove the first student on the roster
        PersistentClass.getCourseByID(99).removeStudentFromRoster("bob");

        // ensure the current class size is 1 after removing student
        assertEquals("Class size was not reduced to 1", 1, PersistentClass.getCourseByID(99).getCurrentClassSize());

        // check if only chuck is left
        assertTrue("Wrong student was deleted", PersistentClass.getCourseByID(99).isStudentRegistered("chuck"));


    }

    @Test
    public void testRemoveStudentFromRosterThatDoesNotExist() {

        // add new class with two students:
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord");
        PersistentClass.addStudentToCourse("bob", 99);
        PersistentClass.addStudentToCourse("chuck", 99);

        // try to remove a student that isn't on the roster
        PersistentClass.getCourseByID(99).removeStudentFromRoster("larry");

        // check that class size wasn't changed
        assertEquals("A student was removed", 2, PersistentClass.getCourseByID(99).getCurrentClassSize());
    }


}



