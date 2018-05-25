package com.sebastianfox.food.entity.event.food;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="ingrediants")

public class Ingrediant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingrediant_id")
    private int ingrediantId;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="meal_id")
    @JsonBackReference
    private Meal meal;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    /**
     *
     * Getter and Setter
     */

    public int getIngrediantId() {
        return ingrediantId;
    }

    public void setIngrediantId(int ingrediantId) {
        this.ingrediantId = ingrediantId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}