package com.sebastianfox.food.entity;

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

    @OneToMany(mappedBy="meal")
    @JsonManagedReference
    private List<Ingrediant> ingrediants;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Meal_User",
            joinColumns = { @JoinColumn(name = "meal_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    Set<User> users = new HashSet<>();

    @Column(name="max_participants")
    private int maxParticipants;

    @Column(name="created_at")
    private Date createdAt = new Date();


    private PhoneType gender;

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

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}