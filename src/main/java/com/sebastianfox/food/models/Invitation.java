package com.sebastianfox.food.models;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name="invitations")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="invitation_id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
