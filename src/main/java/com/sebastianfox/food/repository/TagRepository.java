package com.sebastianfox.food.repository;

import com.sebastianfox.food.models.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
}
