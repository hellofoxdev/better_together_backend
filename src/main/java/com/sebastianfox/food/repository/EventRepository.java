package com.sebastianfox.food.repository;

import com.sebastianfox.food.entity.event.Event;
import com.sebastianfox.food.entity.user.User;
import javax.persistence.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;

//import hello.User;

//import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends CrudRepository<Event, Long> {

    Event findByOrgUser(User user);

}