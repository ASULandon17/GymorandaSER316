package test.java;

import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

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
        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, 2024, 2, 7, 12);
        PersistentClass.addNewClass("super Smash bros", 2, 10, 61, true, 2024, 2, 7, 14);
        // new classes should save to json on creation

        PersistentClass.loadClassesFromFile();


        assertEquals("Entries not saved properly",60, PersistentClass.getListOfCourses().get(0).getClassId());
    }

    @Test
    public void testAddNewClassWithoutInstructor() {

        PersistentClass.addNewClass("super Smash bros", 2, 10, 60, true, 2024, 2, 7, 14);

        assertEquals("first class not added", 1, PersistentClass.getListOfCourses().size());

        PersistentClass.addNewClass("another course", 2, 10, 69, true, 2024, 2, 7, 14);

        assertEquals("second unique class not added",2, PersistentClass.getListOfCourses().size());

    }


    @Test
    public void testAddNewClassWithoutInstructorThatAlreadyExists() {

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, 2024, 2, 7, 14);

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, 2024, 2, 7, 14);

        assertEquals("Duplicate class was added",1, PersistentClass.getListOfCourses().size());

    }

    @Test
    public void testAddNewClassWithInstructorThatAlreadyExists() {

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, "jeff", 2024, 2, 7, 14);

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, "craig", 2024, 2, 7, 14);


        assertEquals("Duplicate class was added and instructor was overwritten","jeff", PersistentClass.getCourseById(60).getInstructorName());

    }

    @Test
    public void testAddInstructorToCourseWithoutInstructor() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true, 2024, 2, 7, 14);

        PersistentClass.addInstructorToCourse("steve", 60);

        assertEquals("Instructor not added to course","steve",
                PersistentClass.getCourseById(60).getInstructorName());

    }

    @Test
    public void testAddInstructorToCourseWithInstructor() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60,true, 2024, 2, 7, 14);

        PersistentClass.addInstructorToCourse("steve", 60);
        // try adding different instructor to same course
        PersistentClass.addInstructorToCourse("craig", 60);

        assertEquals("Instructor was overwritten","steve",
                PersistentClass.getCourseById(60).getInstructorName());
    }


    @Test
    public void testAddInstructorToCourseThatDoesNotExist() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60,true, 2024, 2, 7, 14);
        // try adding an instructor to a course that doesn't exist yet
        PersistentClass.addInstructorToCourse("craig", 62);
        // make sure program doesn't break

    }


    @Test
    public void testAddNewClassWithInstructor() {

        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);


        assertEquals("Instructor is incorrect","scuba lord",
                PersistentClass.getCourseById(99).getInstructorName());

    }

    @Test
    public void testAddStudentToEmptyCourse() {
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);

        PersistentClass.addStudentToCourse("bob", 99);

        assertEquals("Student was not added to course", "bob",
                PersistentClass.getCourseById(99).getRoster().getJSONObject(0).getString("userName"));

    }



    @Test
    public void testAddStudentToCourseThatIsAlreadyRegistered() {
       // add a class and 1 student
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);
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
                false, "scuba lord", 2024, 2, 7, 14);
        // add a student to a course that doesn't exist
        PersistentClass.addStudentToCourse("bob", 22);

        assertEquals("Student was added to existing course", 0, PersistentClass.getCourseById(99).getCurrentClassSize());
        assertNull("Course was created improperly", PersistentClass.getCourseById(22));
    }

    @Test
    public void testAddStudentToEmptyClassFile() {
        // add a student to a course that doesn't exist
        PersistentClass.addStudentToCourse("bob", 22);
        // make sure course was not added
        assertNull("Course that doesn't exisit was added to file", PersistentClass.getCourseById(22));
    }


    @Test
    public void testRemoveStudentFromRoster() {

        // add new class with two students:
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);
        PersistentClass.addStudentToCourse("bob", 99);
        PersistentClass.addStudentToCourse("chuck", 99);

        // ensure class size is 2
        assertEquals("Class size is not correct", 2, PersistentClass.getCourseById(99).getCurrentClassSize());

        // remove the first student on the roster
        PersistentClass.getCourseById(99).removeStudentFromRoster("bob");

        // ensure the current class size is 1 after removing student
        assertEquals("Class size was not reduced to 1", 1, PersistentClass.getCourseById(99).getCurrentClassSize());

        // check if only chuck is left
        assertTrue("Wrong student was deleted", PersistentClass.getCourseById(99).isStudentRegistered("chuck"));


    }


    @Test
    public void testRemoveStudentFromRosterThatDoesNotExist() {

        // add new class with two students:
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);
        PersistentClass.addStudentToCourse("bob", 99);
        PersistentClass.addStudentToCourse("chuck", 99);

        // try to remove a student that isn't on the roster
        PersistentClass.getCourseById(99).removeStudentFromRoster("larry");

        // check that class size wasn't changed
        assertEquals("A student was removed", 2, PersistentClass.getCourseById(99).getCurrentClassSize());
    }

    @Test
    public void testDeleteExistingCourseById(){
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);
        PersistentClass.addNewClass("scuba diving2", 6, 5, 1,
                false, "scuba lord2", 2024, 2, 7, 14);
        int initialSize = PersistentClass.getListOfCourses().size();
        int classIdToDelete = 1;

        PersistentClass.deleteCourseById(classIdToDelete);
        assertEquals("Course size should have decreased", initialSize - 1, PersistentClass.getListOfCourses().size());
        assertNull("Course should not exist", PersistentClass.getCourseById(classIdToDelete));
    }

    @Test
    public void testPersistenceOfDeletion(){
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);
        PersistentClass.addNewClass("scuba diving2", 6, 5, 1,
                false, "scuba lord2", 2024, 2, 7, 14);
        int classIdToDelete = 1;
        PersistentClass.deleteCourseById(classIdToDelete);
        PersistentClass.loadClassesFromFile();
        assertNull("Course should not exist", PersistentClass.getCourseById(classIdToDelete));
    }

    @Test
    public void testDeleteNonExistingCourse() {

        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2024, 2, 7, 14);
        int initialSize = PersistentClass.getListOfCourses().size();
        int nonExistingClassId = 1;
        PersistentClass.deleteCourseById(nonExistingClassId);

        assertEquals("No course should have been deleted", initialSize, PersistentClass.getListOfCourses().size());
    }

    @Test
    public void testDateAndTimeGettersAndSetters() {
        PersistentClass.addNewClass("scuba diving", 6, 5, 99,
                false, "scuba lord", 2025, 3, 7, 18);
        assertEquals("getYear not working", 2025, PersistentClass.getCourseById(99).getClassYear());
        assertEquals("getMonth not working", 3, PersistentClass.getCourseById(99).getClassMonth());
        assertEquals("getDay not working", 7, PersistentClass.getCourseById(99).getClassDay());
        assertEquals("getHour not working", 18, PersistentClass.getCourseById(99).getClassHour());
        
        PersistentClass.getCourseById(99).setClassYear(2026);
        PersistentClass.getCourseById(99).setClassMonth(4);
        PersistentClass.getCourseById(99).setClassDay(8);
        PersistentClass.getCourseById(99).setClassHour(19);
        
        assertEquals("setYear not working", 2026, PersistentClass.getCourseById(99).getClassYear());
        assertEquals("setMonth not working", 4, PersistentClass.getCourseById(99).getClassMonth());
        assertEquals("setDay not working", 8, PersistentClass.getCourseById(99).getClassDay());
        assertEquals("setHour not working", 19, PersistentClass.getCourseById(99).getClassHour());
    }

    @Test
    public void testNoClasses() {
        ArrayList<Course> result = PersistentClass.getNext5Classes();
        assertNull("Expected null when there are no classes", result);
    }

    @Test
    public void testLessThanFiveClasses() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        PersistentClass.addNewClass("Class 1", 2, 30, 101, true, "instructor1", tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 10);
        PersistentClass.addNewClass("Class 2", 2, 30, 102, true, "instructor2", tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 12);
        PersistentClass.addNewClass("Class 3", 2, 30, 103, true, "instructor3", tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 14);

        ArrayList<Course> result = PersistentClass.getNext5Classes();
        assertNotNull("Expected a non-null list of classes", result);
        assertEquals("Expected 3 classes", 3, result.size());
    }

    @Test
    public void testMoreThanFiveClasses() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        for (int i = 1; i <= 6; i++) {
            PersistentClass.addNewClass("Class " + i, 2, 30, 100 + i, true, "instructor" + i, tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 8 + i);
        }

        ArrayList<Course> result = PersistentClass.getNext5Classes();
        assertNotNull("Expected a non-null list of classes", result);
        assertEquals("Expected 5 classes", 5, result.size());
    }
}



