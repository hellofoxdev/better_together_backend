package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("unused")
@Entity
@Table(name="invitations")
public class Invitation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="invitation_id")
    private Integer id;

    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    private User invited;

    @OneToOne(fetch = FetchType.LAZY)
    private User invitedBy;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getInvited() {
        return invited;
    }

    public void setInvited(User invited) {
        this.invited = invited;
    }

    public User getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(User invitedBy) {
        this.invitedBy = invitedBy;
    }

    @PreUpdate
    public void preUpdate() {
        updated = new Date();
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        created = now;
        updated = now;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
