package com.techelevator.model;

import java.util.List;

public class Recipe {
    private int recipeId;
    private String title;
    private String description;
    private int userId;
    private String writer;
    private String attribute;
    private List<Ingredient> ingredientList;
    private List<RecipeStep> recipeStepList;
    private List<RecipePic> recipePicList;
    private List<Tag> recipeTagList;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<RecipeStep> getRecipeStepList() {
        return recipeStepList;
    }

    public void setRecipeStepList(List<RecipeStep> recipeStepList) {
        this.recipeStepList = recipeStepList;
    }

    public List<RecipePic> getRecipePicList() {
        return recipePicList;
    }

    public void setRecipePicList(List<RecipePic> recipePicList) {
        this.recipePicList = recipePicList;
    }

    public List<Tag> getRecipeTagList() {
        return recipeTagList;
    }

    public void setRecipeTagList(List<Tag> recipeTagList) {
        this.recipeTagList = recipeTagList;
    }

}
