package test.java;

import org.json.*;

import main.java.memoranda.PersistentClass;
import main.java.memoranda.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testAddNewClassThatAlreadyExists() {

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        assertEquals("Duplicate class was added",1, PersistentClass.getListOfCourses().size());

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









}



