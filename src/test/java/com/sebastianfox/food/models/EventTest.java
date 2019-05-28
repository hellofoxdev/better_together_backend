package com.sebastianfox.food.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest
{
    @Test
    public void testGetOwner()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        Event testEvent = new Event();
        testEvent.setOwner(testUser1);
        testEvent.addMember(testUser2);

        assertEquals( "user1", testEvent.getOwner().getUserName());
    }

    @Test
    public void testSizeOfMembers()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        Event testEvent1 = new Event();
        testEvent1.setOwner(testUser1);

        assertEquals( 1, testEvent1.getMembers().size());

        User testUser2 = new User();
        testUser2.setUserName("user2");

        testEvent1.addMember(testUser2);

        assertEquals( 2, testEvent1.getMembers().size());
        assertEquals( 1, testUser1.getOwnedEvents().size());
        assertEquals( 0, testUser2.getOwnedEvents().size());

    }

    @Test
    public void testChangeUser()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        Event testEvent1 = new Event();
        testEvent1.setOwner(testUser1);
        testEvent1.addMember(testUser2);

        assertEquals( 2, testEvent1.getMembers().size());
        assertEquals( 1, testUser1.getOwnedEvents().size());
        assertEquals( 0, testUser2.getOwnedEvents().size());

        testEvent1.changeOwner(testUser2);
        assertEquals( 2, testEvent1.getMembers().size());
        assertEquals( 0, testUser1.getOwnedEvents().size());
        assertEquals( 1, testUser2.getOwnedEvents().size());

    }
}