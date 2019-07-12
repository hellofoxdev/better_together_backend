package com.sebastianfox.food.repository.Location;

import com.sebastianfox.food.models.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long>, LocationRepositoryCustom {
    Location findById(Integer id);
    Location findByLangitudeLikeAndLongitudeLike(Float langitude, Float longitude);
    Location findByLangitudeIsAndLongitudeIs(Float langitude, Float longitude);
    Location findByLangitudeBetweenAndLongitudeBetween(Float langitudeMin, Float langitudeMax, Float longitudeMin, Float longitudeMax);
    }
