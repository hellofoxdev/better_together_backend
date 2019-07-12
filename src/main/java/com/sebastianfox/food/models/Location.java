package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressWarnings("unused")
@Entity
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="location_id")
    private Integer id;

    private String name;

    private Float langitude;

    private Float longitude;

    private String Street;

    private Integer zip;

    private String city;

//    @OneToOne(mappedBy = "location")
    @OneToMany(mappedBy="location")
    @JsonIgnoreProperties({"event"})
    private List<Event> events;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    /**
     * Constructor
     */
    public Location() {
        this.events = new ArrayList<>();
    }

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

    public Float getLangitude() {
        return langitude;
    }

    public void setLangitude(Float langitude) {
        this.langitude = langitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public Event getEvent() {
//        return event;
//    }

//    public void setEvent(Event event) {
//        this.event = event;
//    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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

    public void addEvent(Event event) {
        this.events.add(event);
        if (event.getLocation() != this){
            event.setLocation(this);
        }
    }
}
