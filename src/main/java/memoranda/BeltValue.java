package main.java.memoranda;

/**
 * Enum class for the belt ranks
 */
public enum BeltValue {
    NO_BELT, WHITE, YELLOW, ORANGE, PURPLE, BLUE, BLUE_STRIPE, GREEN, GREEN_STRIPE,
    BROWN1, BROWN2, BROWN3, BLACK1, BLACK2, BLACK3;

    /**
     * Increases the belt rank by one stage
     * Does not do anything if there are no more stages
     * @return new belt rank
     */
    public BeltValue increaseBelt() {
        switch(this) {
            case NO_BELT:
                return WHITE;
            case WHITE:
                return YELLOW;
            case YELLOW:
                return ORANGE;
            case ORANGE:
                return PURPLE;
            case PURPLE:
                return BLUE;
            case BLUE:
                return BLUE_STRIPE;
            case BLUE_STRIPE:
                return GREEN;
            case GREEN:
                return GREEN_STRIPE;
            case GREEN_STRIPE:
                return BROWN1;
            case BROWN1:
                return BROWN2;
            case BROWN2:
                return BROWN3;
            case BROWN3:
                return BLACK1;
            case BLACK1:
                return BLACK2;
            case BLACK2:
                return BLACK3;
            case BLACK3:
                System.out.println("Cannot increase rank any further.");
                return this;
            default:
                System.out.println("Error increasing belt rank.");
                return this;
        }
    }
}
