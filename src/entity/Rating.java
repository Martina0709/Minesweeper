package entity;

import java.io.Serializable;
import java.util.Date;

public class Rating implements Serializable {

    private String game;
    private String userName;
    private int rating;
    private Date rated_on;

    public Rating(String game, String userName, int rating, Date rated_on) {
        this.game = game;
        this.userName = userName;
        this.rating = rating;
        this.rated_on = rated_on;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", userName='" + userName + '\'' +
                ", rating=" + rating +
                ", rated_on=" + rated_on +
                '}';
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRated_on() {
        return rated_on;
    }

    public void setRated_on(Date rated_on) {
        this.rated_on = rated_on;
    }
}
