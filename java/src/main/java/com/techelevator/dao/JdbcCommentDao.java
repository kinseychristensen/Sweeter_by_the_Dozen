package com.techelevator.dao;

import com.techelevator.model.Comment;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcCommentDao {






    private Comment mapRowToComment(SqlRowSet rs){
        Comment comment = new Comment();
        comment.setRecipeId(rs.getInt("recipe_id"));
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setComment(rs.getString("comment"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setReported(rs.getBoolean("reported"));

        return comment;
    }
}
