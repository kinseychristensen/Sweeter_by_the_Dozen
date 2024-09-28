package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

@Component
public class JdbcRecipeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRecipeDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    List<Recipe> getRecipesByUser (int userId){
        List<Recipe> recipes = null;
        String sql = "SELECT * FROM recipes WHERE user_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        while(rs.next()){
            Recipe recipe = new Recipe();
            recipe = mapRowToRecipe(rs);
            recipes.add(recipe);
        }
        return recipes;
    }

    public int createRecipe(Recipe recipe){
        String sql = "INSERT INTO recipes (user_id, recipe_title, recipe_description, " +
                "attribution) VALUES (?, ?, ?, ?)" +
                "RETURNING user_id;";

        try{
           int newRecipeId = jdbcTemplate.queryForObject(sql, Integer.class, recipe.getUserId(),
            recipe.getTitle(), recipe.getDescription(), recipe.getAttribute());
            recipe.setRecipeId(newRecipeId);

            if(!recipe.getRecipePicList().isEmpty()) {
                for (RecipePic pic : recipe.getRecipePicList()) {
                    sql = "INSERT INTO recipe_pictures (recipe_id, picture_url) VALUES (?, ?);";
                    jdbcTemplate.update(sql, recipe.getRecipeId(), pic.getPicUrl());
                }
            }
            if(!recipe.getRecipeStepList().isEmpty()) {
                for (RecipeStep step : recipe.getRecipeStepList()) {
                    sql = "INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (?, ?, ?);";
                    jdbcTemplate.update(sql, recipe.getRecipeId(), step.getInstructions(), step.getStepNum());
                }
            }
            if(!recipe.getIngredientList().isEmpty()) {
                for (Ingredient ingredient : recipe.getIngredientList()) {
                    sql = "INSERT INTO recipe_ingredients (recipe_id, order_num, amount, unit_type," +
                            "quantifier, ingredient) VALUES (?, ?, ?, ?, ?, ?);";
                    jdbcTemplate.update(sql, recipe.getRecipeId(), ingredient.getIngredientNum(), ingredient.getAmount(),
                    ingredient.getUnitType(), ingredient.getQuantifier(), ingredient.getIngredientText());
                }
            }
            if(!recipe.getRecipeTagList().isEmpty()) {
                for (Tag tag : recipe.getRecipeTagList()) {
                    sql = "INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (?, ?);";
                    jdbcTemplate.update(sql, recipe.getRecipeId(), tag.getTagId());
                }
            }

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return recipe.getRecipeId();
    }




    Recipe getRecipeDetails (int recipeId){
        Recipe recipe = new Recipe();
        try {
            String sql = "SELECT * from recipes WHERE recipe_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, recipeId);

            if(rs.next()){
                recipe = mapRowToRecipe(rs);

                sql = "SELECT * from recipe_ingredients WHERE recipe_id  ?;";
                rs = jdbcTemplate.queryForRowSet(sql, recipeId);
                List<Ingredient> ingredients = null;
                while(rs.next()){
                    Ingredient ingredient = new Ingredient();
                    ingredient = mapRowToIngredient(rs);
                    ingredients.add(ingredient);
                }
                recipe.setIngredientList(ingredients);

                sql = "SELECT * from recipe_steps WHERE recipe_id = ?;";
                rs = jdbcTemplate.queryForRowSet(sql, recipeId);
                List<RecipeStep> steps = null;
               while(rs.next()){
                   RecipeStep step = new RecipeStep();
                   step = mapRowToStep(rs);
                   steps.add(step);
               }
               recipe.setRecipeStepList(steps);

               sql = "SELECT recipe_to_tags.recipe_id, tags.tag, tags.tag_id " +
                       "FROM recipe_to_tags " +
                       "LEFT JOIN tags ON tags.tag_id = recipe_to_tags.tag_id " +
                       "WHERE recipe_to_tags.recipe_id = ?;";
               rs = jdbcTemplate.queryForRowSet(sql, recipeId);
               List<Tag> tags = null;
               while(rs.next()){
                   Tag tag = new Tag();
                   tag = mapRowToTag(rs);
                   tags.add(tag);
               }
               recipe.setRecipeTagList(tags);

               sql = "SELECT * FROM recipe_pictures WHERE recipe_id = ?;";
               rs = jdbcTemplate.queryForRowSet(sql, recipeId);
               List<RecipePic> pics = null;
               while(rs.next()) {
                   RecipePic pic = new RecipePic();
                   pic = mapRowToRecipePic(rs);
                   pics.add(pic);
               }
            }
        }catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return recipe;
    }




    private Recipe mapRowToRecipe(SqlRowSet rs){
        Recipe recipe = new Recipe();
        recipe.setRecipeId(rs.getInt("recipe_id"));
        recipe.setUserId(rs.getInt("user_id"));
        recipe.setTitle(rs.getString("recipe_title"));
        recipe.setDescription(rs.getString("recipe_description"));
        recipe.setAttribute(rs.getString("attribution"));

        return recipe;
    }

    private Ingredient mapRowToIngredient(SqlRowSet rs){
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipeId(rs.getInt("recipe_id"));
        ingredient.setAmount(rs.getBigDecimal("amount"));
        ingredient.setQuantifier(rs.getString("quantifier"));
        ingredient.setIngredientText(rs.getString("ingredient"));
        ingredient.setIngredientNum(rs.getInt("order_num"));
        ingredient.setUnitType(rs.getString("unit_type"));

        return ingredient;
    }

    private Tag mapRowToTag (SqlRowSet rs){
        Tag tag = new Tag();
        tag.setTag(rs.getString("tag"));
        tag.setTagId(rs.getInt("tag_id"));

        return tag;
    }

    private RecipePic mapRowToRecipePic (SqlRowSet rs){
        RecipePic pic = new RecipePic();
        pic.setPicUrl(rs.getString("picture_url"));
        pic.setRecipeId(rs.getInt("recipe_id"));

        return pic;
    }

    private RecipeStep mapRowToStep (SqlRowSet rs){
        RecipeStep step = new RecipeStep();
        step.setStepNum(rs.getInt("step"));
        step.setInstructions(rs.getString("instructions"));

        return step;
    }


}
