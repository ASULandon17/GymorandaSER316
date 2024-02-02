import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.java.memoranda.User;
import main.java.memoranda.BeltValue;
import main.java.memoranda.UserType;

public class UserTest {
    // Generic variables for testing
    String name = "Unknown";
    String password = "password";
    BeltValue beltRank = BeltValue.WHITE;
    UserType userType = UserType.MEMBER;
    BeltValue trainingRank = BeltValue.NO_BELT;

    /**
     * Test Case to check if Member promoting to Trainer executes properly
     */
    @Test
    public void memberToTrainerTest() {
        User user = new User(name, password, beltRank, userType, trainingRank);
        assertEquals("Return value expected to be 0. Error upgrading from Member", 0, user.becomeTrainer());
    }

    /**
     * Test Case to check if Trainer promoting to Trainer executes properly
     */
    @Test
    public void trainerToTrainerTest() {
        userType = UserType.TRAINER;
        User user = new User(name, password, beltRank, userType, trainingRank);
        assertEquals("Return value expected to be -1. Error upgrading from Trainer", -1, user.becomeTrainer());
    }

    /**
     * Test Case to check if Owner promoting to Trainer executes properly
     */
    @Test
    public void ownerToTrainerTest() {
        userType = UserType.OWNER;
        User user = new User(name, password, beltRank, userType, trainingRank);
        assertEquals("Return value expected to be 1. Error upgrading from Owner", 1, user.becomeTrainer());
    }

    /**
     * Test Case to check if increasing belt rank executes properly
     */
    @Test
    public void increaseBeltRankTest() {
        User user = new User(name, password, beltRank, userType, trainingRank);
        user.increaseBeltRank();
        assertEquals("User has the wrong belt rank after increasing one stage from WHITE", BeltValue.YELLOW, user.getBeltRank());
    }

    /**
     * Test Case to check if increasing belt rank executes properly when there is no higher rank
     */
    @Test
    public void increaseBeltRankMaxTest() {
        beltRank = BeltValue.BLACK3;
        User user = new User(name, password, beltRank, userType, trainingRank);
        user.increaseBeltRank();
        assertEquals("User has the wrong belt rank after increasing one stage from BLACK3", BeltValue.BLACK3, user.getBeltRank());
    }

    /**
     * Test Case to check if increasing training rank executes properly
     */
    @Test
    public void increaseTrainingRankTest() {
        User user = new User(name, password, beltRank, userType, trainingRank);
        user.increaseTrainingRank();
        assertEquals("User has the wrong training rank after increasing one stage from NO_BELT", BeltValue.WHITE, user.getTrainingRank());
    }

    /**
     * Test Case to check if increasing training rank executes properly when there is no higher rank
     */
    @Test
    public void increaseTrainingRankMaxTest() {
        trainingRank = BeltValue.BLACK3;
        User user = new User(name, password, beltRank, userType, trainingRank);
        user.increaseTrainingRank();
        assertEquals("User has the wrong training rank after increasing one stage from BLACK3", BeltValue.BLACK3, user.getTrainingRank());
    }
}
