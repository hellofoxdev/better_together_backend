package com.sebastianfox.food.services;

import com.sebastianfox.food.models.Event;
import com.sebastianfox.food.models.Tag;
import com.sebastianfox.food.models.User;

import java.util.ArrayList;
import java.util.List;

public class EventService {

    public void attendToEvent(Event event, User user) {
        if (!event.isDeleted() && event.isOpen()) {
            event.addInterested(user);
        }
    }

    public void acceptInterested(Event event, User interested){
        if (!event.isDeleted() && event.getInteresteds().contains(interested)) {
            event.addMember(interested);
            event.removeInterested(interested);
        }
    }

    public void declineInterested(Event event, User interested){
        if (!event.isDeleted() && event.getInteresteds().contains(interested)) {
            event.removeInterested(interested);
        }
    }

    public void deleteEvent(Event event) {
        event.setDeleted(true);
        event.setOpen(false);
    }

    public void prepareForDelete(Event event) {

        List<User> membersToDelete = new ArrayList<>(event.getMembers());
        for (User member : membersToDelete) {
            if (event.getMembers().contains(member)) {
                event.removeMember(member);
            }
        }

        List<User> interestedsToDelete = new ArrayList<>(event.getInteresteds());
        for (User interested : interestedsToDelete) {
            if (event.getInteresteds().contains(interested)) {
                event.removeInterested(interested);
            }
        }

        List<Tag> tagsToDelete = new ArrayList<>(event.getTags());
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
