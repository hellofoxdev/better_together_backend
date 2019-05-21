package com.sebastianfox.food.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import com.sebastianfox.food.models.User;
import org.junit.Test;

public class AuthenticatorTest {
    @Test
    public void testIsExpectedPassword(){
        Authenticator authenticator = new Authenticator();

        User user = new User();
        user.setUserName("testUser1");
        user.setSalt(authenticator.getNextSalt());
        user.setPassword(authenticator.hash("password".toCharArray(), user.getSalt()));

        assertTrue(authenticator.isExpectedPassword("password".toCharArray(), user.getSalt(), user.getPassword()));
        assertFalse(authenticator.isExpectedPassword("password_".toCharArray(), user.getSalt(), user.getPassword()));
    }
}
