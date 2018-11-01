package com.sebastianfox.food.repository;

import com.sebastianfox.food.entity.user.User;
import org.springframework.data.repository.CrudRepository;

//import hello.User;

//import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
    //List<User> findByEmail(String email);
    User findByEmail(String email);

    User findByUsername(String name);

    User findByFacebookMail(String mail);

    User findById(Integer id);

    User findByFacebookUsername(String name);
}
