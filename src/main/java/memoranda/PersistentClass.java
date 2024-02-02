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

    private static JSONObject _instructor;


    public static boolean addInstructorToCourse(String instructorUserName, int classID) {

        // pull in the classes array, check if it has an instructor

        try {
            File file = new File("classes.json");

            if (!file.exists()) {
                return false; // JSON file not found
            }

            String classContent = new String(Files.readAllBytes(Paths.get("classes.json")));
            JSONArray classes = new JSONArray(classContent);

            for (int i = 0; i < classes.length(); i++) {
               JSONObject classs = classes.getJSONObject(i);
                if (classs.getString("instructorName").equals("TBD") && classs.getInt("classID") == classID) {

                    classs.remove("instructorName");
                    classs.put("instructorName", instructorUserName);


                    // write updates to file

                    try (FileWriter writer = new FileWriter("classes.json")) {
                        writer.write(classes.toString());
                    }


                }

            }

        } catch (IOException | JSONException e) {
            return false;
        }

        return true;
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
