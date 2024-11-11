package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.RegisterUserDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);

            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        User user = new User();
        String sql = "SELECT * FROM users WHERE username = LOWER(TRIM(?));";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User getUserByDisplayName(String displayName) {
        if (displayName == null) throw new IllegalArgumentException("Username cannot be null");
        User user = new User();
        String sql = "SELECT * FROM users WHERE display_name = LOWER(TRIM(?));";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, displayName);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }


    @Override
    public User createUser(RegisterUserDto user) {
        User newUser = null;
        String insertUserSql = "INSERT INTO users (username, display_name, password_hash, role, avatar_id) values (LOWER(TRIM(?)), ?, ?, ?, ?) RETURNING user_id;";
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        String ssRole = user.getRole().toUpperCase().startsWith("ROLE_") ? user.getRole().toUpperCase() : "ROLE_" + user.getRole().toUpperCase();
        try {
            int newUserId = jdbcTemplate.queryForObject(insertUserSql, int.class, user.getUsername(), user.getDisplayName(), password_hash, ssRole, user.getAvatarId());
            newUser = getUserById(newUserId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newUser;
    }

    @Override
    public boolean updateUserDetails(User user) {
        try {
            String sql = "UPDATE users SET display_name = ?, username = ?, violations = ?, restricted = ?, avatar_id = ?  " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sql, user.getDisplayName(), user.getUsername(), user.getViolations(),
                    user.isRestricted(), user.getAvatarId(), user.getId());

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }

    @Override
    public boolean makeUserAdmin(int userId) {
        try {
            String sql = "UPDATE users SET role = 'ROLE_ADMIN' " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sql, userId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }

    @Override
    public boolean removeAdmin(int userId) {
        try {
            String sql = "UPDATE users SET role = 'ROLE_USER' " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sql, userId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return true;
    }



    public boolean updateUserPassword(User user){
   try{
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, password_hash, user.getId());
    }catch(CannotGetJdbcConnectionException e){
       throw new DaoException("Unable to connect to server or database", e);
    } catch(DataIntegrityViolationException e){
        throw new DaoException("Data integrity violation", e);
    }
   return true;
}

public boolean deleteUser (int userId){
        try {
    String sql= "DELETE FROM recipe_comments WHERE user_id = ?;";
    jdbcTemplate.update(sql, userId);
    sql = "DELETE FROM recipe_pictures WHERE user_id = ?;";
    jdbcTemplate.update(sql, userId);
    sql = "DELETE FROM pending_recipes WHERE user_id = ?;";
    jdbcTemplate.update(sql, userId);
    sql = "DELETE FROM saved_recipes WHERE user_id = ?;";
    jdbcTemplate.update(sql, userId);
    sql = "DELETE FROM pending_recipe_pics WHERE user_id = ?;";
    jdbcTemplate.update(sql, userId);
    sql = "DELETE FROM users WHERE user_id = ?;";
    jdbcTemplate.update(sql, userId);


        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    return true;

}



    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setDisplayName(rs.getString("display_name"));
        user.setViolations(rs.getInt("violations"));
        user.setRestricted(rs.getBoolean("restricted"));
        user.setAvatarId(rs.getInt("avatar_id"));
        return user;
    }
}
