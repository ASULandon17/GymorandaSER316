package main.java.memoranda;

/**
 * Enum class for the belt ranks.
 */
public enum BeltValue {
    NO_BELT(false),

    WHITE(false),
    YELLOW(false),
    ORANGE(false),
    PURPLE(false),

    BLUE(true),
    BLUE_STRIPE(true),

    GREEN(true),
    GREEN_STRIPE(true),
    BROWN1(true),
    BROWN2(true),
    BROWN3(true),
    BLACK1(true),
    BLACK2(true),
    BLACK3(true);

    private final boolean isAdvanced;


    BeltValue(boolean isAdvanced) {
        this.isAdvanced = isAdvanced;
    }


    public boolean isAdvanced() {
        return isAdvanced;
    }

    /**
     * Increases the belt rank by one stage Does not do anything if there are no more stages.
     *
     * @return new belt rank
     */
    public BeltValue increaseBelt() {

        // get next rank
        int nextRank = this.ordinal() + 1;

        // as long as the next rank is within bounds of the enum array, return increased rank.
        if (nextRank < BeltValue.values().length) {
            return BeltValue.values()[nextRank];

            // otherwise, return current rank and display error message
        } else {
            System.out.println("User is already at the max rank.");
            return this;


        }
    }
}
