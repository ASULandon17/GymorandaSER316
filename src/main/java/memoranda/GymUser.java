package main.java.memoranda;

import org.json.JSONObject;

public class GymUser {

    private String username;
    private String password;
    private BeltValue beltRank;
    private UserType userType;
    private BeltValue trainingRank;
    private int startAvailability;
    private int endAvailability;


    public GymUser(JSONObject jsonObject) {

        username = jsonObject.getString("username");
        password = jsonObject.getString("password");
        beltRank =  BeltValue.valueOf(jsonObject.getString("beltRank"));
        userType = UserType.valueOf(jsonObject.getString("userType"));
        trainingRank = BeltValue.valueOf(jsonObject.getString("trainingRank"));
        startAvailability = jsonObject.getInt("startAvailability");
        endAvailability = jsonObject.getInt("endAvailability");

    }

    /**
     * Allows the owner to upgrade an existing user to a trainer.
     */
    public void becomeTrainer() {
        if (userType == UserType.MEMBER) {

            this.userType = userType.becomeTrainer();
        }

    }

    private void updateUserName(String newUsername) {
        this.username = newUsername;
    }

    private void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void increaseBeltRank() {
        this.beltRank = beltRank.increaseBelt();
    }

    public void increaseTrainingRank() {
        this.trainingRank = trainingRank.increaseBelt();
    }

    private void updateStartAvailability(int startHour) {
        this.startAvailability = startHour;
    }

    private void updateEndAvailability(int endHour) {
        this.endAvailability = endHour;
    }

    /**
     * Updates a trainer's start and end availability.
     * @param startHour beginning of availability
     * @param endHour   hour that availability ends
     */
    public void setAvailability(int startHour, int endHour) {

        if (userType == UserType.TRAINER) {
            this.updateStartAvailability(startHour);
            this.updateEndAvailability(endHour);
        } else {
            System.out.println("User is not a trainer");
        }


    }



    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BeltValue getBeltRank() {
        return beltRank;
    }

    public UserType getUserType() {
        return userType;
    }

    public BeltValue getTrainingRank() {
        return trainingRank;
    }

    public int getStartAvailability() {
        return startAvailability;
    }

    public int getEndAvailability() {
        return endAvailability;
    }







}
