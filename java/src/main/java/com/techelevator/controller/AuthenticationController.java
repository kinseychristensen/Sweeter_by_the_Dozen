package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.techelevator.dao.UserDao;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

import java.security.Principal;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        User user;
        try {
            user = userDao.getUserByUsername(loginDto.getUsername());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDto newUser) {
        try {
            User checkUser = userDao.getUserByUsername(newUser.getUsername());
            User checkName = userDao.getUserByDisplayName(newUser.getDisplayName());
            if (checkUser.getId() != 0 || checkName.getId() != 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name or email already exists.");
            } else {
                userDao.createUser(newUser);
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed.");
        }
    }

    @RequestMapping(path="/update-user", method=RequestMethod.PUT)
    public boolean updateUserDetails(@RequestBody User user) {
        return userDao.updateUserDetails(user);
    }

    @RequestMapping(path="/update-password", method=RequestMethod.PUT)
    public boolean updateUserPassword(@RequestBody User user){
        return userDao.updateUserPassword(user);
    }

    @RequestMapping(path = "/user/{userId}/make-admin", method = RequestMethod.PUT)
    public boolean makeUserAdmin(@PathVariable int userId){
        return userDao.makeUserAdmin(userId);
    }

    @RequestMapping(path = "/get-user/{username}", method = RequestMethod.GET)
        public int getUserIdByUsername(@PathVariable String username){
            User user = userDao.getUserByUsername(username);
            return user.getId();
        }

       @RequestMapping(path = "/get-user/principal", method = RequestMethod.GET)
    public User getUserPrincipal(Principal principal){
           String userName = principal.getName();
           return userDao.getUserByUsername(userName);
       }

    }






