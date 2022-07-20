package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;
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
            if (field.getState() == GameState.SOLVED) {
                System.out.println("Vyhrali ste!");
                System.out.println(Minesweeper.getInstance().getBestTimes());
                System.exit(0);
            } else if (field.getState() == GameState.FAILED) {
                System.out.println("Prehrali ste.");
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        System.out.printf("Cas hrania: %d%n",
                Minesweeper.getInstance().getPlayingSeconds()
        );
        System.out.printf("Pocet neoznacenych min: %d%n", field.getRemainingMineCount());
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
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        if (input.equals("X")) {
            System.exit(0);
            return;
        }

        Pattern pattern = Pattern.compile("M[A-I][0-8]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        boolean matchFound = matcher.find();
        if (matchFound) {
            int row = input.charAt(1) - 65;
            int column = input.charAt(2) - 48;
            field.markTile(row, column);
            return;
        }

        pattern = Pattern.compile("O[A-I][0-8]", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input);
        matchFound = matcher.find();
        if (matchFound) {
            int row = input.charAt(1) - 65;
            int column = input.charAt(2) - 48;
            field.openTile(row, column);
            return;
        }

        throw new WrongFormatException("Zadali ste nespravny vstup.");
    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.print("Zadajte X pre ukoncenie hry\nM[A-I][0-8] pre oznacenie dlazdice\nO[A-I][0-8] pre odkrytie dlazdice\n");

        String input = readLine();

        try {
            handleInput(input);
        } catch (WrongFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
