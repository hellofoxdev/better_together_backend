package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@SuppressWarnings({"unused"})
@Entity
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="tags_events",
            joinColumns=@JoinColumn(name="tag_id"),
            inverseJoinColumns=@JoinColumn(name="event_id")
    )
    @JsonIgnoreProperties({"tags"})
    private List<Event> events;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    /**
     * Getter and Setter
     */

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        this.events.add(event);
//        if (!event.getTags().contains(this)){
//            event.addTag(this);
//        }
    }

    public void removeEvent(Event event){
        this.events.remove(event);
//        if (event.getTags().contains(this)) {
//            event.removeTag(this);
//        }
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
