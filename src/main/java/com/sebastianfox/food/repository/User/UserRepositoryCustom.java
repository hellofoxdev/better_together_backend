package com.sebastianfox.food.repository.User;

import com.sebastianfox.food.models.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findUserByFancyStuff(String email);
}
