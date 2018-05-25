package com.sebastianfox.food.entity.event.food;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="meals")
public class Meal implements Serializable {

    /**
     * variables
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="meal_id")
    private int mealId;

    @Column(name="title")
    private String title;

    @OneToOne
    @JoinColumn(name = "meal_type_id")
    private MealType mealType;

    @OneToMany(mappedBy="meal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ingrediant> ingrediants;


    @ManyToOne
    @JoinColumn(name="food_event_id")
    @JsonBackReference
    private FoodEvent foodEvent;

    /**
     * constructor
     */
    public Meal() {
        ingrediants = new ArrayList<Ingrediant>();
    }

    public Collection<Ingrediant> getIngrediants() {
        return ingrediants;
    }

    public void addIngrediant(Ingrediant ingrediant) {
        if (!getIngrediants().contains(ingrediant)) {
            getIngrediants().add(ingrediant);
            if (ingrediant.getMeal() != null) {
                ingrediant.getMeal().getIngrediants().remove(ingrediant);
            }
            ingrediant.setMeal(this);
        }
    }

    public void removeIngrediant(Ingrediant ingrediant) {

        ingrediants.remove(ingrediant);
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}