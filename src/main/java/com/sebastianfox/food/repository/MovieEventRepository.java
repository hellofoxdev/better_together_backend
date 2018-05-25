package com.sebastianfox.food.repository;

import com.sebastianfox.food.entity.event.movie.MovieEvent;
import org.springframework.data.repository.CrudRepository;

//import hello.User;

//import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MovieEventRepository extends CrudRepository<MovieEvent, Long> {
}