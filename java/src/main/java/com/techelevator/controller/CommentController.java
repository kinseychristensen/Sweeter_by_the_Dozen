package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Comment;
import com.techelevator.model.User;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class CommentController {
    private final CommentDao commentDao;
    private final UserDao userDao;

    public CommentController(CommentDao commentDao, UserDao userDao) {
        this.commentDao = commentDao;
        this.userDao = userDao;
    }

    @RequestMapping(path="/recipe/{recipeId}/comments", method = RequestMethod.GET)
    List<Comment> getCommentsByRecipe (@PathVariable int recipeId){
        return commentDao.getCommentsByRecipe(recipeId);
    }

    @RequestMapping(path="/new-comment", method = RequestMethod.POST)
    boolean createComment(@RequestBody Comment comment, Principal principal){
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        comment.setUserId(user.getId());
        return commentDao.createComment(comment);
    }

    @RequestMapping(path="/comment/{commentId}", method = RequestMethod.PUT)
    boolean reportComment (@PathVariable int commentId){
        return commentDao.reportComment(commentId);
    }
    @RequestMapping(path="/comment/{commentId}", method = RequestMethod.POST)
    boolean unreportComment (@PathVariable int commentId){
        return commentDao.unreportComment(commentId);
    }

    @RequestMapping(path="/comment/{commentId}", method = RequestMethod.DELETE)
    boolean deleteComment (@PathVariable int commentId){
        return commentDao.deleteComment(commentId);
    }

    @RequestMapping(path="/comments/reported", method = RequestMethod.GET)
    List<Comment> getReportedComments(){
        return commentDao.getReportedComments();
    }





}
