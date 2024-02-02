package memoranda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PersistentClass {

    private static String _className;
    //private static String _trainer;
    private static int _classLength;
    private static int _maxClassSize;
    private static int _classID;




    /**
     * This method adds an instructor to a specific class
     * @param instructorUserName username of instructor
     * @param classID classID
     * @return 0 - Instructor added; 1 - instructor is already assigned; 2 - IO or JSON exception thrown; 3 - JSON file not found
     */
    public static int addInstructorToCourse(String instructorUserName, int classID) {

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
                if (classs.getString("instructorName").equals("TBD") && classs.getInt("classID") == classID) {

                    classs.remove("instructorName");
                    classs.put("instructorName", instructorUserName);

                } else if (classs.getString("instructorName").equals(instructorUserName) && classs.getInt("classID") == classID) {

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


    public static int addStudentToCourse(String studentUserName, int classID) {



        // pull in the classes array

        try {
            File file = new File("classes.json");

            if (!file.exists()) {
                return 3; // JSON file not found
            }

            String classContent = new String(Files.readAllBytes(Paths.get("classes.json")));
            JSONArray classes = new JSONArray(classContent); // current list of classes


            for (int i = 0; i < classes.length(); i++) {

                JSONObject classs = classes.getJSONObject(i);

                int maxRoster = classs.getInt("maxClassSize");

                if (classs.getInt("classID") == classID) { // if we're on the right class, pull the roster

                    JSONArray students = classs.getJSONArray("roster");

                    if (students.length() < maxRoster) {

                        for (int j = 0; j <= students.length(); j++) {

                            // if array is empty, add the student
                            if (students.isNull(j)) {

                                // create JSON object for student
                                JSONObject nullStudent = new JSONObject();
                                nullStudent.put("students", studentUserName);
                               // add to JSON array (aka student roster)
                                students.put(nullStudent);
                                j++;

                            } else {

                                JSONObject registeredStudent = students.getJSONObject(j);
                                registeredStudent.put("students", studentUserName);
                                students.put(registeredStudent);
                                j++;


                            }
                        }
                    }

                    // write updates to file
                    try (FileWriter writer = new FileWriter("classes.json")) {
                        writer.write(classes.toString());
                    }


                }
            }

        } catch (IOException | JSONException e) {
            return 2;
        }

        return 0;
    }


    public static boolean addNewClass(String className, int classLength, int maxClassSize, int classID) {
        _className = className;
       // _trainer = trainer;
        _classLength = classLength;
        _maxClassSize = maxClassSize;
        _classID = classID;

        JSONObject classObject = new JSONObject();
        classObject.put("className", _className);
        classObject.put("classLength", _classLength);
        classObject.put("maxClassSize", _maxClassSize);
        classObject.put("classID", _classID);
        classObject.put("instructorName", "TBD");

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
}
