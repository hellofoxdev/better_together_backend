package com.sebastianfox.food.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastianfox.food.models.Event;
import com.sebastianfox.food.models.User;
import com.sebastianfox.food.utils.Authenticator;

public class UserService {

    private Authenticator authenticator = new Authenticator();
    private ObjectMapper mapper = new ObjectMapper();

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

    public void fullfilFacebookUser(User user) {
        user.setFacebookAccount(true);
        user.setEmail(user.getFacebookAccountEmail());
        user.setFacebookAccountUserName(user.getEmail());
        user.setUserName(user.getEmail());
        user.setSalt(authenticator.getNextSalt());
        // change facebookEmail to facebookId for password
        user.setPassword(authenticator.hash(String.valueOf(user.getFacebookAccountId()).toCharArray(), user.getSalt()));

    }

    public void updateUser(User dbUser, User appUser) {
        dbUser.mergeDataFromOtherInstance(appUser);
    }



}
