package test.java;

import main.java.memoranda.GymUser;
import org.json.*;
import static org.junit.Assert.*;

import org.junit.Test;

import main.java.memoranda.User;
import main.java.memoranda.UserType;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GymUserTest {
    /**
     * Test to check the functionality of the setAvailability method.
     * Checks to see if the method functions correctly for MEMBER.
     */
    @Test
    public void setAvailabilityMemberTest() {
        try {
            User.signUp("TestMember", "password", UserType.MEMBER);

            GymUser gymUser = null;

            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("username").equals("TestMember")) {
                    gymUser = new GymUser(user);
                }
            }

            assert gymUser != null;
            int start = gymUser.getStartAvailability();
            int end = gymUser.getEndAvailability();

            gymUser.setAvailability(12, 1);

            assertEquals("The start availability should not be changed for Members", start,
                    gymUser.getStartAvailability());
            assertEquals("The end availability should not be changed for Members", end,
                    gymUser.getEndAvailability());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test to check the functionality of the setAvailability method.
     * Checks to see if the method functions correctly for TRAINER.
     */
    @Test
    public void setAvailabilityTrainerTest() {
        try {
            User.signUp("TestTrainer", "password", UserType.TRAINER);

            GymUser gymUser = null;

            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("username").equals("TestTrainer")) {
                    gymUser = new GymUser(user);
                }
            }

            assert gymUser != null;
            gymUser.setAvailability(12, 1);

            assertEquals("The start availability should be changed for Trainers", 12,
                    gymUser.getStartAvailability());
            assertEquals("The end availability should be changed for Trainers", 1,
                    gymUser.getEndAvailability());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test to check the functionality of the setAvailability method.
     * Checks to see if the method functions correctly for OWNER.
     */
    @Test
    public void setAvailabilityOwnerTest() {
        try {
            User.signUp("TestOwner", "password", UserType.OWNER);

            GymUser gymUser = null;

            String content = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("username").equals("TestOwner")) {
                    gymUser = new GymUser(user);
                }
            }

            assert gymUser != null;
            int start = gymUser.getStartAvailability();
            int end = gymUser.getEndAvailability();

            gymUser.setAvailability(12, 1);

            assertEquals("The start availability should not be changed for Owners", start,
                    gymUser.getStartAvailability());
            assertEquals("The end availability should not be changed for Owners", end,
                    gymUser.getEndAvailability());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
