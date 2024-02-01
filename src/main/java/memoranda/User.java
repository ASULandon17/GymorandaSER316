package main.java.memoranda;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
public class User {
    private static String _username;
    private static String _password;
    private static String _beltRank = "white";
    private static String _userType;

    public static boolean signUp(String newUser, String newPassword, String userType){
        _username = newUser;
        _password = newPassword;
        _userType = userType;

        JSONObject userObject = new JSONObject();
        userObject.put("username", _username);
        userObject.put("password", _password);
        userObject.put("beltRank", _beltRank);
        userObject.put("userType", _userType);

        try {
            File file = new File("users.json");
            JSONArray usersArray;

            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get("users.json")));
                usersArray = new JSONArray(content);
            } else {
                usersArray = new JSONArray();
            }

            // Check if user already exists
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    return false;
                }
            }

            usersArray.put(userObject);

            try (FileWriter fileWriter = new FileWriter("users.json")) {
                fileWriter.write(usersArray.toString());
            }
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String loginUser, String loginPassword) {
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return false;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(loginUser) &&
                        user.getString("password").equals(loginPassword)) {
                    _username = loginUser;
                    _password = loginPassword;
                    _beltRank = user.getString("beltRank");
                    _userType = user.getString("userType");
                    return true;
                }
            }
            return false;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getUsername(){
        return _username;
    }
    public static String getUserType(){
        return _userType;
    }
}
