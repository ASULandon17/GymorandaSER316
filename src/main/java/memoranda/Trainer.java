package main.java.memoranda;

/**
 * Trainer class to construct a trainer object.
 * Trainer's have a name, beltRank and a trainingRank.
 */
public class Trainer {
    private final String trainerName;
    private BeltValue beltRank;
    private BeltValue trainingRank;
    // The earliest time the Trainer can start work. Ranges from 8(8:00am) to 19(7:00pm)
    private int startAvailability;
    // The latest time the Trainer will stay. Ranges from 8(8:00am) to 19(7:00pm)
    private int endAvailability;

    /**
     * Generic constructor for the Trainer class.
     */
    public Trainer() {
        this.trainerName = "Unknown";
        this.beltRank = BeltValue.WHITE;
        this.trainingRank = BeltValue.WHITE;
        this.startAvailability = 8;
        this.endAvailability = 19;
    }

    /**
     * Constructor for the Trainer class.
     *
     * @param trainerName Trainer's name
     * @param beltRank Trainer's belt rank
     * @param trainingRank Trainer's training level
     */
    public Trainer(String trainerName, BeltValue beltRank, BeltValue trainingRank,
                   int startAvailability, int endAvailability) {
        this.trainerName = trainerName;
        this.beltRank = beltRank;
        this.trainingRank = trainingRank;
        this.startAvailability = startAvailability;
        this.endAvailability = endAvailability;
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

    public int getStartAvailability() {
        return this.startAvailability;
    }

    public int getEndAvailability() {
        return this.endAvailability;
    }

    public void setStartAvailability(int start) {
        this.startAvailability = start;
    }

    public void setEndAvailability(int end) {
        this.endAvailability = end;
    }

    /**
     * Increases the Trainer's belt rank.
     */
    public void increaseBeltRank() {
        this.beltRank = beltRank.increaseBelt();
    }

    /**
     * Increases the Trainer's training level.
     */
    public void increaseTrainingRank() {
        this.trainingRank = trainingRank.increaseBelt();
    }
}
