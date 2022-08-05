package service;

import entity.Comment;
import entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {

    /**
     * CREATE TABLE rating(
     * game VARCHAR(64) NOT NULL,
     * username VARCHAR(64) NOT NULL,
     * rating INT NOT NULL CHECK (rating between 1 and 5),
     * rated_on TIMESTAMP NOT NULL,
     * constraint rating_info unique (game, username)
     * );
     */

    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";

    private static final String STATEMENT_SET_RATING = "INSERT INTO rating VALUES (?, ?, ?, ?)";
    private static final String STATEMENT_UPDATE_RATING = "UPDATE rating SET rating = ?, rated_on = ? WHERE username = ?";
    private static final String STATEMENT_GET_RATING = "SELECT game, username, rating, rated_on FROM rating WHERE game=? AND username = ? ORDER BY rated_on DESC";
    private static final String STATEMENT_RESET = "DELETE FROM rating";
    private static final String STATEMENT_GET_AVERAGE_RATING = "SELECT ROUND(AVG(rating)) FROM rating WHERE game= ?";

    @Override
    public void setRating(Rating rating) {

        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_SET_RATING)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getUserName());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRated_on().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Uzivatel s rovnakym menom uz zadal rating, prepisujem existujuci.");
            updateRating(rating);
        }

    }

    private void updateRating(Rating rating) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_UPDATE_RATING)
        ) {
            statement.setInt(1, rating.getRating());
            statement.setTimestamp(2, new Timestamp(rating.getRated_on().getTime()));
            statement.setString(3, rating.getUserName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_GET_AVERAGE_RATING)
        ) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getRating(String game, String userName) {

        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_GET_RATING)
        ) {
            statement.setString(1, game);
            statement.setString(2, userName);
            try (var rs = statement.executeQuery()) {
                Rating rating = null;
                while (rs.next()) {
                    rating = new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4));
                }
                if (rating == null) {
                    return 0;
                }
                return rating.getRating();
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.createStatement()
        ) {
            statement.executeUpdate(STATEMENT_RESET);


        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
