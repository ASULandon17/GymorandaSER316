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
    // The earliest a Trainer can start, set to 0 for non-trainers
    private static int _startAvailability = 0;
    // The latest a Trainer can work, set to 0 for non-trainers
    private static int _endAvailability = 0;

    public static boolean signUp(String newUser, String newPassword, UserType userType){
        _username = newUser;
        _password = newPassword;
        _userType = userType;

        //Check if userType is TRAINER to update availability
        if(_userType == UserType.TRAINER) {
            // All Trainers start will completely open availability
            // They can update their availability after
            _startAvailability = 8;   //8:00am
            _endAvailability = 19;    //7:00pm
        }

        JSONObject userObject = new JSONObject();
        userObject.put("username", _username);
        userObject.put("password", _password);
        userObject.put("beltRank", _beltRank);
        userObject.put("userType", _userType);
        userObject.put("trainingRank", _trainingRank);
        userObject.put("startAvailability", _startAvailability);
        userObject.put("endAvailability", _endAvailability);

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
                    _startAvailability = user.getInt("startAvailability");
                    _endAvailability = user.getInt("endAvailability");
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
    public static BeltValue getBeltRank() {
        return _beltRank;
    }

    public static BeltValue getTrainingRank() {
        return _trainingRank;
    }

    public static int getStartAvailability() {
        return _startAvailability;
    }

    public static int getEndAvailability() {
        return _endAvailability;
    }

    public static void setStartAvailability(int start) {
        _startAvailability = start;
        try {
            File file = new File("users.json");


            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    user.put("startAvailability", _startAvailability);
                    break;
                }
            }
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(usersArray.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setEndAvailability(int end) {
        _endAvailability = end;
        try {
            File file = new File("users.json");


            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    user.put("endAvailability", _endAvailability);
                    break;
                }
            }
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(usersArray.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increase User's belt rank by one stage
     */
    public static void increaseBeltRank() {
        _beltRank = _beltRank.increaseBelt();
        try {
            File file = new File("users.json");


            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    user.put("beltRank", _beltRank.toString());
                    break;
                }
            }
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(usersArray.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets user's belt rank to given rank value.
     */
    public static void setBeltRank(BeltValue rank) {
        _beltRank = rank;
        try {
            File file = new File("users.json");


            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    user.put("beltRank", _beltRank.toString());
                    break;
                }
            }
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(usersArray.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Upgrade a member to trainer
     * Returns 0 if the User changed from a member to a trainer
     * Returns -1 if the User is already a trainer
     * Returns 1 if the User is an owner (They should not change)
     * Returns 2 if the User's userType is none of the 3 options (error)
     * @return int
     */
    public static int becomeTrainer() {
        int changedUserType = 2;

        if(_userType == UserType.MEMBER) {
            changedUserType = 0;
            _userType = UserType.TRAINER;
            increaseTrainingRank();
            // Change availability from 0 to 8am-7pm
            setStartAvailability(8);
            setEndAvailability(19);
            try {
                File file = new File("users.json");
                String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
                JSONArray usersArray = new JSONArray(content);

                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject user = usersArray.getJSONObject(i);
                    if (user.getString("username").equals(_username)) {
                        // Update the user type in the JSON object
                        user.put("userType", _userType.toString());
                        user.put("startAvailability", _startAvailability);
                        user.put("endAvailability", _endAvailability);
                        break;
                    }
                }

                // Write the updated JSON array back to the file
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(usersArray.toString());
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
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
    public static void increaseTrainingRank() {
        _trainingRank = _trainingRank.increaseBelt();
        try {
            File file = new File("users.json");
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    user.put("trainingRank", _trainingRank.toString());
                    break;
                }
            }

            // Write the updated JSON array back to the file
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(usersArray.toString());
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to allow the current user to change their password.
     * @param newPassword, the new password to change to.
     * @return true if password change, false if password not properly changed.
     */
    public static boolean changePassword(String newPassword){
        _password = newPassword;
        try {
            File file = new File("users.json");

            // Check if the file exists and read its content
            if (!file.exists()) {
                System.out.println("User file not found.");
                return false;
            }
            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);

            // Iterate through the users array to find the current user and update the password
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(_username)) {
                    user.put("password", _password);
                    break; // Exit the loop once the user is found and updated
                }
            }

            // Write the updated JSON array back to the file
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(usersArray.toString());
            }
            return true; // Return true to indicate the password was successfully changed
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false; // Return false to indicate the password change failed
        }
    }
}
