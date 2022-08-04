package service;

import entity.Comment;
import entity.Score;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommentServiceFile implements CommentService {

    private static final String FILE = "comment.bin";
    private List<Comment> comments = new ArrayList<>();

    @Override
    public void addComment(Comment comment) {
        comments = load();
        comments.add(comment);
        save();

    }

    @Override
    public List<Comment> getComments(String game) {
        comments = load();
        return comments.stream().filter(s -> s.getGame().equals(game))
                .sorted((s1, s2) -> -s1.getCommented_on().compareTo(s2.getCommented_on()))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        comments = new ArrayList<>();
        save();

    }

    private List<Comment> load() {
        try (var is = new ObjectInputStream(new FileInputStream(FILE))) {
            return (List<Comment>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            save();
        }
        return new ArrayList<>();
    }

    private void save() {
        try (var os = new ObjectOutputStream(new FileOutputStream(FILE))) {
            os.writeObject(comments);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }
}
