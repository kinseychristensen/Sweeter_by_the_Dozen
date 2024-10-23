package com.techelevator.controller;

import com.techelevator.dao.RecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Recipe;
import com.techelevator.model.Tag;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin

public class RecipeController {

    @Autowired
    private final RecipeDao recipeDao;
    @Autowired
    private final UserDao userDao;


    public RecipeController(RecipeDao recipeDao, UserDao userDao) {
        this.recipeDao = recipeDao;
        this.userDao = userDao;
    }


    @RequestMapping(path = "/recipe/{recipeId}", method= RequestMethod.GET)
    public Recipe getRecipeDetails (@PathVariable int recipeId){
        return recipeDao.getRecipeDetails(recipeId);
    }

    @RequestMapping(path = "/{userId}/recipes", method= RequestMethod.GET)
    public List<Recipe> getRecipesByUser (@PathVariable int userId){
        return recipeDao.getRecipesByUser(userId);
    }
    @RequestMapping(path = "/my-recipes", method= RequestMethod.GET)
    public List<Recipe> getRecipesForPrincipal (Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return recipeDao.getRecipesByUser(user.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/recipes/new", method= RequestMethod.POST)
    public int createRecipe (@RequestBody Recipe recipe){
        return recipeDao.createRecipe(recipe);
    }

 @RequestMapping(path = "/recipes/number", method = RequestMethod.GET)
    public int getNumOfRecipes(){
        return recipeDao.getNumOfRecipes();
 }

 @RequestMapping(path = "recipes/random", method = RequestMethod.GET)
    public Recipe getRandomRecipe(){
        int numRecipes = recipeDao.getNumOfRecipes();
        Random rand = new Random();
        int randomRecipeId = rand.nextInt(numRecipes);
        while(randomRecipeId == 0){
            randomRecipeId = rand.nextInt(numRecipes);
        }
        return recipeDao.getRecipeDetails(randomRecipeId);
 }
    @PreAuthorize("isAuthenticated()")
@RequestMapping(path = "recipe/{recipeId}/save", method = RequestMethod.POST)
    public boolean saveRecipe (@PathVariable int recipeId, Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return recipeDao.saveRecipe(recipeId, user.getId());
        }
    @PreAuthorize("isAuthenticated()")
 @RequestMapping(path = "/recipes/my-saves", method = RequestMethod.GET)
 public List<Recipe> getSavedRecipes (Principal principal){
     String userName = principal.getName();
     User user= userDao.getUserByUsername(userName);
   return recipeDao.getSavedRecipes(user.getId());
    }

    @RequestMapping(path = "/tags")
    public List<Tag> getAllTags(){
        return recipeDao.getAllTags();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/recipe/{recipeId}", method = RequestMethod.DELETE)
        public boolean deleteRecipe(@PathVariable int recipeId) {
        return recipeDao.deleteRecipe(recipeId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/recipe/{recipeId}/save", method = RequestMethod.DELETE)
    public boolean removeFromSaves (@PathVariable int recipeId, Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return recipeDao.removeFromSaves(recipeId, user.getId());
    }

    @RequestMapping(path="/search/={keyword}/page/{pageNum}", method = RequestMethod.GET)
    public List<Recipe> searchByKeyword (@PathVariable String keyword, @PathVariable int pageNum){

        return recipeDao.searchByKeyword(keyword, pageNum);
    }

    @RequestMapping(path="/search/={keyword}/page/{pageNum}", method = RequestMethod.PUT)
    public List<Recipe> searchByKeywordAndTags (@PathVariable String keyword, @PathVariable int pageNum, @RequestBody int[] tags){

        if(tags.length == 0){
            return recipeDao.searchByKeyword(keyword, pageNum);
        }

        return recipeDao.getRecipesByKeywordAndTag(tags, keyword, pageNum);
    }



        }
