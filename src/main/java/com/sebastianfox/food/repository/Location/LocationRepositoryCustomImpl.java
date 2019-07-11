package com.sebastianfox.food.repository.Location;

import com.sebastianfox.food.models.Location;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Location> findLocationByCity(String city) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);
        Root<Location> location = query.from(Location.class);

        Path<String> cityPath = location.get("city");

        List<Predicate> predicates = new ArrayList<>();
        //for (String email : emails) {
            predicates.add(cb.like(cityPath, city));
        //}
        query.select(location)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
