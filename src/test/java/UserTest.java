package test.java;

import org.json.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.memoranda.User;
import main.java.memoranda.BeltValue;
import main.java.memoranda.UserType;

public class UserTest extends JSONTest{
    // Generic variables for testing
    String name = "Unknown";
    String password = "password";
    UserType userType = UserType.MEMBER;


    /**
     * Test Case to check if Member promoting to Trainer executes properly
     */
    @Test
    public void memberToTrainerTest() {
        User.signUp(name, password, userType);
        assertEquals("Return value expected to be 0. Error upgrading from Member", 0, User.becomeTrainer());
    }

    /**
     * Test Case to check if Trainer promoting to Trainer executes properly
     */
    @Test
    public void trainerToTrainerTest() {
        userType = UserType.TRAINER;
        User.signUp(name, password, userType);
        assertEquals("Return value expected to be -1. Error upgrading from Trainer", -1, User.becomeTrainer());
    }

    /**
     * Test Case to check if Owner promoting to Trainer executes properly
     */
    @Test
    public void ownerToTrainerTest() {
        userType = UserType.OWNER;
        User.signUp(name, password, userType);
        assertEquals("Return value expected to be 1. Error upgrading from Owner", 1, User.becomeTrainer());
    }

    /**
     * Test Case to check if increasing belt rank executes properly
     */
    @Test
    public void increaseBeltRankTest() {
        User.signUp(name, password, userType);
        User.increaseBeltRank();
        assertEquals("User has the wrong belt rank after increasing one stage from WHITE", BeltValue.YELLOW, User.getBeltRank());
    }

    /**
     * Test Case to check if increasing belt rank executes properly when there is no higher rank
     */
    @Test
    public void increaseBeltRankMaxTest() {
        User.signUp(name, password, userType);
        while(User.getBeltRank() != BeltValue.BLACK3) {
            User.increaseBeltRank();
        }
        User.increaseBeltRank();
        assertEquals("User has the wrong belt rank after increasing one stage from BLACK3", BeltValue.BLACK3, User.getBeltRank());
    }

    /**
     * Test Case to check if increasing training rank executes properly
     */
    @Test
    public void increaseTrainingRankTest() {
        User.signUp(name, password, userType);
        User.increaseTrainingRank();
        assertEquals("User has the wrong training rank after increasing one stage from NO_BELT", BeltValue.WHITE, User.getTrainingRank());
    }

    /**
     * Test Case to check if increasing training rank executes properly when there is no higher rank
     */
    @Test
    public void increaseTrainingRankMaxTest() {
        User.signUp(name, password, userType);
        while(User.getTrainingRank() != BeltValue.BLACK3) {
            User.increaseTrainingRank();
        }
        User.increaseTrainingRank();
        assertEquals("User has the wrong training rank after increasing one stage from BLACK3", BeltValue.BLACK3, User.getTrainingRank());
    }

    /**
     * Test Case to check if changing start availability executes properly
     */
    @Test
    public void setStartAvailabilityTest() {
        User.signUp(name, password, userType);
        User.setStartAvailability(10);
        assertEquals("User has the wrong start availability after changing", 10, User.getStartAvailability());
    }

    /**
     * Test Case to check if changing end availability executes properly
     */
    @Test
    public void setEndAvailabilityTest() {
        User.signUp(name, password, userType);
        User.setEndAvailability(16);
        assertEquals("User has the wrong end availability after changing", 16, User.getEndAvailability());
    }

    /**
     * Test case for changing the user's password.
     */
    @Test
    public void testChangePassword(){
        User.signUp(name, password, userType);

        // Define a new password different from the initial one
        String newPassword = "newPassword123";

        assertTrue("Password change should return true", User.changePassword(newPassword));

        assertTrue("User should be able to log in with the new password", User.login(name, newPassword));
    }

    /**
     * Test Case to check if setting training rank executes properly
     */
    @Test
    public void setTrainingRankTest() {
        User.signUp(name, password, UserType.TRAINER);
        User.setTrainingRank(BeltValue.BLACK2);
        assertEquals("User has the wrong training rank after changing", BeltValue.BLACK2, User.getTrainingRank());
    }
}
