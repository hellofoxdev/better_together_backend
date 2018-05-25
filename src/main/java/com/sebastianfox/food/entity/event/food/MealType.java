package com.sebastianfox.food.entity.event.food;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="meal_types")
public class MealType implements Serializable {

    /**
     * variables
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="meal_type_id")
    private int mealTypeId;
}
