package service;

import entity.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getComments(String game);

    void reset();

}


