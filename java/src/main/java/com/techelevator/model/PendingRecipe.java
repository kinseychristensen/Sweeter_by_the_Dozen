package com.techelevator.model;

import java.util.List;

public class PendingRecipe {
    private int userId;
    private int id;
    private String recipeText;
    private String title;
    private String tags;
    private String attribute;
    private String description;
    private String pics;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
