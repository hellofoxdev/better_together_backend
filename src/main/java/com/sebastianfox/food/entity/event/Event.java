package com.sebastianfox.food.entity.event;

import com.sebastianfox.food.entity.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_event_id")
    private Integer id;

    private String name;

    private String location;

    private Date date;

    private boolean publicEvent = false;

    @OneToOne
    @JoinColumn(name = "org_user_id")
    private User orgUser;

    private String description;

    private Integer maxParticipants;

    //

    public String getUserSession() {
        return orgUser.getSession();
    }

    //   Getter and Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getOrgUser() {
        return orgUser;
    }

    public void setOrgUser(User orgUser) {
        this.orgUser = orgUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(boolean publicEvent) {
        this.publicEvent = publicEvent;
    }
}
