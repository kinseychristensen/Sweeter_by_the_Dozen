package com.techelevator.model;

public class Ingredient {

    private int amountNumerator;
    private int amountDenominator;
    private int ingredientNum;
    private String quantifier;
    private String ingredientText;
    private int recipeId;
    private String unitType;

    public int getRecipeId() {
        return recipeId;
    }

    public String getUnitType() {
        return unitType;
    }

    public int getAmountNumerator() {
        return amountNumerator;
    }

    public void setAmountNumerator(int amountNumerator) {
        this.amountNumerator = amountNumerator;
    }

    public int getAmountDenominator() {
        return amountDenominator;
    }

    public void setAmountDenominator(int amountDenominator) {
        this.amountDenominator = amountDenominator;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }


    public int getIngredientNum() {
        return ingredientNum;
    }

    public void setIngredientNum(int ingredientNum) {
        this.ingredientNum = ingredientNum;
    }

    public String getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(String quantifier) {
        this.quantifier = quantifier;
    }

    public String getIngredientText() {
        return ingredientText;
    }

    public void setIngredientText(String ingredientText) {
        this.ingredientText = ingredientText;
    }
}
