package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        for (int i = 0; i < field.getColumnCount(); i++) {
            System.out.printf("%s%d", "\t", i);
        }
        System.out.println();
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.printf("%c%s", row + 65, "\t");
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                if (tile.getState() == Tile.State.MARKED) {
//                    System.out.printf("%c%s", 'M', "\t");
                    System.out.print('M');
                } else if (tile.getState() == Tile.State.CLOSED) {
//                    System.out.printf("%c%s", '-', "\t");
                    System.out.print('-');
                } else {
                    System.out.print(field.getTile(row, column));

                }
                System.out.print("\t");

            }
            System.out.println();
//        throw new UnsupportedOperationException("Method update not yet implemented");
        }
    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        throw new UnsupportedOperationException("Method processInput not yet implemented");
    }
}
