package com.sebastianfox.food.repository.User;

import com.sebastianfox.food.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

//import hello.User;

//import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@SuppressWarnings("unused")
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
    User findByEmail(String email);
    User findByUserName(String name);
    User findByFacebookAccountEmail(String mail);
    User findByFacebookAccountId(Long id);
    User findById(UUID id);
    List<User> findAllByDeletedIsFalse();


    @Query(value = "SELECT * FROM Users u WHERE u.email = :email",
            nativeQuery = true)
    User findUserByMyFancyMail(
            @Param("email") String email);

    @Query(value = "SELECT COUNT(*) FROM Users",
            nativeQuery = true)
    Long countUsers();
}
