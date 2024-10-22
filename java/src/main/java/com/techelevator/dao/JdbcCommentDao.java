package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Comment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcCommentDao implements CommentDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> getCommentsByRecipe (int recipeId){
        List<Comment> comments = new ArrayList<>();
        try {
            String sql = "SELECT recipe_comments.recipe_id, recipe_comments.comment_id, recipe_comments.user_id, \n" +
                    "\trecipe_comments.reported, recipe_comments.comment, users.display_name \n" +
                    "\tFROM recipe_comments \n" +
                    "\tLEFT JOIN users \n" +
                    "\tON recipe_comments.user_id = users.user_id \n" +
                    "\tWHERE recipe_comments.recipe_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, recipeId);

            while(rs.next()){
                Comment comment = mapRowToComment(rs);
                comments.add(comment);
            }

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return comments;
    }
    @Override
    public boolean createComment(Comment comment){
        try {
            String sql = "INSERT INTO recipe_comments (recipe_id, comment, user_id)" +
                    "VALUES (?, ?, ?);";
            jdbcTemplate.update(sql, comment.getRecipeId(), comment.getComment(), comment.getUserId());

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }
    @Override
public boolean reportComment (int commentId){
        try {
            String sql = "UPDATE recipe_comments SET reported = true WHERE comment_id = ?;";
            jdbcTemplate.update(sql, commentId);

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    return true;
}

    @Override
    public boolean unreportComment (int commentId){
        try {
            String sql = "UPDATE recipe_comments SET reported = false WHERE comment_id = ?;";
            jdbcTemplate.update(sql, commentId);

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }


    @Override
    public boolean deleteComment (int commentId){
        try {
            String sql = "DELETE FROM recipe_comments WHERE comment_id = ?;";
            jdbcTemplate.update(sql, commentId);

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }

    @Override
    public List<Comment> getReportedComments(){
        List<Comment> comments = new ArrayList<>();
        try {
            String sql = "SELECT recipe_comments.recipe_id, recipe_comments.comment_id, recipe_comments.user_id, \n" +
                    "recipe_comments.reported,recipe_comments.comment, users.display_name \n" +
                    "FROM recipe_comments \n" +
                    "LEFT JOIN users \n" +
                    "ON recipe_comments.user_id = users.user_id \n" +
                    "WHERE recipe_comments.reported = true;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

            while(rs.next()){
                Comment comment = mapRowToComment(rs);
                comments.add(comment);
            }

        }catch (CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
    } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
    }
    return comments;
    }



    private Comment mapRowToComment(SqlRowSet rs){
        Comment comment = new Comment();
        comment.setRecipeId(rs.getInt("recipe_id"));
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setComment(rs.getString("comment"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setReported(rs.getBoolean("reported"));
        comment.setWriter(rs.getString("display_name"));

        return comment;
    }
}
