package com.sebastianfox.food.repository;

import com.sebastianfox.food.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
