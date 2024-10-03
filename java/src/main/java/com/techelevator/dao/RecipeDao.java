package com.techelevator.dao;

import com.techelevator.model.Recipe;

import java.util.List;

public interface RecipeDao {

    List<Recipe> getRecipesByUser (int userId);
    int createRecipe(Recipe recipe);
    Recipe getRecipeDetails (int recipeId);
    int getNumOfRecipes();
    boolean saveRecipe (int recipeId, int userId);
    List<Recipe> getSavedRecipes (int userId);
}
