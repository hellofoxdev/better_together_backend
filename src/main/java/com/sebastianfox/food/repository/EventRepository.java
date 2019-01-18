package com.sebastianfox.food.repository;

import com.sebastianfox.food.models.Event;
import com.sebastianfox.food.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findByOwner(User user);

    Event findById(Integer id);

}