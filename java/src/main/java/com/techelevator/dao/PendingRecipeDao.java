package com.techelevator.dao;

import com.techelevator.model.PendingRecipe;

import java.util.List;

public interface PendingRecipeDao {

    boolean createPendingRecipe(PendingRecipe recipe);
    List<PendingRecipe> getPendingRecipes();
    boolean deletePendingRecipe (int recipeId);
    List<PendingRecipe> getPendingRecipesByUser(int userId);
}
