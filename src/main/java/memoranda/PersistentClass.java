package main.java.memoranda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class provides the backend for interacting with Classes within Gymoranda.
 */
public class PersistentClass {

    private static String _className;
    private static int _classLength;
    private static int _maxClassSize;
    private static int _classID;
    private static boolean _classIsPublic;
    private static String _studentUserName;
    private static String _instructorUserName;


    /**
     * Ensures JVM doesn't make a default constructor for utility class
     */
    private PersistentClass() {
        // No objects here!
    }
    
    /**
     * This method allows the owner to add a new class if they also do not know which instructor will teach it yet
     * @param className name of the class
     * @param classLength length of the class in hours
     * @param maxClassSize max class size
     * @param classID unique class ID (int)
     * @return true or false pending success
     */
    public static boolean addNewClass(String className, int classLength, int maxClassSize, int classID, boolean classIsPublic) {

        _className = className;
        _classLength = classLength;
        _maxClassSize = maxClassSize;
        _classID = classID;
        _classIsPublic = classIsPublic;

        JSONObject classObject = new JSONObject();
        classObject.put("className", _className);
        classObject.put("classLength", _classLength);
        classObject.put("maxClassSize", _maxClassSize);
        classObject.put("classID", _classID);
        classObject.put("instructorName", "TBD");
        classObject.put("isPublic", _classIsPublic);

        return addNewClass(classObject);

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
    public static boolean addNewClass(String className, int classLength, int maxClassSize, int classID,
                                      boolean classIsPublic, String instructorUserName) {

        _className = className;
        _classLength = classLength;
        _maxClassSize = maxClassSize;
        _classID = classID;
        _instructorUserName = instructorUserName;
        _classIsPublic = classIsPublic;

        JSONObject classObject = new JSONObject();
        classObject.put("className", _className);
        classObject.put("classLength", _classLength);
        classObject.put("maxClassSize", _maxClassSize);
        classObject.put("classID", _classID);
        classObject.put("instructorName", _instructorUserName);
        classObject.put("isPublic", _classIsPublic);

        return addNewClass(classObject);

    }

    private static boolean addNewClass (JSONObject classObject) {
        JSONArray roster = new JSONArray(); // add JSON array of students registered for the course
        classObject.put("roster", roster);

        try {

            File file = new File("classes.json");
            JSONArray classesArray; // load in the JSON file to a JSON array or if it doesn't exist, create new file

            if (file.exists()) {

                String content = new String(Files.readAllBytes(Paths.get("classes.json")));
                classesArray = new JSONArray(content);

            } else {
                classesArray = new JSONArray();
            }

            // Check to make sure the class doesn't already exist
            for (int i = 0; i < classesArray.length(); i++) {

                JSONObject classs = classesArray.getJSONObject(i);

                if (classs.getInt("classID") == _classID) {
                    return false; // classID already exists
                }
            }

            classesArray.put(classObject);

            // Write to classes JSON file
            try (FileWriter writer = new FileWriter("classes.json")) {
                writer.write(classesArray.toString());
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // todo: make a Classes class that uses below logic to pull in a specific class out of the json file to pass around
    /**
     * This method returns the total amount of students currently signed up for the course.
     * @param classID unique ID of the course
     * @return number of students registered (0 if empty) OR 99 if there is an error.
     */
    public static int getClassSize (int classID) {
        _classID = classID;

        try {
            File file = new File("classes.json");
            JSONArray classesArray; // load in the JSON file to a JSON array or if it doesn't exist, create new file

            if (!file.exists()){
                return 99;
            }
            else if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get("classes.json")));
                classesArray = new JSONArray(content);
            }
            else{

                classesArray = new JSONArray();
            }

            for (int i = 0; i < classesArray.length(); i++) {

                JSONObject iterator = classesArray.getJSONObject(i);

                System.out.println(iterator);

                if(iterator.getInt("classID") == _classID) {
                    JSONArray roster = iterator.getJSONArray("roster");
                    return roster.length();

                }

            }

            } catch (Exception e) {

            e.printStackTrace();

            return 99;
        }

        return 0;
    }
    /**
     * This method adds an instructor to a specific class
     * @param instructorUserName username of instructor
     * @param classID classID
     * @return 0 - Instructor added; 1 - instructor is already assigned; 2 - IO or JSON exception thrown; 3 - JSON file not found
     */
    public static int addInstructorToCourse(String instructorUserName, int classID) {

        _instructorUserName = instructorUserName;
        _classID = classID;
        // pull in the classes array, check if it has an instructor

        try {
            File file = new File("classes.json");

            if (!file.exists()) {
                return 3; // JSON file not found
            }

            String classContent = new String(Files.readAllBytes(Paths.get("classes.json")));
            JSONArray classes = new JSONArray(classContent);

            for (int i = 0; i < classes.length(); i++) {

                JSONObject classs = classes.getJSONObject(i);

                // If a class doesn't have an instructor, update instructor field
                if (classs.getString("instructorName").equals("TBD") && classs.getInt("classID") == _classID) {

                    classs.remove("instructorName");
                    classs.put("instructorName", _instructorUserName);

                } else if (classs.getString("instructorName").equals(_instructorUserName) && classs.getInt("classID") == _classID) {

                    return 1; // This instructor is already assigned
                }


                // write updates to file
                try (FileWriter writer = new FileWriter("classes.json")) {
                    writer.write(classes.toString());
                }


            }

        } catch (IOException | JSONException e) {
            return 2;
        }

        return 0;
    }

    /**
     * addStudentToCourse() allows user to register for a course as long as the course isn't full
     * AND they aren't already registered.
     * @param studentUserName username of student registering
     * @param classID classID for the course they want to register for
     * @return 0 - student added successfully, 1 - JSON file not found, 2 - IO or JSON Exception thrown,
     * 3 - class is full, 4 - student is already registered for course
     */
    public static int addStudentToCourse(String studentUserName, int classID) {

        _studentUserName = studentUserName;
        _classID = classID;

        // pull in the classes array

        try {
            File file = new File("classes.json");

            if (!file.exists()) {
                return 1; // JSON file not found
            }

            String classContent = new String(Files.readAllBytes(Paths.get("classes.json")));
            JSONArray classes = new JSONArray(classContent); // current list of classes


            for (int i = 0; i < classes.length(); i++) {

                JSONObject classs = classes.getJSONObject(i);

                int maxRoster = classs.getInt("maxClassSize");

                if (classs.getInt("classID") == _classID) { // if we're on the right class, pull the roster

                    JSONArray students = classs.getJSONArray("roster");

                    if (students.length() < maxRoster) {

                        for (int j = 0; j <= students.length(); j++) {

                            // if array is empty, add the student
                            if (students.isNull(j)) {

                                students.put(_studentUserName);


                                // write update to file
                                try (FileWriter writer = new FileWriter("classes.json")) {
                                    writer.write(classes.toString());
                                }

                                return 0;

                                // check to see if student is already registered for the course
                            } else if (!students.getString(j).equals(_studentUserName)){

                                students.put(_studentUserName);

                                // write update to file
                                try (FileWriter writer = new FileWriter("classes.json")) {
                                    writer.write(classes.toString());
                                }

                                return 0;

                            }else if (students.getJSONObject(j).getString(_studentUserName).equals(_studentUserName)){

                                return 4; // Student already registered for course

                            }

                            else {
                                j++; // go onto next class entry
                            }
                        }
                    }
                }
            }

        } catch (IOException | JSONException e) {
            return 2;
        }

        return 3;
    }



}
