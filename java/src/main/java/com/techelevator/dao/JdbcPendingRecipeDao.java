package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.PendingRecipe;
import com.techelevator.model.Recipe;
import com.techelevator.model.RecipePic;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPendingRecipeDao implements PendingRecipeDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcPendingRecipeDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean createPendingRecipe(PendingRecipe recipe){
        try {
            String sql = "INSERT INTO pending_recipes (user_id, recipe_text, title, description, tags, attribution)" +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING pending_recipe_id;";
            int recipeId = jdbcTemplate.queryForObject(sql, int.class, recipe.getUserId(), recipe.getRecipeText(), recipe.getTitle(),
                    recipe.getDescription(), recipe.getTags(), recipe.getAttribution());

            if(recipeId != 0 && !recipe.getPics().isEmpty()){
                sql = "INSERT INTO pending_recipe_pics (pending_recipe_id, picture_url) VALUES (?, ?);";
                for (RecipePic pic : recipe.getPics()){
                    jdbcTemplate.update(sql, recipeId, pic.getPicUrl());
                }
            }

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }

    @Override
    public List<PendingRecipe> getPendingRecipes(){
        List<PendingRecipe> recipes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM pending_recipes;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

            while(rs.next()){
                PendingRecipe recipe = mapRowToPendingRecipe(rs);
                recipes.add(recipe);
            }for(PendingRecipe recipe : recipes){
              sql = "SELECT * FROM pending_recipes_pics WHERE pending_recipe_id = ?;";
              SqlRowSet rs2 = jdbcTemplate.queryForRowSet(sql, recipe.getId());
              List<RecipePic> pics = new ArrayList<>();
              while(rs.next()){
                  RecipePic pic = mapRowToPic(rs2);
                  pics.add(pic);
              }
              recipe.setPics(pics);
            }

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return recipes;
    }

    @Override
    public boolean deletePendingRecipe (int recipeId){
        try {
            String sql = "DELETE FROM pending_recipe_pics WHERE pending_recipe_id = ?;";
            jdbcTemplate.update(sql, recipeId);
            sql = "DELETE FROM pending_recipes WHERE pending_recipe_id = ?;";
            jdbcTemplate.update(sql, recipeId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }

    @Override
    public List<PendingRecipe> getPendingRecipesByUser(int userId){
        List<PendingRecipe> recipes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM pending_recipes WHERE user_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);

            while(rs.next()){
                PendingRecipe recipe = mapRowToPendingRecipe(rs);
                recipes.add(recipe);
            }for(PendingRecipe recipe : recipes){
                sql = "SELECT * FROM pending_recipes_pics WHERE pending_recipe_id = ?;";
                SqlRowSet rs2 = jdbcTemplate.queryForRowSet(sql, recipe.getId());
                List<RecipePic> pics = new ArrayList<>();
                while(rs.next()){
                    RecipePic pic = mapRowToPic(rs2);
                    pics.add(pic);
                }
                recipe.setPics(pics);
            }


        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return recipes;
    }

private PendingRecipe mapRowToPendingRecipe (SqlRowSet rs){
        PendingRecipe recipe = new PendingRecipe();
        recipe.setUserId(rs.getInt("user_id"));
        recipe.setId(rs.getInt("pending_recipe_id"));
        recipe.setRecipeText(rs.getString("recipe_text"));
        recipe.setTitle(rs.getString("title"));
        recipe.setDescription(rs.getString("description"));
        recipe.setTags(rs.getString("tags"));
        recipe.setAttribution(rs.getString("attribution"));

        return recipe;
}

private RecipePic mapRowToPic (SqlRowSet rs){
        RecipePic pic = new RecipePic();
        pic.setRecipeId(rs.getInt("pending_recipe_id"));
        pic.setPicUrl("picture_url");
        return pic;
}



}
