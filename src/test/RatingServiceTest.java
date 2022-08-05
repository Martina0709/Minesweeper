package test;

import entity.Rating;
import org.junit.jupiter.api.Test;
import service.RatingService;
import service.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingServiceTest {
    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testReset() {
        ratingService.setRating(new Rating("minesweeper", "Jeno", 2, new Date()));
        ratingService.reset();
        assertEquals(0, ratingService.getRating("minesweeper", "Jeno"));
    }

    @Test
    public void testSetRating() {
        ratingService.reset();
        var date = new Date();
        ratingService.setRating(new Rating("minesweeper", "Jeno", 1, date));
        ratingService.setRating(new Rating("minesweeper", "Jeno", 2, date));

        var rating = ratingService.getRating("minesweeper", "Jeno");
        assertEquals(2, rating);
    }

    @Test
    public void testGetRating() {
        ratingService.reset();
        var date = new Date();
        ratingService.setRating(new Rating("minesweeper", "Peto", 2, date));

        var rating = ratingService.getRating("minesweeper", "Peto");
        assertEquals(2, rating);

        var rating2 = ratingService.getRating("minesweeper", "Adam");
        assertEquals(0, rating2);
    }

    @Test
    public void testAverageRating() {
        ratingService.reset();
        var date = new Date();
        ratingService.setRating(new Rating("minesweeper", "Peto", 1, date));
        ratingService.setRating(new Rating("minesweeper", "Jeno", 3, date));
        ratingService.setRating(new Rating("minesweeper", "Adam", 5, date));

        var averageRating = ratingService.getAverageRating("minesweeper");
        assertEquals(3, averageRating);

        ratingService.reset();

        ratingService.setRating(new Rating("minesweeper", "Peto", 1, date));
        ratingService.setRating(new Rating("minesweeper", "Jeno", 3, date));
        ratingService.setRating(new Rating("minesweeper", "Adam", 4, date));

        var averageRating2 = ratingService.getAverageRating("minesweeper");
        assertEquals(3, averageRating2);

        ratingService.reset();

        var averageRating3 = ratingService.getAverageRating("minesweeper");
        assertEquals(0, averageRating3);
    }
}
