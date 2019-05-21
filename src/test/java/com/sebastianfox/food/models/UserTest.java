package com.sebastianfox.food.models;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class UserTest
{
    @Test
    public void testGetUserName()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        assertEquals( "user1", testUser1.getUserName());
    }

    @Test
    public void testGetAcceptedFriends()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        Friendship friendship = new Friendship(testUser1,testUser2);
        friendship.setAccepted(true);

        assertEquals( "user1", testUser1.getUserName());
        assertThat(testUser1.getAcceptedFriends(), hasItems(testUser2));

    }

    @Test
    public void testNumberOfAcceptedFriends(){

        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        User testUser3 = new User();
        testUser3.setUserName("user3");

        new Friendship(testUser1,testUser2).setAccepted(true);
        assertEquals(1, testUser1.getAcceptedFriends().size());

        new Friendship(testUser1,testUser3).setAccepted(true);
        assertEquals(2, testUser1.getAcceptedFriends().size());
    }

    @Test
    public void testCreateOrUpdateFriendship(){

        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        User testUser3 = new User();
        testUser3.setUserName("user3");

        User testUser4 = new User();
        testUser4.setUserName("user4");

        testUser4.createAndAcceptFriendship(testUser3);
        assertEquals(0, testUser3.getAcceptedFriends().size());
        assertEquals(0, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser3.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser4.getAcceptedFriends().size());
        assertEquals(1, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        testUser4.createAndAcceptFriendship(testUser3);
        assertEquals(0, testUser3.getAcceptedFriends().size());
        assertEquals(0, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser3.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser4.getAcceptedFriends().size());
        assertEquals(1, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        testUser3.createAndAcceptFriendship(testUser4);
        assertEquals(1, testUser3.getAcceptedFriends().size());
        assertEquals(0, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser4.getAcceptedFriends().size());
        assertEquals(0, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());
    }

    @Test
    public void testDeclineAndDeleteFriendship(){

        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        // user requests friendship
        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        // user deletes request for friendship
        testUser1.declineAndDeleteFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // user requests friendship
        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        // friend declines friendship
        testUser2.declineAndDeleteFriendship(testUser1);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // friend requests friendship
        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(1, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // friend deletes request for friendship
        testUser2.declineAndDeleteFriendship(testUser1);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // friend requests friendship
        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(1, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // user deletes request for friendship
        testUser1.declineAndDeleteFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // user requests friendship
        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        // friend accepts friendship
        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // user deletes friendship
        testUser1.declineAndDeleteFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // user requests friendship
        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        // friend accepts friendship
        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());

        // friend deletes friendship
        testUser2.declineAndDeleteFriendship(testUser1);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());
    }

    @Test
    public void testGetFriendsOfAFriend() {

        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        User testUser3 = new User();
        testUser3.setUserName("user3");

        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser3.getAcceptedFriends().size());
        assertEquals(0, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());

        testUser3.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(2, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser3.getAcceptedFriends().size());
        assertEquals(1, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());

        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser3.getAcceptedFriends().size());
        assertEquals(1, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());

        testUser2.createAndAcceptFriendship(testUser3);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(2, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser3.getAcceptedFriends().size());
        assertEquals(0, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());

        assertEquals(1, testUser1.getFriendsOfAFriend(testUser2).size());

    }

    @Test
    public void testGetFriendsOfAllFriend() {

        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        User testUser3 = new User();
        testUser3.setUserName("user3");

        User testUser4 = new User();
        testUser3.setUserName("user4");

        User testUser5 = new User();
        testUser3.setUserName("user5");

        User testUser6 = new User();
        testUser3.setUserName("user6");

        User testUser7 = new User();
        testUser3.setUserName("user7");

        User testUser8 = new User();
        testUser3.setUserName("user8");

        // User 1 requests friendship with User 2
        testUser1.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());

        // User 3 requests friendship with User 2
        testUser3.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(2, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser3.getAcceptedFriends().size());
        assertEquals(1, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());

        // User 4 requests friendship with User 2
        testUser4.createAndAcceptFriendship(testUser2);
        assertEquals(0, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(3, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser4.getAcceptedFriends().size());
        assertEquals(1, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        // User 2 accepts friendship with User 1
        testUser2.createAndAcceptFriendship(testUser1);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(2, testUser2.getRequestedFriendsByFriend().size());

        // User 2 accepts friendship with User 3
        testUser2.createAndAcceptFriendship(testUser3);
        assertEquals(2, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser3.getAcceptedFriends().size());
        assertEquals(0, testUser3.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser3.getRequestedFriendsByFriend().size());

        assertEquals(1, testUser1.getFriendsOfAllFriends().size());

        // User 2 accepts friendship with User 4
        testUser2.createAndAcceptFriendship(testUser4);
        assertEquals(3, testUser2.getAcceptedFriends().size());
        assertEquals(0, testUser2.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser2.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser4.getAcceptedFriends().size());
        assertEquals(0, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        assertEquals(2, testUser1.getFriendsOfAllFriends().size());

        // User 1 requests friendship with User 5
        testUser1.createAndAcceptFriendship(testUser5);
        assertEquals(1, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser5.getAcceptedFriends().size());
        assertEquals(0, testUser5.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser5.getRequestedFriendsByFriend().size());

        // User 5 accepts friendship with User 1
        testUser5.createAndAcceptFriendship(testUser1);
        assertEquals(2, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser5.getAcceptedFriends().size());
        assertEquals(0, testUser5.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser5.getRequestedFriendsByFriend().size());

        // User 1 requests friendship with User 5
        testUser1.createAndAcceptFriendship(testUser7);
        assertEquals(2, testUser1.getAcceptedFriends().size());
        assertEquals(1, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser7.getAcceptedFriends().size());
        assertEquals(0, testUser7.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser7.getRequestedFriendsByFriend().size());

        // User 7 accepts friendship with User 1
        testUser7.createAndAcceptFriendship(testUser1);
        assertEquals(3, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser7.getAcceptedFriends().size());
        assertEquals(0, testUser7.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser7.getRequestedFriendsByFriend().size());

        // User 6 requests friendship with User 5
        testUser6.createAndAcceptFriendship(testUser5);
        assertEquals(0, testUser6.getAcceptedFriends().size());
        assertEquals(1, testUser6.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser6.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser5.getAcceptedFriends().size());
        assertEquals(0, testUser5.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser5.getRequestedFriendsByFriend().size());

        // User 5 accepts friendship with User 1
        testUser5.createAndAcceptFriendship(testUser6);
        assertEquals(1, testUser6.getAcceptedFriends().size());
        assertEquals(0, testUser6.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser6.getRequestedFriendsByFriend().size());
        assertEquals(2, testUser5.getAcceptedFriends().size());
        assertEquals(0, testUser5.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser5.getRequestedFriendsByFriend().size());

        assertEquals(3, testUser1.getFriendsOfAllFriends().size());

        // User 7 requests friendship with User 8
        testUser7.createAndAcceptFriendship(testUser8);
        assertEquals(1, testUser7.getAcceptedFriends().size());
        assertEquals(1, testUser7.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser7.getRequestedFriendsByFriend().size());
        assertEquals(0, testUser8.getAcceptedFriends().size());
        assertEquals(0, testUser8.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser8.getRequestedFriendsByFriend().size());

        // User 8 accepts friendship with User 7
        testUser8.createAndAcceptFriendship(testUser7);
        assertEquals(2, testUser7.getAcceptedFriends().size());
        assertEquals(0, testUser7.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser7.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser8.getAcceptedFriends().size());
        assertEquals(0, testUser8.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser8.getRequestedFriendsByFriend().size());

        assertEquals(4, testUser1.getFriendsOfAllFriends().size());

        // User 7 requests friendship with User 4
        testUser7.createAndAcceptFriendship(testUser4);
        assertEquals(2, testUser7.getAcceptedFriends().size());
        assertEquals(1, testUser7.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser7.getRequestedFriendsByFriend().size());
        assertEquals(1, testUser4.getAcceptedFriends().size());
        assertEquals(0, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser4.getRequestedFriendsByFriend().size());

        // User 4 accepts friendship with User 7
        testUser4.createAndAcceptFriendship(testUser7);
        assertEquals(3, testUser7.getAcceptedFriends().size());
        assertEquals(0, testUser7.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser7.getRequestedFriendsByFriend().size());
        assertEquals(2, testUser4.getAcceptedFriends().size());
        assertEquals(0, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        assertEquals(4, testUser1.getFriendsOfAllFriends().size());

        // User 4 requests friendship with User 1
        testUser4.createAndAcceptFriendship(testUser1);
        assertEquals(3, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(1, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(2, testUser4.getAcceptedFriends().size());
        assertEquals(1, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        // User 1 accepts friendship with User 4
        testUser1.createAndAcceptFriendship(testUser4);
        assertEquals(4, testUser1.getAcceptedFriends().size());
        assertEquals(0, testUser1.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser1.getRequestedFriendsByFriend().size());
        assertEquals(3, testUser4.getAcceptedFriends().size());
        assertEquals(0, testUser4.getRequestedFriendsByCurrentUser().size());
        assertEquals(0, testUser4.getRequestedFriendsByFriend().size());

        assertEquals(3, testUser1.getFriendsOfAllFriends().size());

    }
}