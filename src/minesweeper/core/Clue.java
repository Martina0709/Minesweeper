package minesweeper.core;

/**
 * Clue tile.
 */
public class Clue  extends Tile {
    public int getValue() {
        return value;
    }
    /** Value of the clue. */
    private final int value;
    
    /**
     * Constructor.
     * @param value  value of the clue
     */
    public Clue(int value) {
        this.value = value;
    }
}
