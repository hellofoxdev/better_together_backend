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

    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    //@MapsId
    private User invited;

    @OneToOne(fetch = FetchType.LAZY)
    //@MapsId
    private User invitedBy;

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

}
