package com.sebastianfox.food.repository;

import com.sebastianfox.food.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//import hello.User;

//import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@SuppressWarnings("unused")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUserName(String name);
    User findByFacebookAccountEmail(String mail);
    User findByFacebookAccountId(Long id);
    User findById(UUID id);
}
