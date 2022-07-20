package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.BestTimes;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private UserInterface userInterface;
    private long startMillis;
    private BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;
        userInterface = new ConsoleUI();
        startMillis = System.currentTimeMillis();

        Field field = new Field(9, 9, 10);
        userInterface.newGameStarted(field);
    }

    public static Minesweeper getInstance() {
        if(instance == null) {
            new Minesweeper();
        }
        return instance;
    }

    public int getPlayingSeconds() {
        long currentTime = System.currentTimeMillis();
        return (int) (currentTime-startMillis) / 1000;
    }

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }
}
