package com.sebastianfox.food.models;

import com.sebastianfox.food.services.EventService;
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

        testEvent1.removeMember(testUser2);

        assertEquals( 1, testEvent1.getMembers().size());
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

    @Test
    public void prepareForDeletion()
    {
        User testUser1 = new User();
        testUser1.setUserName("user1");

        User testUser2 = new User();
        testUser2.setUserName("user2");

        User testUser3 = new User();
        testUser3.setUserName("user3");

        Location testLocation = new Location();
        testLocation.setLangitude((float) 3.2);
        testLocation.setLongitude((float) 3.2);

        Event testEvent1 = new Event();
        testEvent1.setOwner(testUser1);
        testEvent1.addMember(testUser2);
        testEvent1.addMember(testUser3);
        testEvent1.setLocation(testLocation);

        assertEquals( 3, testEvent1.getMembers().size());
        assertEquals( 1, testUser1.getOwnedEvents().size());
        assertEquals( 1, testUser1.getEvents().size());
        assertEquals( 0, testUser2.getOwnedEvents().size());
        assertEquals( 1, testUser2.getEvents().size());

        EventService eventService = new EventService();
        eventService.prepareForDelete(testEvent1);

        assertEquals( 0, testEvent1.getMembers().size());
        assertEquals( 0, testUser1.getOwnedEvents().size());
        assertEquals( 0, testUser1.getEvents().size());
        assertEquals( 0, testUser2.getOwnedEvents().size());
        assertEquals( 0, testUser2.getEvents().size());
    }
}