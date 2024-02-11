package test.java;

import main.java.memoranda.BeltValue;
import main.java.memoranda.Trainer;
import main.java.memoranda.TrainerList;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

/**
 * tests for the trainer list class
 */
public class TrainerListTest {
    /**
     * set up for the trainer methods needing to read from users.json
     */
    @Before
    public void setUp() throws IOException {
        File file = new File("users.json");
        PrintWriter writer = new PrintWriter("users.json", StandardCharsets.UTF_8);
        writer.println("[{\"password\":\"Trainer1\",\"trainingRank\":\"NO_BELT\",\"beltRank\":\"WHITE\",\"userType\":\"TRAINER\",\"username\":\"Trainer1\"},{\"password\":\"Trainer2\",\"trainingRank\":\"NO_BELT\",\"beltRank\":\"WHITE\",\"userType\":\"TRAINER\",\"username\":\"Trainer2\"}]");
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
}
