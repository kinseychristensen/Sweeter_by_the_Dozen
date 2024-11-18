package com.techelevator.dao;

import com.techelevator.model.Recipe;
import com.techelevator.model.Tag;

import java.util.List;

public interface RecipeDao {

    List<Recipe> getRecipesByUser (int userId);
    int createRecipe(Recipe recipe);
    Recipe getRecipeDetails (int recipeId);
    int getNumOfRecipes();
    boolean saveRecipe (int recipeId, int userId);
    List<Recipe> getSavedRecipes (int userId);
    List<Tag> getAllTags();
    boolean deleteRecipe(int recipeId);
    boolean removeFromSaves (int recipeId, int userId);
    List<Recipe> searchByKeyword (String keyword, int pageNum);
    List<Recipe> getRecipesByKeywordAndTag (int[] tags, String keyword, int pageNum);
    boolean updateRecipe (Recipe recipe);
    boolean createTags (List<Tag> tags);
}
