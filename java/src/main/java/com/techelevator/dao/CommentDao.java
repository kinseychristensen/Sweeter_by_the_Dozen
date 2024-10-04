package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getCommentsByRecipe (int recipeId);
    boolean createComment(Comment comment);
    boolean reportComment (int commentId);
    boolean unreportComment (int commentId);
    boolean deleteComment (int commentId);
    List<Comment> getReportedComments();
}
