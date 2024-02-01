package main.java.memoranda;

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
