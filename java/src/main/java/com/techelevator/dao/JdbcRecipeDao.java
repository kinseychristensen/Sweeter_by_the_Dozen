package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class JdbcRecipeDao implements RecipeDao{

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final int RECIPES_PER_PAGE = 25;

    public JdbcRecipeDao(JdbcTemplate jdbcTemplate, UserDao userDao) {this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<Recipe> getRecipesByUser (int userId){
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE user_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        while(rs.next()){
            Recipe recipe = new Recipe();
            recipe = mapRowToRecipe(rs);
            recipes.add(recipe);
        }
        for(Recipe recipe : recipes){
           Recipe fullRecipe = getRecipeDetails(recipe.getRecipeId());
           recipe.setRecipePicList(fullRecipe.getRecipePicList());
           recipe.setRecipeTagList(fullRecipe.getRecipeTagList());
           recipe.setRecipeStepList(fullRecipe.getRecipeStepList());
           recipe.setIngredientList(fullRecipe.getIngredientList());

        }
        return recipes;
    }

    @Override
    public List<Recipe> getRecipesByKeywordAndTag(List<Integer> tags, String keyword, int pageNum) {
        List<Recipe> recipes = new ArrayList<>();
        String searchWord = "%" + keyword.toLowerCase().strip() + "%";
        int offset = pageNum * RECIPES_PER_PAGE;
        int numTags = tags.size();

        try {
            String sql = "SELECT recipe_to_tags.recipe_id FROM recipe_to_tags " +
                    "LEFT JOIN recipes ON recipes.recipe_id = recipe_to_tags.recipe_id " +
                    "WHERE recipe_to_tags.tag_id = ANY (?) " +
                    "AND recipes.recipe_title LIKE ? " +
                    "GROUP BY recipe_to_tags.recipe_id " +
                    "HAVING COUNT(DISTINCT tag_id) >= ? " +
                    "LIMIT ? OFFSET ?";

            Object[] params = new Object[]{
                    tags.toArray(new Integer[0]), // Pass the tag IDs as an array
                    searchWord,
                    numTags,
                    RECIPES_PER_PAGE,
                    offset
            };

            int[] types = new int[]{
                    java.sql.Types.ARRAY, // Type for tagIds
                    java.sql.Types.VARCHAR, // Type for searchWord
                    java.sql.Types.INTEGER, // Type for numTags
                    java.sql.Types.INTEGER, // Type for limit
                    java.sql.Types.INTEGER  // Type for offset
            };

            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, params, types);

            while (rs.next()) {
                Recipe recipe = mapRowToRecipe(rs);
                recipe = getRecipeDetails(recipe.getRecipeId());
                recipes.add(recipe);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return recipes;
    }



    /**
    @Override
    public List<Recipe> getRecipesByKeywordAndTag (List<Integer> tags, String keyword, int pageNum){
        List<Recipe> recipes = new ArrayList<>();
        String searchWordUpper = "%" + keyword.toUpperCase().strip() + "%";
        String searchWordLower = "%" + keyword.toLowerCase().strip() + "%";
        int offset = pageNum * RECIPES_PER_PAGE;
        int numTags = tags.size();
        if(numTags != 1){ numTags = 2;}


        try {

            String sql = "SELECT recipe_to_tags.recipe_id FROM recipe_to_tags \n" +
                    "LEFT JOIN recipes ON recipes.recipe_id = recipe_to_tags.recipe_id\n" +
                    "WHERE recipe_to_tags.tag_id ANY (:tagIds) AND recipes.recipe_title LIKE :searchWord\n" +
                    "GROUP BY recipe_to_tags.recipe_id\n" +
                    "HAVING COUNT(DISTINCT tag_id) >= :numTags\n" +
                    "LIMIT :limit OFFSET :offset";

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("tagIds", tags.toArray(new Integer[0]), java.sql.Types.ARRAY);  // Explicitly specify ARRAY type
            parameters.addValue("searchWord", searchWordUpper, java.sql.Types.VARCHAR);  // Explicitly specify VARCHAR
            parameters.addValue("numTags", numTags, java.sql.Types.INTEGER);  // INTEGER type for numTags
            parameters.addValue("limit", RECIPES_PER_PAGE, java.sql.Types.INTEGER);  // Explicitly specify INTEGER for limit
            parameters.addValue("offset", offset, java.sql.Types.INTEGER);  // INTEGER for offset

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
            SqlRowSet rs = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

            while(rs.next()){
                Recipe recipe = mapRowToRecipe(rs);
                recipe = getRecipeDetails(recipe.getRecipeId());
                recipes.add(recipe);
            }

            if(recipes.size()< RECIPES_PER_PAGE){
                if(recipes.isEmpty() && pageNum != 1) {

                    sql = "SELECT COUNT (recipe_to_tags.recipe_id) FROM recipe_to_tags \n" +
                            "LEFT JOIN recipes ON recipes.recipe_id = recipe_to_tags.recipe_id\n" +
                            "WHERE recipe_to_tags.tag_id ANY (:tagIds) AND recipes.recipe_title LIKE :searchWord\n" +
                            "GROUP BY recipe_to_tags.recipe_id\n" +
                            "HAVING COUNT(DISTINCT tag_id) >= :numTags;";


                    MapSqlParameterSource parameters2 = new MapSqlParameterSource();
                    parameters2.addValue("tagIds", tags.toArray(new Integer[0]), Types.ARRAY);
                    parameters2.addValue("searchWord", searchWordUpper, Types.VARCHAR);
                    parameters2.addValue("numTags", numTags, Types.INTEGER);

                    int numRecipes = jdbcTemplate.queryForObject(sql, int.class, parameters2);
                        int page = numRecipes / RECIPES_PER_PAGE;//example if we are on pageNum 4 and there were 51 recipes then 2 whole pages were recipes
                        int adjustedPage = pageNum - page; //4 - 2 = 2 we are on page two of searching for ingredients
                        offset = adjustedPage * RECIPES_PER_PAGE; // 2 * 25 = 50 so our offset should be 50 since we are on page 2
                        if (numRecipes % RECIPES_PER_PAGE > 0) {//1 one left over recipe was on the third page so we need to adjust the offset
                            offset = offset - (numRecipes % RECIPES_PER_PAGE); //offset is reduced by the amount of leftover recipes.
                        }

                    } else {
                        offset = 0;  //if there is a recipe, then that means we are doing the first page of ingredient searching so offset is 0
                    }
                    int listLimit = RECIPES_PER_PAGE - recipes.size(); //list should only return 25 items, so this will make sure to not go over.

                    sql = "SELECT recipe_to_tags.recipe_id FROM recipe_to_tags \n" +
                            "LEFT JOIN recipe_ingredients ON recipe_ingredients.recipe_id = recipe_to_tags.recipe_id\n" +
                            "WHERE recipe_to_tags.tag_id ANY (:tagIds) AND recipe_ingredients.ingredient LIKE :searchWord\n" +
                            "GROUP BY recipe_to_tags.recipe_id\n" +
                            "HAVING COUNT(DISTINCT tag_id) >= :numTags\n" +
                            "LIMIT :limit OFFSET :offset";

                MapSqlParameterSource parameters3 = new MapSqlParameterSource();
                parameters3.addValue("tagIds", tags.toArray(new Integer[0]), java.sql.Types.ARRAY);  // Explicitly specify ARRAY type
                parameters3.addValue("searchWord", searchWordUpper, java.sql.Types.VARCHAR);  // Explicitly specify VARCHAR
                parameters3.addValue("numTags", numTags, java.sql.Types.INTEGER);  // INTEGER type for numTags
                parameters3.addValue("limit", RECIPES_PER_PAGE, java.sql.Types.INTEGER);  // Explicitly specify INTEGER for limit
                parameters3.addValue("offset", offset, java.sql.Types.INTEGER);  // INTEGER for offset



              rs = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
                    while (rs.next()) {
                        Recipe recipe = getRecipeDetails(rs.getInt("recipe_id"));
                        recipes.add(recipe);
                    }
                }

        }catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return recipes;
    }
     /*/

    @Override
    public int createRecipe(Recipe recipe){
        String sql = "INSERT INTO recipes (user_id, recipe_title, recipe_description, " +
                "attribution) VALUES (?, ?, ?, ?)" +
                "RETURNING recipe_id;";
        String titleLower = recipe.getTitle().toUpperCase(Locale.ROOT).strip();

        try{
           int newRecipeId = jdbcTemplate.queryForObject(sql, Integer.class, recipe.getUserId(),
            titleLower, recipe.getDescription(), recipe.getAttribute());
            recipe.setRecipeId(newRecipeId);

            if(!recipe.getRecipePicList().isEmpty()) {
                for (RecipePic pic : recipe.getRecipePicList()) {
                    sql = "INSERT INTO recipe_pictures (recipe_id, picture_url, alt_text) VALUES (?, ?, ?);";
                    jdbcTemplate.update(sql, recipe.getRecipeId(), pic.getPicUrl(), pic.getAltText());
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
                    sql = "INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type," +
                            "quantifier, ingredient) VALUES (?, ?, ?, ?, ?, ?, ?);";
                    String ingredientLower = ingredient.getIngredientText().toLowerCase().strip();
                    jdbcTemplate.update(sql, recipe.getRecipeId(), ingredient.getIngredientNum(), ingredient.getAmountNumerator(),
                    ingredient.getAmountDenominator(), ingredient.getUnitType(), ingredient.getQuantifier(), ingredientLower);
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


@Override
public Recipe getRecipeDetails (int recipeId){
        Recipe recipe = new Recipe();
        try {
            String sql = "SELECT * FROM recipes WHERE recipe_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, recipeId);

            if(rs.next()){
                recipe = mapRowToRecipe(rs);
                User writer = userDao.getUserById(recipe.getUserId());
                recipe.setWriter(writer.getDisplayName());

                sql = "SELECT * from recipe_ingredients WHERE recipe_id = ?;";
                rs = jdbcTemplate.queryForRowSet(sql, recipeId);
                List<Ingredient> ingredients = new ArrayList<>();
                while(rs.next()){
                    Ingredient ingredient = new Ingredient();
                    ingredient = mapRowToIngredient(rs);
                    ingredients.add(ingredient);
                }
                recipe.setIngredientList(ingredients);

                sql = "SELECT * from recipe_steps WHERE recipe_id = ?;";
                rs = jdbcTemplate.queryForRowSet(sql, recipeId);
                List<RecipeStep> steps = new ArrayList<>();
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
               List<Tag> tags = new ArrayList<>();
               while(rs.next()){
                   Tag tag = new Tag();
                   tag = mapRowToTag(rs);
                   tags.add(tag);
               }
               recipe.setRecipeTagList(tags);

               sql = "SELECT * FROM recipe_pictures WHERE recipe_id = ?;";
               rs = jdbcTemplate.queryForRowSet(sql, recipeId);
               List<RecipePic> pics = new ArrayList<>();
               while(rs.next()) {
                   RecipePic pic = new RecipePic();
                   pic = mapRowToRecipePic(rs);
                   pics.add(pic);
               }
               recipe.setRecipePicList(pics);
            }
        }catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return recipe;
    }

 public int getNumOfRecipes(){
        int count = 0;
        try {
            String sql = "SELECT COUNT(recipe_id) FROM recipes;";
            int newCount = jdbcTemplate.queryForObject(sql, Integer.class);
            if (newCount >0){
                count = newCount;
            }
        }catch(CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
    } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
    }
    return count;
    }

   public boolean saveRecipe (int recipeId, int userId){
        try {
            String sql = "INSERT INTO saved_recipes (recipe_id, user_id) VALUES (?, ?);";
            jdbcTemplate.update(sql, recipeId, userId);

        }catch(CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
    } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
    } return true;
   }


   public List<Recipe> getSavedRecipes (int userId){
        List<Recipe> recipes = new ArrayList<>();

        try {
            String sql = "SELECT recipe_id FROM saved_recipes WHERE user_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
            while (rs.next()){
               int recipeId = rs.getInt("recipe_id");
               Recipe recipe = getRecipeDetails(recipeId);
               recipes.add(recipe);
            }
        }catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return recipes;
   }

   @Override
   public List<Tag> getAllTags(){
       List<Tag> tags = new ArrayList<>();
        try{
            String sql = "SELECT * from tags";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

            while(rs.next()){
                Tag tag = mapRowToTag(rs);
                tags.add(tag);
            }

        }catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return tags;
   }

   @Override
   public boolean deleteRecipe(int recipeId){
       try{
           String sql = "DELETE FROM recipe_to_tags WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);
           sql = "DELETE FROM recipe_ingredients WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);
           sql = "DELETE FROM recipe_steps WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);
           sql = "DELETE FROM recipe_comments WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);
           sql = "DELETE FROM recipe_pictures WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);
           sql = "DELETE FROM saved_recipes WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);
           sql = "DELETE FROM recipes WHERE recipe_id = ?;";
           jdbcTemplate.update(sql, recipeId);

       }catch(CannotGetJdbcConnectionException e) {
           throw new DaoException("Unable to connect to server or database", e);
       } catch (DataIntegrityViolationException e) {
           throw new DaoException("Data integrity violation", e);
       } return true;
   }

   public boolean removeFromSaves (int recipeId, int userId){
        try {
            String sql = "DELETE FROM saved_recipes WHERE recipe_id = ? AND user_id = ?;";
            jdbcTemplate.update(sql, recipeId, userId);

        }catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return true;
   }

   @Override
   public List<Recipe> searchByKeyword (String keyword, int pageNum){
        List<Recipe> recipes = new ArrayList<>();
        String searchWordUpper = "%" + keyword.toUpperCase().strip() + "%";
        String searchWordLower = "%" + keyword.toLowerCase().strip() + "%";
        int offset = pageNum * RECIPES_PER_PAGE;


        try {
            String sql = "SELECT * FROM recipes \n" +
                    "WHERE recipe_title LIKE ? \n" +
                    "ORDER BY recipe_id LIMIT ? OFFSET ?;";

            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, searchWordUpper, RECIPES_PER_PAGE, offset);

            while(rs.next()){
                Recipe recipe = mapRowToRecipe(rs);
                recipe = getRecipeDetails(recipe.getRecipeId());
                recipes.add(recipe);
            }

            if(recipes.size()< RECIPES_PER_PAGE){
                if(recipes.isEmpty() && pageNum != 1) {
                    sql = "SELECT COUNT (recipe_id) FROM recipes WHERE recipe_title LIKE ?;";
                    int numRecipes = jdbcTemplate.queryForObject(sql, int.class, searchWordUpper);
                    int page = numRecipes / RECIPES_PER_PAGE;//example if we are on pageNum 4 and there were 51 recipes then 2 whole pages were recipes
                    int adjustedPage = pageNum - page; //4 - 2 = 2 we are on page two of searching for ingredients
                    offset = adjustedPage * RECIPES_PER_PAGE; // 2 * 25 = 50 so our offset should be 50 since we are on page 2
                    if (numRecipes % RECIPES_PER_PAGE > 0) {//1 one left over recipe was on the third page so we need to adjust the offset
                        offset = offset - (numRecipes % RECIPES_PER_PAGE); //offset is reduced by the amount of leftover recipes.

                    }
                }else {
                      offset = 0;  //if there is a recipe, then that means we are doing the first page of ingredient searching so offset is 0
                    }
               int listLimit = RECIPES_PER_PAGE - recipes.size(); //list should only return 25 items, so this will make sure to not go over.

                sql = "SELECT recipe_id FROM recipe_ingredients \n" +
                            "WHERE ingredient LIKE ? \n" +
                            "ORDER BY recipe_id LIMIT ? OFFSET ?;";

                rs = jdbcTemplate.queryForRowSet(sql, searchWordLower, listLimit, offset);
                while (rs.next()){
                    Recipe recipe = getRecipeDetails(rs.getInt("recipe_id"));
                    recipes.add(recipe);
                }

            }

        }catch(CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
    } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
    }
        return recipes;

   }



    private Recipe mapRowToRecipe(SqlRowSet rs){
        {
            Recipe recipe = new Recipe();
            recipe.setRecipeId(rs.getInt("recipe_id"));
            recipe.setUserId(rs.getInt("user_id"));
            recipe.setTitle(rs.getString("recipe_title"));
            recipe.setDescription(rs.getString("recipe_description"));
            recipe.setAttribute(rs.getString("attribution"));

            return recipe;
        }
    }

    private Ingredient mapRowToIngredient(SqlRowSet rs){
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipeId(rs.getInt("recipe_id"));
        ingredient.setAmountNumerator(rs.getInt("amount_numerator"));
        ingredient.setAmountDenominator(rs.getInt("amount_denominator"));
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
        pic.setAltText(rs.getString("alt_text"));

        return pic;
    }

    private RecipeStep mapRowToStep (SqlRowSet rs){
        RecipeStep step = new RecipeStep();
        step.setStepNum(rs.getInt("step"));
        step.setInstructions(rs.getString("instructions"));

        return step;
    }


}
