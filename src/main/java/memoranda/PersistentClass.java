package main.java.memoranda;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class provides the backend for interacting with Classes within Gymoranda.
 */
public class PersistentClass {

    private static final ArrayList<Course> courses = new ArrayList<>();


    public static ArrayList<Course> getListOfCourses() {
        return courses;
    }

    /**
     * Clears the ArrayList and the json file.
     */
    public static void clearCourses() {
        courses.clear();
        clearCourseFile();
    }

    private static void clearCourseFile() {

        try (FileWriter file = new FileWriter("classes.json")) {

            file.write(new JSONArray().toString()); // Write an empty array to the file

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Loads the classes to the arraylist from the JSON file.
     */
    public static void loadClassesFromFile() {

        try {

            String content = new String(Files.readAllBytes(Paths.get("classes.json")));
            JSONArray jsonArray = new JSONArray(content);
            courses.clear(); // Clear existing rooms before loading

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                courses.add(new Course(jsonObject));
            }

        } catch (IOException e) {

            System.out.println("No existing classes.json found. A new one will be created.");
        }
    }


    /**
     *
     * Saves the Room list to the json file.
     */
    private static void saveClassesToFile() {

        JSONArray jsonArray = new JSONArray();

        for (Course course : courses) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("className", course.getClassName());
            jsonObject.put("instructorName", course.getInstructorName());
            jsonObject.put("classLength", course.getClassLength());
            jsonObject.put("maxClassSize", course.getMaxClassSize());
            jsonObject.put("currentClassSize", course.getCurrentClassSize());
            jsonObject.put("classID", course.getClassID());
            jsonObject.put("isPublic", course.getPublic());
            jsonObject.put("roster", course.getRoster());

            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("classes.json")) {

            file.write(jsonArray.toString(4)); // Pretty print

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    /**
     * Ensures JVM doesn't make a default constructor for utility class
     */
    private PersistentClass() {
        // No objects here!
    }

    public static Course getCourseByID(int classID) {

        for(Course course : courses) {
            if (course.getClassID() == classID) {
                return course;
            }
        }

        return null; // no matching course found
    }

    /**
     * This method allows the owner to add a new class if they also do not know which instructor will teach it yet
     * @param className name of the class
     * @param classLength length of the class in hours
     * @param maxClassSize max class size
     * @param classID unique class ID (int)
     * @return true or false pending success
     */
    public static void addNewClass(String className, int classLength, int maxClassSize, int classID, boolean classIsPublic) {

        // check if class already exists:
        if (getCourseByID(classID) == null) {
            courses.add(new Course(className, classLength, maxClassSize, classID, classIsPublic));
            saveClassesToFile();
        }
        else {
            System.out.println("Class already exists");
        }

    }

    /**
     * This method allows the owner to add a new class if they do know the instructor that will be teaching
     * @param className name of the class
     * @param classLength length of the class in hours
     * @param maxClassSize max class size
     * @param classID unique class ID (int)
     * @param instructorUserName name of instructor teaching the course
     * @return true or false pending success
     */
    public static void addNewClass(String className, int classLength, int maxClassSize, int classID,
                                      boolean classIsPublic, String instructorUserName) {

        // check if class already exists:
        if (getCourseByID(classID) == null) {
            courses.add(new Course(className, classLength, maxClassSize,classID, classIsPublic, instructorUserName));
            saveClassesToFile();

        }
        else {
            System.out.println("Class already exists");
        }

    }


    /**
     * This method adds an instructor to a specific class
     * @param instructorUserName username of instructor
     * @param classID classID
     * @return 0 - Instructor added; 1 - instructor is already assigned; 2 - IO or JSON exception thrown; 3 - JSON file not found
     */
    public static void addInstructorToCourse(String instructorUserName, int classID) {

        for (Course course : courses) {
            if (course.getClassID() == classID) {

                // check if there's already an instructor
                if (course.getInstructorName().isEmpty()) {

                    course.setInstructorName(instructorUserName);

                } else {

                    System.out.println("There is already an instructor assigned to this course!");
                }
            }
        }
    }

    /**
     * addStudentToCourse() allows user to register for a course as long as the course isn't full
     * AND they aren't already registered.
     * @param studentUserName username of student registering
     * @param classID classID for the course they want to register for
     * @return 0 - student added successfully, 1 - JSON file not found, 2 - IO or JSON Exception thrown,
     * 3 - class is full, 4 - student is already registered for course
     */
    public static void addStudentToCourse(String studentUserName, int classID) {

        for (Course course : courses) {
           // make sure course exists
            if (course.getClassID() == classID) {

                // see if student is already registered
                if (!course.isStudentRegistered(studentUserName)) {

                    course.addStudentToRoster(studentUserName);
                }


            }
        }

    }



}
