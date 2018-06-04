package com.sebastianfox.food.repository;

import com.sebastianfox.food.entity.user.UserImage;
import org.springframework.data.repository.CrudRepository;

public interface UserImageRepository extends CrudRepository<UserImage, Long> {
}
