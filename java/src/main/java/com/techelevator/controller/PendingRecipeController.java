package com.techelevator.controller;

import com.techelevator.dao.PendingRecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.PendingRecipe;
import com.techelevator.model.RecipePic;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PendingRecipeController {

    @Autowired
    private final PendingRecipeDao pendingRecipeDao;
    @Autowired
    private final UserDao userDao;


    public PendingRecipeController(PendingRecipeDao pendingRecipeDao, UserDao userDao) {
        this.pendingRecipeDao = pendingRecipeDao;
        this.userDao = userDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/new-recipe/pending", method= RequestMethod.POST)
    boolean createPendingRecipe(@RequestBody PendingRecipe recipe, Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        recipe.setUserId(user.getId());
        return pendingRecipeDao.createPendingRecipe(recipe);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/pending-recipes", method=RequestMethod.GET)
    List<PendingRecipe> getPendingRecipes() {
        return pendingRecipeDao.getPendingRecipes();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/pending-recipe/{recipeId}", method = RequestMethod.DELETE)
    boolean deletePendingRecipe (@PathVariable int recipeId){
        return pendingRecipeDao.deletePendingRecipe(recipeId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/my-pending-recipes", method = RequestMethod.GET)
    List<PendingRecipe> getPendingRecipesByUser(Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return pendingRecipeDao.getPendingRecipesByUser(user.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/pending-pics", method = RequestMethod.GET)
    List<RecipePic> getPendingPics(){
        return pendingRecipeDao.getPendingPics();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/pending-pics", method = RequestMethod.PUT)
      boolean approvePendingPics(@RequestBody List<RecipePic> pics){
        return pendingRecipeDao.approvePendingPics(pics);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/pending-pics/delete", method = RequestMethod.PUT)
    boolean deletePendingPics(@RequestBody List<RecipePic> pics){
        return pendingRecipeDao.deletePendingPics(pics);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/submit-photo/{recipeId}/{picUrl}", method = RequestMethod.PUT)
    boolean submitPhoto(@PathVariable String picUrl, @PathVariable int recipeId, Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return pendingRecipeDao.submitPhoto(picUrl, recipeId, user.getId());
    }


}
