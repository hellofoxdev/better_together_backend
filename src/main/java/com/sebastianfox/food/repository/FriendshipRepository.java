package com.sebastianfox.food.repository;

import com.sebastianfox.food.models.Friendship;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
}
