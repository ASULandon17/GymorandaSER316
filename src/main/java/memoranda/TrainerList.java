package main.java.memoranda;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Contains methods that get either a trainer or all trainers out of ones that are signed up in the
 * system.
 */
public class TrainerList {
    /**
     * Constructor for creating a new TrainerList.
     */
    public TrainerList() {
    }

    /**
     * getTrainers searches for currently signed up trainers and adds them all to a list to be
     * returned.
     *
     * @return a vector filled with the currently signed up trainers
     */
    public Vector<Trainer> getTrainers() {
        Vector<Trainer> trainers = new Vector<>();
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return null;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")),
                    StandardCharsets.UTF_8);

            JSONArray usersArray = new JSONArray(content);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("userType").equals("TRAINER")) {
                    trainers.add(new Trainer(user.getString("username"),
                            BeltValue.valueOf(user.getString("beltRank")),
                            BeltValue.valueOf(user.getString("trainingRank")),
                            user.getInt("startAvailability"),
                            user.getInt("endAvailability")));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trainers;
    }

    /**
     * Searches for a trainer given their name.
     *
     * @param name the name of the specified trainer
     * @return returns a trainer's information if found
     */
    public Trainer getTrainer(String name) {
        Trainer trainer = null;
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return null;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")),
                    StandardCharsets.UTF_8);
            JSONArray usersArray = new JSONArray(content);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(name)
                        && user.getString("userType").equals("TRAINER")) {
                    trainer = new Trainer(user.getString("username"),
                            BeltValue.valueOf(user.getString("beltRank")),
                            BeltValue.valueOf(user.getString("trainingRank")),
                            user.getInt("startAvailability"),
                            user.getInt("endAvailability"));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trainer;
    }

    /**
     * Method to update the trainers start availability.
     *
     * @param name              trainer name
     * @param startAvailability trainer's start availability
     * @return success of update
     */
    public boolean setTrainerStartAvailability(String name, int startAvailability) {
        return updateTrainerAvailability(name, startAvailability, true);
    }

    /**
     * Update the trainers end availability.
     *
     * @param name            trainer name
     * @param endAvailability end of trainer's availability window.
     * @return success of update
     */
    public boolean setTrainerEndAvailability(String name, int endAvailability) {
        return updateTrainerAvailability(name, endAvailability, false);
    }

    /**
     * Updates the trainer by name with their availability.
     *
     * @param name         trainer name
     * @param availability hours available
     * @param isStart      decides whether to update start or end availability
     * @return success of update
     */
    private boolean updateTrainerAvailability(String name, int availability, boolean isStart) {
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return false;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")),
                    StandardCharsets.UTF_8);
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(name)
                        && user.getString("userType").equals("TRAINER")) {
                    if (isStart) {
                        user.put("startAvailability", availability);
                    } else {
                        user.put("endAvailability", availability);
                    }
                    // Write the updated JSON array back to the file
                    Files.write(Paths.get("users.json"),
                            usersArray.toString().getBytes(StandardCharsets.UTF_8),
                            StandardOpenOption.WRITE);
                    return true;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * Gets all trainers that are available to work at a given time.
     *
     * @param time for the class.
     * @return vector of trainers that are available during that time.
     */
    public Vector<Trainer> getTrainersAvailableAtTime(int time) {
        Vector<Trainer> availableTrainers = new Vector<>();
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return availableTrainers;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")),
                    StandardCharsets.UTF_8);

            JSONArray usersArray = new JSONArray(content);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("userType").equals("TRAINER")) {
                    int startAvailability = user.getInt("startAvailability");
                    int endAvailability = user.getInt("endAvailability");

                    // Check if the given time is within the trainer's availability range
                    if (time >= startAvailability && time <= endAvailability) {
                        Trainer trainer = new Trainer(user.getString("username"),
                                BeltValue.valueOf(user.getString("beltRank")),
                                BeltValue.valueOf(user.getString("trainingRank")),
                                startAvailability,
                                endAvailability);
                        availableTrainers.add(trainer);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return availableTrainers;
    }


}
