package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByDisplayName(String displayName);

    User createUser(RegisterUserDto user);

    boolean updateUserDetails(User user);

    boolean updateUserPassword(User user);

    boolean makeUserAdmin(int userId);
    boolean removeAdmin(int userId);
    boolean deleteUser (int userId);
}
