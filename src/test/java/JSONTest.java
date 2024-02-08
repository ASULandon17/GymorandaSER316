package test.java;

import org.json.*;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class contains utility methods for unit testing with JSON files.
 */
public class JSONTest {

    private JSONArray tempJSONArray;

    @Before
    public void setup() {
        tempJSONArray = deleteJSONFiles();
    }

    @After
    public void restoreFiles() {
        restoreJSONFiles(tempJSONArray);
    }

    /**
     * This utility test method restores the local json files after testing is complete
     * @param tempJSONArray json files as a JSONArray
     */
    public void restoreJSONFiles (JSONArray tempJSONArray) {

        // Each json file is in the JSONArray as a JSONObject with a key corresponding to the file

        try (FileWriter writer = new FileWriter("classes.json")) {
            writer.write(tempJSONArray.getJSONObject(0).getJSONArray("class").toString());


            try (FileWriter anotherWriter = new FileWriter("users.json")) {
                anotherWriter.write(tempJSONArray.getJSONObject(1).getJSONArray("user").toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Files restored!");


    }

    /**
     * This utility test method stores the program's current json files, deletes them for unit testing, and then returns
     * them as a delineated JSONArray to be restored after the tests.
     * @return array of JSON files
     */
    public JSONArray deleteJSONFiles() {

        JSONArray jsonSecretStash = new JSONArray();

        try{
            File doomedClasses = new File("classes.json");
            File doomedUsers = new File("users.json");

            // read each file into a temp JSONArray

            JSONArray tempClasses = createTempFile(doomedClasses);
            JSONArray tempUsers = createTempFile(doomedUsers);

            // create a key JSON object to separate each json file grouping

            JSONObject classKey = new JSONObject();
            classKey.put("class", tempClasses);

            JSONObject userKey = new JSONObject();
            userKey.put("user", tempUsers);


            jsonSecretStash.put(classKey);
            jsonSecretStash.put(userKey);

            //System.out.println(jsonSecretStash);
            //System.out.println("Files deleted!");


            // delete the json files

            if(doomedClasses.delete() && doomedUsers.delete()) {
                System.out.println("JSON files deleted successfully for testing");
            } else{
                System.out.println("JSON files were not deleted");
            }


        }catch (SecurityException | NullPointerException e) {
            System.out.println("Program does not have access to delete this file");
        }


        return jsonSecretStash;
    }

    /**
     * This method stores a json file into a temp array to provide a clean slate for unit testing
     * @param doomedFile file to be stored during testing
     * @return temp JSONArray
     */
    private JSONArray createTempFile (File doomedFile) {

        try{

            String content = new String(Files.readAllBytes(Paths.get(doomedFile.toURI())));

            return new JSONArray(content);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
