package main.java.memoranda;

/**
 * Enum class for the belt ranks
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
     * Increases the belt rank by one stage Does not do anything if there are no more stages
     *
     * @return new belt rank
     */
    public BeltValue increaseBelt() {
        switch (this) {
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
