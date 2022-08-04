package test;

import entity.Comment;
import entity.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import service.*;

import java.util.Date;

public class CommentServiceTest {
    //    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset() {
        commentService.addComment(new Comment("minesweeper", "Jeno", "bla", new Date()));
        commentService.reset();
        assertEquals(0, commentService.getComments("minesweeper").size());
    }


    @Test
    public void testAddComment() {
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("minesweeper", "Jeno", "bla", date));

        var comments = commentService.getComments("minesweeper");
        assertEquals(1, comments.size());
        assertEquals("minesweeper", comments.get(0).getGame());
        assertEquals("Jeno", comments.get(0).getUserName());
        assertEquals("bla", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommented_on());
    }

    @Test
    public void testGetComments() {
        commentService.reset();
        var date1 = new Date();
        var date2 = new Date( System.currentTimeMillis() + 5000L);
        var date3 = new Date( System.currentTimeMillis() + 10000L);
        var date4 = new Date( System.currentTimeMillis() + 20000L);
        commentService.addComment(new Comment("minesweeper", "Peto", "bla", date1));
        commentService.addComment(new Comment("minesweeper", "Katka", "bla", date2));
        commentService.addComment(new Comment("minesweeper", "Zuzka", "bla", date3));
        commentService.addComment(new Comment("minesweeper", "Jergus", "bla", date4));

        var comments = commentService.getComments("minesweeper");

        assertEquals(4, comments.size());

        assertEquals("minesweeper", comments.get(0).getGame());
        assertEquals("Jergus", comments.get(0).getUserName());
        assertEquals("bla", comments.get(0).getComment());
        assertEquals(date4, comments.get(0).getCommented_on());

        assertEquals("minesweeper", comments.get(1).getGame());
        assertEquals("Zuzka", comments.get(1).getUserName());
        assertEquals("bla", comments.get(1).getComment());
        assertEquals(date3, comments.get(1).getCommented_on());

        assertEquals("minesweeper", comments.get(2).getGame());
        assertEquals("Katka", comments.get(2).getUserName());
        assertEquals("bla", comments.get(2).getComment());
        assertEquals(date2, comments.get(2).getCommented_on());

        assertEquals("minesweeper", comments.get(3).getGame());
        assertEquals("Peto", comments.get(3).getUserName());
        assertEquals("bla", comments.get(3).getComment());
        assertEquals(date1, comments.get(3).getCommented_on());
    }
}
