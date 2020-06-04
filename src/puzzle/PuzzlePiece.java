package puzzle;

/**
 * Puzzle piece inside the puzzle
 */
public final class PuzzlePiece {
    // PRIVATE VARIABLES //
    private boolean isShowing;
    private int value;

    /**
     * Constructor
     *
     * Defaults to 0 and false when initialized
     */
    public PuzzlePiece() {
        this.value = 0;
        this.isShowing = false;
    }

    /** Return the value held by the puzzle piece */
    public int getValue() {
        return value;
    }

    /** Return if the puzzle piece is showing the value to the user */
    public boolean getShowing() {
        return isShowing;
    }

    /**
     * Sets a new value to the puzzle piece given new value
     * @param value: new (int) value for puzzle piece
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets either True/False to displaying value to user
     * @param isShowing: (Boolean) value to see if user should see the value or not
     */
    public void setShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }
}
