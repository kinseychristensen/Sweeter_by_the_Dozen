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
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin

public class RecipeController {

    private final RecipeDao recipeDao;
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
        while(randomRecipeId > numRecipes || randomRecipeId == 0){
            randomRecipeId = rand.nextInt(numRecipes);
        }
        return recipeDao.getRecipeDetails(randomRecipeId);
 }

@RequestMapping(path = "recipe/{recipeId}/save", method = RequestMethod.POST)
    public boolean saveRecipe (@PathVariable int recipeId, Principal principal){
        String userName = principal.getName();
        User user= userDao.getUserByUsername(userName);
        return recipeDao.saveRecipe(recipeId, user.getId());
        }

 @RequestMapping(path = "/recipes/my-saves", method = RequestMethod.GET)
 public List<Recipe> getSavedRecipes (Principal principal){
     String userName = principal.getName();
     User user= userDao.getUserByUsername(userName);
   return recipeDao.getSavedRecipes(user.getId());
    }

    @RequestMapping(path = "/recipes/search-tag/{tagId}/page/{offset}", method = RequestMethod.GET)
    public List<Recipe> getRecipesByTag (@PathVariable int tagId, @PathVariable int offset){
        return recipeDao.getRecipesByTag(tagId, offset);
    }

    @RequestMapping(path = "/tags")
    public List<Tag> getAllTags(){
        return recipeDao.getAllTags();

    }





        }
