package com.techelevator.dao;

import com.techelevator.model.PendingRecipe;
import com.techelevator.model.RecipePic;

import java.util.List;

public interface PendingRecipeDao {

    boolean createPendingRecipe(PendingRecipe recipe);
    List<PendingRecipe> getPendingRecipes();
    boolean deletePendingRecipe (int recipeId);
    List<PendingRecipe> getPendingRecipesByUser(int userId);
    List<RecipePic> getPendingPics();
    boolean approvePendingPics(List<RecipePic> pics);
    boolean deletePendingPics(List<RecipePic> pics);
    boolean submitPhoto(String picURL, int recipeId, int userId);

}
