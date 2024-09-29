package com.techelevator.controller;

import com.techelevator.dao.RecipeDao;
import com.techelevator.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin

public class RecipeController {

    private final RecipeDao recipeDao;


    public RecipeController(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }


    @RequestMapping(path = "/recipe/{recipeId}", method= RequestMethod.GET)
    public Recipe getRecipeDetails (@PathVariable int recipeId){
        return recipeDao.getRecipeDetails(recipeId);
    }


    @RequestMapping(path = "/{userId}/recipes", method= RequestMethod.GET)
    public List<Recipe> getRecipesByUser (@PathVariable int userId){
        return recipeDao.getRecipesByUser(userId);
    }

    @RequestMapping(path = "/new-recipe", method= RequestMethod.POST)
    public int createRecipe (@RequestBody Recipe recipe){
        return recipeDao.createRecipe(recipe);
    }




}
