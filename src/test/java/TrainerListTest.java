package test.java;

import main.java.memoranda.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * tests for the trainer list class
 */
public class TrainerListTest {
    /**
     * set up for the trainer methods needing to read from users.json
     */
    @Before
    public void setUp() throws IOException {
        PrintWriter writer = new PrintWriter("users.json", StandardCharsets.UTF_8);
        writer.println("[{\"endAvailability\":\"19\",\"startAvailability\":\"8\",\"password\":\"Trainer1\"," +
                "\"trainingRank\":\"NO_BELT\",\"beltRank\":\"WHITE\",\"userType\":\"TRAINER\",\"username\":" +
                "\"Trainer1\"},{\"endAvailability\":\"19\",\"startAvailability\":\"8\",\"password\":\"Trainer2\"," +
                "\"trainingRank\":\"NO_BELT\",\"beltRank\":\"WHITE\",\"userType\":\"TRAINER\",\"username\":" +
                "\"Trainer2\"}]");
        writer.close();

    }

    /**
     * tests getting a trainer given their name
     */
    @Test
    public void getTrainerTest() {
        TrainerList trainerList = new TrainerList();
        Trainer trainer = new Trainer("Trainer1", BeltValue.WHITE, BeltValue.NO_BELT, 8, 19);
        assertEquals(trainerList.getTrainer("Trainer1").getTrainerName(), trainer.getTrainerName());
        assertEquals(trainerList.getTrainer("Trainer1").getBeltRank(), trainer.getBeltRank());
        assertEquals(trainerList.getTrainer("Trainer1").getTrainingRank(), trainer.getTrainingRank());
    }

    /**
     * tests getting all trainers available
     */
    @Test
    public void getTrainersTest() {
        Vector<Trainer> tvector = new Vector<>();
        TrainerList trainerList = new TrainerList();
        tvector.add(new Trainer("Trainer1", BeltValue.WHITE, BeltValue.NO_BELT, 8, 19));
        tvector.add(new Trainer("Trainer2", BeltValue.WHITE, BeltValue.NO_BELT, 8, 19));

        assertEquals(trainerList.getTrainers().get(0).getTrainerName(), tvector.get(0).getTrainerName());
        assertEquals(trainerList.getTrainers().get(0).getBeltRank(), tvector.get(0).getBeltRank());
        assertEquals(trainerList.getTrainers().get(0).getTrainingRank(), tvector.get(0).getTrainingRank());

        assertEquals(trainerList.getTrainers().get(1).getTrainerName(), tvector.get(1).getTrainerName());
        assertEquals(trainerList.getTrainers().get(1).getBeltRank(), tvector.get(1).getBeltRank());
        assertEquals(trainerList.getTrainers().get(1).getTrainingRank(), tvector.get(1).getTrainingRank());
    }

    @Test
    public void setTrainerStartAvailabilityTest() throws IOException {
        TrainerList trainerList = new TrainerList();
        boolean result = trainerList.setTrainerStartAvailability("Trainer1", 10);
        assertTrue(result);


        Trainer updatedTrainer = trainerList.getTrainer("Trainer1");
        assertEquals(10, updatedTrainer.getStartAvailability());
    }

    @Test
    public void setTrainerEndAvailabilityTest() throws IOException {
        TrainerList trainerList = new TrainerList();
        boolean result = trainerList.setTrainerEndAvailability("Trainer2", 20);
        assertTrue(result);

        Trainer updatedTrainer = trainerList.getTrainer("Trainer2");
        assertEquals(20, updatedTrainer.getEndAvailability());
    }

    @Test
    public void testGetTrainersAtAvailableTime(){
        User.signUp("testTrainer", "testPassword", UserType.TRAINER);
        TrainerList t = new TrainerList();

        t.setTrainerStartAvailability("testTrainer", 10);
        t.setTrainerEndAvailability("testTrainer", 14);

        Vector<Trainer> availableTrainers = t.getTrainersAvailableAtTime(12);
        assertTrue("Trainer should be available", availableTrainers.size() > 1);
    }
}
