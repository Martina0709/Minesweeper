package minesweeper;

import minesweeper.consoleui.ConsoleUI;
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

    /**
     * Constructor.
     */
    private Minesweeper() {
        userInterface = new ConsoleUI();
        startMillis = System.currentTimeMillis();

        Field field = new Field(9, 9, 10);
        userInterface.newGameStarted(field);
    }

    public int getPlayingSeconds() {
        long currentTime = System.currentTimeMillis();
        return (int) (currentTime-startMillis) / 1000;
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
