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
    private BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;
    private Settings setting;

    /**
     * Constructor.
     */
    private Minesweeper() {
        Settings.EXPERT.save();

        instance = this;
        userInterface = new ConsoleUI();
        startMillis = System.currentTimeMillis();
        setting = Settings.load();
        Field field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());
        userInterface.newGameStarted(field);
    }

    public static Minesweeper getInstance() {
        if (instance == null) {
            new Minesweeper();
        }
        return instance;
    }

    public int getPlayingSeconds() {
        long currentTime = System.currentTimeMillis();
        return (int) (currentTime - startMillis) / 1000;
    }

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
        this.setting.save();
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
