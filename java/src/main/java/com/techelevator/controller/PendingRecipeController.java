package com.techelevator.controller;

import com.techelevator.dao.PendingRecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.PendingRecipe;
import com.techelevator.model.RecipePic;
import com.techelevator.model.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PendingRecipeController {

    private final PendingRecipeDao pendingRecipeDao;
    private final UserDao userDao;


    public PendingRecipeController(PendingRecipeDao pendingRecipeDao, UserDao userDao) {
        this.pendingRecipeDao = pendingRecipeDao;
        this.userDao = userDao;
    }

    @RequestMapping(path="/new-recipe/pending", method= RequestMethod.POST)
    boolean createPendingRecipe(@RequestBody PendingRecipe recipe, Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        recipe.setUserId(user.getId());
        return pendingRecipeDao.createPendingRecipe(recipe);
    }

    @RequestMapping(path="/pending-recipes", method=RequestMethod.GET)
    List<PendingRecipe> getPendingRecipes() {
        return pendingRecipeDao.getPendingRecipes();
    }

    @RequestMapping(path="/pending-recipe/{recipeId}", method = RequestMethod.DELETE)
    boolean deletePendingRecipe (@PathVariable int recipeId){
        return pendingRecipeDao.deletePendingRecipe(recipeId);
    }

    @RequestMapping(path="/my-pending-recipes", method = RequestMethod.GET)
    List<PendingRecipe> getPendingRecipesByUser(Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return pendingRecipeDao.getPendingRecipesByUser(user.getId());
    }

    @RequestMapping(path="/pending-pics", method = RequestMethod.GET)
    List<RecipePic> getPendingPics(){
        return pendingRecipeDao.getPendingPics();
    }

    @RequestMapping(path="/pending-pics", method = RequestMethod.PUT)
      boolean approvePendingPics(@RequestBody List<RecipePic> pics){
        return pendingRecipeDao.approvePendingPics(pics);
    }

    @RequestMapping(path="/pending-pics/delete", method = RequestMethod.PUT)
    boolean deletePendingPics(@RequestBody List<RecipePic> pics){
        return pendingRecipeDao.deletePendingPics(pics);
    }



}
