package main.java.memoranda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
public class TrainerList {

    public TrainerList() {}

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
                    trainers.add(new Trainer(user.getString("username"), getRank(user.getString("beltRank")), getRank(user.getString("trainingRank"))));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trainers;
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
                    trainer = new Trainer(user.getString("username"), getRank(user.getString("beltRank")), getRank(user.getString("trainingRank")));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trainer;
    }

    private BeltValue getRank(String userRank) {
        BeltValue rank = null;
        switch(userRank) {
            case "NO_BELT":
                rank = BeltValue.NO_BELT;
                break;
            case "WHITE":
                rank = BeltValue.WHITE;
                break;
            case "YELLOW":
                rank = BeltValue.YELLOW;
                break;
            case "ORANGE":
                rank = BeltValue.ORANGE;
                break;
            case "PURPLE":
                rank = BeltValue.PURPLE;
                break;
            case "BLUE":
                rank = BeltValue.BLUE;
                break;
            case "BLUE_STRIPE":
                rank = BeltValue.BLUE_STRIPE;
                break;
            case "GREEN":
                rank = BeltValue.GREEN;
                break;
            case "GREEN_STRIPE":
                rank = BeltValue.GREEN_STRIPE;
                break;
            case "BROWN1":
                rank = BeltValue.BROWN1;
                break;
            case "BROWN2":
                rank = BeltValue.BROWN2;
                break;
            case "BROWN3":
                rank = BeltValue.BROWN3;
                break;
            case "BLACK1":
                rank = BeltValue.BLACK1;
                break;
            case "BLACK2":
                rank = BeltValue.BLACK2;
                break;
            case "BLACK3":
                rank = BeltValue.BLACK3;
                break;
        }

        return rank;
    }

}
