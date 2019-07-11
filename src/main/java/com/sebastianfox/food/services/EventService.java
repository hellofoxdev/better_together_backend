package com.sebastianfox.food.services;

import com.sebastianfox.food.models.Event;
import com.sebastianfox.food.models.User;

public class EventService {

    public void addMember(Event event, User user) {
        event.addMember(user);
    }

    public void updateEvent(Event dbEvent, Event appEvent) {
        dbEvent.mergeDataFromOtherInstance(appEvent);
    }
}
