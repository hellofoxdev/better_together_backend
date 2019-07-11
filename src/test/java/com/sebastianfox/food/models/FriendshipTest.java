package com.sebastianfox.food.models;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FriendshipTest
{
    @Test
    public void testIsOpen()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(1, testUser1.getFriendshipsFriend1().size());
        assertEquals(0, testUser1.getFriendshipsFriend2().size());
        assertEquals(0, testUser2.getFriendshipsFriend1().size());
        assertEquals(1, testUser2.getFriendshipsFriend2().size());

        Iterator<Friendship> friendshipIterator = testUser1.getFriendshipsFriend1().iterator();
        Friendship friendship = friendshipIterator.next();

        assertTrue(friendship.isOpen());

        testUser2.createAndAcceptFriendship(testUser1);

        assertFalse(friendship.isOpen());

    }

    @Test
    public void testIsAccepted()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(1, testUser1.getFriendshipsFriend1().size());
        assertEquals(0, testUser1.getFriendshipsFriend2().size());
        assertEquals(0, testUser2.getFriendshipsFriend1().size());
        assertEquals(1, testUser2.getFriendshipsFriend2().size());

        Iterator<Friendship> friendshipIterator = testUser1.getFriendshipsFriend1().iterator();
        Friendship friendship = friendshipIterator.next();

        assertFalse(friendship.isAccepted());

        testUser2.createAndAcceptFriendship(testUser1);

        assertTrue(friendship.isAccepted());

    }
}