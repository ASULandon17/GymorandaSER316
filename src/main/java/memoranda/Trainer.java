package main.java.memoranda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
/**
 * Trainer class to construct a trainer object
 * Trainer's have a name, beltRank and a trainingRank
 */
public class Trainer {
    private final String trainerName;
    private BeltValue beltRank;
    private BeltValue trainingRank;

    /**
     * Generic constructor for the Trainer class
     */
    public Trainer() {
        this.trainerName = "Unknown";
        this.beltRank = BeltValue.WHITE;
        this.trainingRank = BeltValue.WHITE;
    }

    /**
     * Constructor for the Trainer class
     * @param trainerName Trainer's name
     * @param beltRank Trainer's belt rank
     * @param trainingRank Trainer's training level
     */
    public Trainer(String trainerName, BeltValue beltRank, BeltValue trainingRank) {
        this.trainerName = trainerName;
        this.beltRank = beltRank;
        this.trainingRank = trainingRank;
    }

    public Trainer getTrainer(String name) {
        Trainer trainer = null;
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return null;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray usersArray = new JSONArray(content);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("username").equals(name)) {
                    trainer = new Trainer(user.getString("username"), user.getString("beltRank"), user.getString("trainingRank"));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trainer;
    }


    public Vector<Trainer> getTrainers() {
        Vector<Trainer> trainers = new Vector<>();
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return null;
            }

            String content = new String(Files.readAllBytes(Paths.get("users.json")));

            JSONArray usersArray = new JSONArray(content);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("userType").equals("TRAINER")) {
                        trainers.add(new Trainer(user.getString("username"), user.getString("beltRank"), user.getString("trainingRank")));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trainers;
    }


    public String getTrainerName() {
        return this.trainerName;
    }

    public BeltValue getBeltRank() {
        return this.beltRank;
    }

    public BeltValue getTrainingRank() {
        return this.trainingRank;
    }

    /**
     * Increases the Trainer's belt rank
     */
    public void increaseBeltRank() {
        this.beltRank = beltRank.increaseBelt();
    }

    /**
     * Increases the Trainer's training level
     */
    public void increaseTrainingRank() {
        this.trainingRank = trainingRank.increaseBelt();
    }
}
