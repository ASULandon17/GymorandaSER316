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

import main.java.memoranda.BeltValue;
import main.java.memoranda.UserType;

public class User {
    private static String _username;
    private static String _password;
    private static BeltValue _beltRank = BeltValue.WHITE;
    private static UserType _userType;
    private static BeltValue _trainingRank = BeltValue.NO_BELT;

    public User(String name, String password, BeltValue beltRank, UserType userType, BeltValue trainingRank) {
        _username = name;
        _password = password;
        _beltRank = beltRank;
        _userType = userType;
        _trainingRank = trainingRank;
    }

    public static boolean signUp(String newUser, String newPassword, UserType userType){
        _username = newUser;
        _password = newPassword;
        _userType = userType;

        JSONObject userObject = new JSONObject();
        userObject.put("username", _username);
        userObject.put("password", _password);
        userObject.put("beltRank", _beltRank);
        userObject.put("userType", _userType);
        userObject.put("trainingRank", _trainingRank);

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
                    _beltRank = BeltValue.valueOf(user.getString("beltRank"));
                    _userType = UserType.valueOf(user.getString("userType"));
                    _trainingRank = BeltValue.valueOf(user.getString("trainingRank"));
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
    public static UserType getUserType(){
        return _userType;
    }
    public BeltValue getBeltRank() {
        return _beltRank;
    }

    public BeltValue getTrainingRank() {
        return _trainingRank;
    }

    /**
     * Increase User's belt rank by one stage
     */
    public void increaseBeltRank() {
        _beltRank = _beltRank.increaseBelt();
    }

    /**
     * Upgrade a member to trainer
     * Returns 0 if the User changed from a member to a trainer
     * Returns -1 if the User is already a trainer
     * Returns 1 if the User is an owner (They should not change)
     * Returns 2 if the User's userType is none of the 3 options (error)
     * @return int
     */
    public int becomeTrainer() {
        int changedUserType = 2;

        if(_userType == UserType.MEMBER) {
            changedUserType = 0;
            _userType = _userType.becomeTrainer();
            increaseTrainingRank();
        }
        else if(_userType == UserType.TRAINER) {
            changedUserType = -1;
        }
        else if(_userType == UserType.OWNER) {
            changedUserType = 1;
        }
        else {
            System.out.println("Error changing UserTypes");
        }

        return changedUserType;
    }

    /**
     * Increase User's training rank by one stage
     */
    public void increaseTrainingRank() {
        _trainingRank = _trainingRank.increaseBelt();
    }
}
