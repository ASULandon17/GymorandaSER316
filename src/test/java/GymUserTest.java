package test.java;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import main.java.memoranda.BeltValue;
import main.java.memoranda.GymUser;
import main.java.memoranda.User;
import main.java.memoranda.UserType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class GymUserTest {

    /**
     * Test to check the functionality of the setAvailability method. Checks to see if the method
     * functions correctly for MEMBER.
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
     * Test to check the functionality of the setAvailability method. Checks to see if the method
     * functions correctly for TRAINER.
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
     * Test to check the functionality of the setAvailability method. Checks to see if the method
     * functions correctly for OWNER.
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

    /**
     * Ensure a member is upgraded to a trainer properly.
     */
    @Test
    public void testBecomeTrainerOnMember() {
        User.signUp("test member", "password", UserType.MEMBER);

        GymUser gymUser = User.getUser("test member");

        assert gymUser != null;
        assertEquals("User did not start as a member",
                UserType.MEMBER, gymUser.getUserType());

        // apply trainer upgrade but don't save to file
        gymUser.becomeTrainer();

        assertEquals("Member did not upgrade to trainer",
                UserType.TRAINER, gymUser.getUserType());
    }

    /**
     * Ensure a trainer remains a trainer.
     */
    @Test
    public void testBecomeTrainerOnTrainer() {
        User.signUp("test trainer", "password", UserType.TRAINER);

        GymUser gymUser = User.getUser("test trainer");

        assert gymUser != null;
        assertEquals("User did not start as a trainer",
                UserType.TRAINER, gymUser.getUserType());

        // apply trainer upgrade but don't save to file
        gymUser.becomeTrainer();

        assertEquals("Member is no longer a trainer",
                UserType.TRAINER, gymUser.getUserType());

    }

    /**
     * Ensure an owner is not downgraded to a trainer.
     */
    @Test
    public void testBecomeTrainerOnOwner() {
        User.signUp("test owner", "password", UserType.OWNER);

        GymUser gymUser = User.getUser("test owner");

        assert gymUser != null;
        assertEquals("User did not start as an owner",
                UserType.OWNER, gymUser.getUserType());

        // apply trainer upgrade but don't save to file
        gymUser.becomeTrainer();

        assertEquals("Member is no longer an owner",
                UserType.OWNER, gymUser.getUserType());


    }

    /**
     * Ensure training rank is increased properly.
     */
    @Test
    public void testIncreaseTrainingRank() {
        User.signUp("new trainer", "password", UserType.TRAINER);

        GymUser gymUser = User.getUser("new trainer");
        assert gymUser != null;

        gymUser.increaseTrainingRank();

        assertEquals("training belt rank not increased",
                BeltValue.WHITE, gymUser.getTrainingRank());
    }

    /**
     * Ensure belt rank is increased properly.
     */
    @Test
    public void testIncreaseBeltRank() {
        User.signUp("new member", "password", UserType.MEMBER);

        GymUser gymUser = User.getUser("new member");
        assert gymUser != null;

        gymUser.increaseBeltRank();

        assertEquals("training belt rank not increased",
                BeltValue.YELLOW, gymUser.getBeltRank());
    }


    @Test
    public void testUpdateUserName() {
        User.signUp("change my name", "password", UserType.MEMBER);
        GymUser gymUser = User.getUser("change my name");
        assert gymUser != null;
        gymUser.updateUserName("new username");

        assertEquals("username did not update",
                "new username", gymUser.getUsername());
    }

    @Test
    public void testUpdatePassword() {
        User.signUp("needs new password", "old password", UserType.MEMBER);
        GymUser gymUser = User.getUser("needs new password");
        assert gymUser != null;
        gymUser.updatePassword("new password");

        assertEquals("password did not update",
                "new password", gymUser.getPassword());
    }
}
