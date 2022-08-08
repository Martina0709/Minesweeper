package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.consoleui.UserInterface;
import service.*;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */


    private ScoreService scoreService;
    private CommentService commentService;
    private RatingService ratingService;


    private static Minesweeper instance;


    //vracia prave jednu instanciu singletona
    public static Minesweeper getInstance() {
        if (instance == null) {
            new Minesweeper();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    //singleton - konstruktor musi byt private
    private Minesweeper() {
        instance = this; //singleton

        scoreService = new ScoreServiceJDBC();
        commentService = new CommentServiceJDBC();
        ratingService = new RatingServiceJDBC();

        final UserInterface userInterface = new ConsoleUI();
        userInterface.play();
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Minesweeper.getInstance();
    }


//    public void setSetting(Settings setting) {
//        this.setting = setting;
//        this.setting.save();
//    }
//
//    public Settings getSetting() {
//        return this.setting;
//    }

    public ScoreService getScoreService() {
        return scoreService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public RatingService getRatingService() {
        return ratingService;
    }
}
