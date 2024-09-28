package com.techelevator.dao;

import com.techelevator.model.Recipe;

import java.util.List;

public interface RecipeDao {

    List<Recipe> getRecipesByUser (int userId);
    public int createRecipe(Recipe recipe);
    Recipe getRecipeDetails (int recipeId);
}
