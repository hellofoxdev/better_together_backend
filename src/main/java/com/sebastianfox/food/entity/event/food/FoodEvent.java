package com.sebastianfox.food.entity.event.food;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sebastianfox.food.entity.event.Event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="food_events")
public class FoodEvent extends Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="food_event_id")
    private Integer id;

    private String type;

    @OneToMany(mappedBy="foodEvent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Meal> meal;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}