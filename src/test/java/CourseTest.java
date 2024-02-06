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


    @Test
    public void testLoadingClassesFromFile() {

        // add two unique classes
        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);
        PersistentClass.addNewClass("super Smash bros", 2, 10, 60, true);
        // new classes should save to json on creation

        PersistentClass.loadClassesFromFile();


    }

    @Test
    public void testAddNewClassWithoutInstructor() {

        PersistentClass.addNewClass("super Smash bros", 2, 10, 60, true);

        assertEquals(1, PersistentClass.getListOfCourses().size());

        PersistentClass.addNewClass("another course", 2, 10, 69, true);

        assertEquals(2, PersistentClass.getListOfCourses().size());

    }


    @Test
    public void testAddNewClassThatAlreadyExists() {

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        assertEquals(1, PersistentClass.getListOfCourses().size());

    }

    @Test
    public void testAddInstructorToCourseWithoutInstructor() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60, true);

        PersistentClass.addInstructorToCourse("steve", 60);

        assertEquals("steve", PersistentClass.getCourseByID(60).getInstructorName());

    }

    @Test
    public void testAddInstructorToCourseWithInstructor() {
        PersistentClass.addNewClass("whale sharks", 2, 10, 60,true);

        PersistentClass.addInstructorToCourse("steve", 60);
        // try adding different instructor to same course
        PersistentClass.addInstructorToCourse("craig", 60);

        assertEquals("steve", PersistentClass.getCourseByID(60).getInstructorName());
    }









}



