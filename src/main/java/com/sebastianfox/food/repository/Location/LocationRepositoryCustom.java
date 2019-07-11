package com.sebastianfox.food.repository.Location;

import com.sebastianfox.food.models.Location;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> findLocationByCity(String city);
}
