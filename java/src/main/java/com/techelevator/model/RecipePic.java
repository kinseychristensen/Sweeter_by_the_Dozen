package com.techelevator.model;

public class RecipePic {
    private int recipeId;
    private String picUrl;
    private String altText;

    public int getRecipeId() {
        return recipeId;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
