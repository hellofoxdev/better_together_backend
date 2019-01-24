package com.sebastianfox.food.repository;

import com.sebastianfox.food.models.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByCity(String city);
}
