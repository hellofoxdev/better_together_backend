package com.sebastianfox.food.repository.Event;

import com.sebastianfox.food.enums.PrivacyTypes;
import com.sebastianfox.food.models.Event;
//import com.sebastianfox.food.models.Location;
import com.sebastianfox.food.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@SuppressWarnings({"unused"})
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findByOwner(User user);
//    List<Event> findByLocation(Location location);

    Event findById(UUID id);
//    List<Event> findAllByPrivacyType(PrivacyTypes privacyType);
    List<Event> findAllByDeletedIsFalse();
    List<Event> findAllByDeletedIsFalseAndOpenIsTrue();
}