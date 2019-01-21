package com.sebastianfox.food.repository;

import com.sebastianfox.food.models.User;
import org.springframework.data.repository.CrudRepository;

//import hello.User;

//import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String name);
    User findByFacebookMail(String mail);
    User findByFacebookId(Long id);
    User findById(Integer id);
    User findByFacebookUsername(String name);
}
