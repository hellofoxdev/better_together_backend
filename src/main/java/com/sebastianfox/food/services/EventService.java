package com.sebastianfox.food.services;

import com.sebastianfox.food.models.Event;
import com.sebastianfox.food.models.Tag;
import com.sebastianfox.food.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventService {

    public void addMember(Event event, User user) {
        event.addMember(user);
    }

    public void updateEvent(Event dbEvent, Event appEvent) {
        dbEvent.mergeDataFromOtherInstance(appEvent);
    }

    public void prepareForDelete(Event event) {

        List<User> membersToDelete = new ArrayList<>();
        membersToDelete.addAll(event.getMembers());
        for (User member : membersToDelete) {
            if (event.getMembers().contains(member)) {
                event.removeMember(member);
            }
        }

        List<User> interestedsToDelete = new ArrayList<>();
        interestedsToDelete.addAll(event.getInteresteds());
        for (User interested : interestedsToDelete) {
            if (event.getInteresteds().contains(interested)) {
                event.removeInterested(interested);
            }
        }

        List<Tag> tagsToDelete = new ArrayList<>();
        tagsToDelete.addAll(event.getTags());
        for (Tag tag : tagsToDelete) {
            if (event.getTags().contains(tag)) {
                event.removeTag(tag);
            }
        }

        // remove from locatiuon array
        event.getLocation().removeEvent(event);

        // remove from owners list
        event.removeFromOwnersList();
    }
}
