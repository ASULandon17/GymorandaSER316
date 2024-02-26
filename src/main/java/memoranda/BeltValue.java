package main.java.memoranda;

/**
 * Enum class for the belt ranks.
 */
public enum BeltValue {

    // Beginner belts
    NO_BELT,

    WHITE,
    YELLOW,
    ORANGE,
    PURPLE,

    // Advanced belts
    BLUE,
    BLUE_STRIPE,
    GREEN,
    GREEN_STRIPE,
    BROWN1,
    BROWN2,
    BROWN3,
    BLACK1,
    BLACK2,
    BLACK3;


    public boolean isAdvanced() {
        return this.ordinal() >= BLUE.ordinal();
    }

    /**
     * Used to compare a user's training rank to see if they're eligible to teach a course.
     *
     * @return true or false
     */
    public boolean canTrainCourses() {
        return this.ordinal() >= BLACK2.ordinal();
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
