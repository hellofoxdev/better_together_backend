package com.sebastianfox.food.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastianfox.food.models.Event;
import com.sebastianfox.food.models.User;
import com.sebastianfox.food.utils.Authenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class UserService {

    private Authenticator authenticator = new Authenticator();
    private ObjectMapper mapper = new ObjectMapper();

    public User downcast(User origin) {
        User user = new User();
        user.setId(origin.getId());
        user.setUserName(origin.getUserName());
        return user;
    }


    public void addEvent(User user, Event event) {
        user.addEvent(event);
    }

    public User createUser(String username, String email, String password) {
        // Create and safe new user
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setSalt(authenticator.getNextSalt());
        user.setPassword(authenticator.hash(password.toCharArray(), user.getSalt()));
        return user;
    }

    public void mergeData(User dbUser, User appUser) {
        dbUser.mergeDataFromOtherInstance(appUser);
    }



}
