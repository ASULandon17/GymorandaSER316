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
