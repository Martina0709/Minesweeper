package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Comment;
import entity.Rating;
import entity.Score;
import minesweeper.Minesweeper;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Tile;
import service.CommentServiceJDBC;
import service.RatingServiceJDBC;
import service.ScoreServiceJDBC;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field. MA1 OB99
     */
    private Field field;
    Pattern OPEN_MARK_PATTERN = Pattern.compile("([OM]{1})([A-Z]{1})([0-9]{1,2})");

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * name of the player
     */
    private String userName = "";

    private Settings setting;

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

        int gameScore = 0;

        this.field = field;

        while (userName.length() < 1 || userName.length() > 64) {
            System.out.println("Zadaj svoje meno(1-64 znakov):");
            userName = readLine();
        }

        System.out.println("Vyber obtiaznost:");
        System.out.println("(1) BEGINNER, (2) INTERMEDIATE, (3) EXPERT, (ENTER) NECHAT DEFAULT");
        String level = readLine();
        if (level != null && !level.equals("")) {
            try {
                int intLevel = Integer.parseInt(level);
                Settings s = switch (intLevel) {
                    case 2 -> Settings.INTERMEDIATE;
                    case 3 -> Settings.EXPERT;
                    default -> Settings.BEGINNER;
                };
                this.setting = s;
                this.setting.save();
                this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
            } catch (NumberFormatException e) {
                //empty naschval
            }
        }

        do {
            update();
            processInput();

            var fieldState = this.field.getState();

            if (fieldState == GameState.FAILED) {
                gameScore = this.field.getScore();
                System.out.println(userName + ", odkryl si minu. Prehral si. Tvoje skore je " + gameScore + ".");

                break;
            }
            if (fieldState == GameState.SOLVED) {
                gameScore = this.field.getScore();
                System.out.println(userName + ", vyhral si. Tvoje skore je " + gameScore + ".");

                break;
            }
        } while (true);
        addNewScore("minesweeper", userName, gameScore);
        printTop5Scores();
        addNewComment("minesweeper", userName);
        printComments();
        setNewRating("minesweeper", userName);
        getAverageRating("minesweeper");
        System.exit(0);

    }

    private void addNewScore(String game, String userName, int score) {
        Minesweeper.getInstance().getScoreService().addScore(new Score(game, userName, score, new Date()));
    }

    private void printTop5Scores() {
        List<Score> scores = Minesweeper.getInstance().getScoreService().getBestScores("minesweeper");
        for (int i = 0; i < scores.size(); i++) {
            if (i == 5) {
                break;
            }
            System.out.printf("Meno: %s, Body: %d%n", scores.get(i).getUsername(), scores.get(i).getPoints());
        }
    }

    private void addNewComment(String game, String userName) {
        Scanner scanner = new Scanner(System.in);
        String comment = "";
        while (comment.length() < 1 || comment.length() > 100) {
            System.out.println("Napiste komentar(1-100 znakov):");
            comment = scanner.next();
        }
        Minesweeper.getInstance().getCommentService().addComment(new Comment(game, userName, comment, new Date()));
    }

    private void printComments() {
        List<Comment> comments = Minesweeper.getInstance().getCommentService().getComments("minesweeper");
        for (Comment comment : comments) {
            System.out.printf("Meno: %s, komentar: %s%n", comment.getUserName(), comment.getComment());
        }
    }

    private void setNewRating(String game, String userName) {
        Scanner scanner = new Scanner(System.in);

        int rating = 0;
        while (rating <= 0 || rating > 5) {
            System.out.println("Napiste hodnotenie (1-5):");
            try {
                rating = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Nespravny vstup!");

            }
        }


        Minesweeper.getInstance().getRatingService().setRating(new Rating(game, userName, rating, new Date()));
    }

    private void getAverageRating(String game) {
        int averageRating = Minesweeper.getInstance().getRatingService().getAverageRating(game);
        System.out.printf("Priemerne hodnotenie hry je %d%n", averageRating);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //System.out.println("Metoda update():");
        System.out.printf("Cas hrania: %d%n",
                field.getPlayTimeInSeconds()
        );
        System.out.printf("Pocet poli neoznacenych ako mina je %s (pocet min: %s)%n", field.getRemainingMineCount(), field.getMineCount());

        //vypis horizontalnu os
        StringBuilder hornaOs = new StringBuilder("   ");
        for (int i = 0; i < field.getColumnCount(); i++) {
            hornaOs.append(String.format("%3s", i));
        }
        System.out.println(hornaOs);

        //vypis riadky so zvislo osou na zaciatku
        for (int r = 0; r < field.getRowCount(); r++) {
            System.out.printf("%3s", Character.toString(r + 65));
            for (int c = 0; c < field.getColumnCount(); c++) {
                System.out.printf("%3s", field.getTile(r, c));
            }
            System.out.println();
        }


    }

    @Override
    public void play() {
        setting = Settings.load();

        Field field = new Field(
                setting.getRowCount(),
                setting.getColumnCount(),
                setting.getMineCount()
        );

        newGameStarted(field);

    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("Zadaj svoj vstup.");
        System.out.println("Ocakavany vstup:  X - ukoncenie hry, M - mark, O - open, U - unmark. Napr.: MA1 - oznacenie dlazdice v riadku A a stlpci 1");
        String playerInput = readLine();

        if (playerInput.trim().equals('X')) {
            System.out.println("Ukoncujem hru");
            System.exit(0);
        }

        // overi format vstupu - exception handling
        try {
            handleInput(playerInput);
        } catch (WrongFormatException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            processInput();
        }
    }

    private void doOperation(char operation, char osYRow, int osXCol) {

        int osYRowInt = osYRow - 65;

        // M - oznacenie dlzadice
        if (operation == 'M') {
            field.markTile(osYRowInt, osXCol);

        }

        // O - Odkrytie dlazdice
        if (operation == 'O') {
            if (field.getTile(osYRowInt, osXCol).getState() == Tile.State.MARKED) {
                System.out.println("!!! Nie je mozne odkryt dlazdicu v stave MARKED");
                return;
            } else {
                field.openTile(osYRowInt, osXCol);
            }

        }

        System.out.println("Vykonal som pozadovanu operaciu");
    }

    private boolean isInputInBorderOfField(String suradnicaZvislaPismeno, String suradnicaHorizontalnaCislo) {
        boolean result = true;

        if ((int) suradnicaZvislaPismeno.charAt(0) >= (65 + field.getRowCount())) {
            result = false;
            System.out.print("!!! Pismeno prekracuje pocet riadkov.");
        }
        if (Integer.parseInt(suradnicaHorizontalnaCislo) >= field.getColumnCount()) {
            result = false;
            System.out.print(" !!! Cislo prekracuje pocet stlpcov.");

        }
        if (!result) {
            System.out.println(" Opakuj vstup.");
        }

        return result;
    }

    void handleInput(String playerInput) throws WrongFormatException {
        Matcher matcher1 = OPEN_MARK_PATTERN.matcher(playerInput);

        if (!OPEN_MARK_PATTERN.matcher(playerInput).matches()) {
            throw new WrongFormatException("!!! Zadal si nespravny format vstupu, opakuj vstup.");
        }

        matcher1.find();

        if (!isInputInBorderOfField(matcher1.group(2), matcher1.group(3))) {
            System.out.println("");
            processInput();
            return;
        }

        if (OPEN_MARK_PATTERN.matcher(playerInput).matches()) {
            doOperation(matcher1.group(1).charAt(0), matcher1.group(2).charAt(0), Integer.parseInt(matcher1.group(3)));
        }

    }

}