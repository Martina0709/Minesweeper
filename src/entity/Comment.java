package entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private String game;
    private String userName;
    private String comment;
    private Date commented_on;

    public Comment(String game, String userName, String comment, Date commented_on) {
        this.game = game;
        this.userName = userName;
        this.comment = comment;
        this.commented_on = commented_on;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                ", userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", commented_on=" + commented_on +
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommented_on() {
        return commented_on;
    }

    public void setCommented_on(Date commented_on) {
        this.commented_on = commented_on;
    }
}
