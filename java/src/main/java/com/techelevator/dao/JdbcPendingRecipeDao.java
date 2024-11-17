package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.PendingRecipe;
import com.techelevator.model.RecipePic;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
            String sql = "INSERT INTO pending_recipes (user_id, recipe_text, title, description, tags, attribution, picture_url)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";
             jdbcTemplate.update(sql, recipe.getUserId(), recipe.getRecipeText(), recipe.getTitle(),
                    recipe.getDescription(), recipe.getTags(), recipe.getAttribute(), recipe.getPics());

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
            String sql = "DELETE FROM pending_recipes WHERE pending_recipe_id = ?;";
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
            }


        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return recipes;
    }

    @Override
    public List<RecipePic> getPendingPics(){
        List<RecipePic> pics = new ArrayList<>();
        try {
            String sql = "SELECT * FROM pending_recipe_pics;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

            while(rs.next()){
                RecipePic pic = mapRowToPic(rs);
                pics.add(pic);
            }
         }catch (CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
    } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
    }
        return pics;
}

@Override
public boolean approvePendingPics(List<RecipePic> pics){
        try {
            String sql1 = "INSERT INTO recipe_pictures (recipe_id, picture_url, alt_text, user_id) VALUES (?,?,?, ?);";
            String sql2 = "DELETE FROM pending_recipe_pics WHERE recipe_id = ? AND picture_url = ?;";
            for(RecipePic pic : pics) {
            jdbcTemplate.update(sql1, pic.getRecipeId(), pic.getPicUrl(), pic.getAltText(), pic.getUserId());
            jdbcTemplate.update(sql2, pic.getRecipeId(), pic.getPicUrl());
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
}

@Override
public boolean deletePendingPics(List<RecipePic> pics){
        try {
            String sql = "DELETE FROM pending_recipe_pics WHERE recipe_id = ? AND picture_url = ?;";
            for(RecipePic pic : pics) {
                jdbcTemplate.update(sql, pic.getRecipeId(), pic.getPicUrl());
            }

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    return true;
}


    @Override
    public boolean submitPhoto(String picURL, int recipeId, int userId){
        try {
            String sql = "INSERT INTO pending_recipe_pics (recipe_id, picture_url, user_id)" +
                    "VALUES (?, ?, ?);";
            jdbcTemplate.update(sql, recipeId, picURL, userId);

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }





private PendingRecipe mapRowToPendingRecipe (SqlRowSet rs){
        PendingRecipe recipe = new PendingRecipe();
        recipe.setUserId(rs.getInt("user_id"));
        recipe.setId(rs.getInt("pending_recipe_id"));
        recipe.setRecipeText(rs.getString("recipe_text"));
        recipe.setTitle(rs.getString("title"));
        recipe.setDescription(rs.getString("description"));
        recipe.setTags(rs.getString("tags"));
        recipe.setAttribute(rs.getString("attribution"));
        recipe.setPics(rs.getString("picture_url"));


        return recipe;
}

private RecipePic mapRowToPic (SqlRowSet rs){
        RecipePic pic = new RecipePic();
        pic.setRecipeId(rs.getInt("recipe_id"));
        pic.setPicUrl(rs.getString("picture_url"));
        pic.setUserId(rs.getInt("user_id"));
        return pic;
}



}
