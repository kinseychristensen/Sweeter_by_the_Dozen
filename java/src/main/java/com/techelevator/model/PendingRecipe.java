package com.techelevator.model;

import java.util.List;

public class PendingRecipe {
    private int userId;
    private int id;
    private String recipeText;
    private String title;
    private String tags;
    private String attribution;
    private String description;
    private List<RecipePic> pics;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public List<RecipePic> getPics() {
        return pics;
    }

    public void setPics(List<RecipePic> pics) {
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

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
