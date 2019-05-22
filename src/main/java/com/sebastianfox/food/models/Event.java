package com.sebastianfox.food.models;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sebastianfox.food.enums.EventTypes;
import com.sebastianfox.food.enums.PrivacyTypes;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="event_id")
    private Integer id;

    private String text;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "locationId", referencedColumnName = "location_id")
//    @JsonBackReference
//    private Location location;

    private Date date;

    @Enumerated(EnumType.STRING)
    private PrivacyTypes privacyType = PrivacyTypes.FRIENDS;

    @Enumerated(EnumType.STRING)
    private EventTypes eventType = EventTypes.OTHERS;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "owner_id", nullable=false)
    @JsonIgnoreProperties({"events", "ownedEvents","acceptedFriends", "requestedFriendsByCurrentUser", "requestedFriendsByFriend", "events", "ownedEvents","friendshipsFriend2", "friendsOfAllFriends", "members", "interesteds"})
//    @JsonBackReference(value="event-owner")
    private User owner;

    private String description;

    private Integer maxParticipants;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events", "ownedEvents","acceptedFriends", "requestedFriendsByCurrentUser", "requestedFriendsByFriend", "events", "ownedEvents","friendshipsFriend2", "friendsOfAllFriends", "members", "interesteds"})
    private List<User> members;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events", "ownedEvents","acceptedFriends", "requestedFriendsByCurrentUser", "requestedFriendsByFriend", "events", "ownedEvents","friendshipsFriend2", "friendsOfAllFriends", "members", "interesteds"})
    private List<User> interesteds;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events"})
    private Set<Tag> tags = new HashSet<>();

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    /**
     * Constructor
     */
    public Event() {
        this.members = new ArrayList<>();
        this.interesteds = new ArrayList<>();
    }

    //   Getter and Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Location getLocation() {
//        return location;
//    }

//    public void setLocation(Location location) {
//        this.location = location;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        if (!owner.getOwnedEvents().contains(this)){
            owner.addOwnedEvent(this);
        }
        if (!owner.getEvents().contains(this)){
            owner.addEvent(this);
        }
    }

    // Members

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void addMember(User member) {
            this.members.add(member);
            if (!member.getEvents().contains(this)) {
                member.addEvent(this);
            }
    }

    public void removeMember(User member) {
        this.members.remove(member);
        if (member.getEvents().contains(this)) {
            member.removeEvent(this);
        }
    }

    public void clearMembers() {
        for( User member: members )
        {
            if (member.getEvents().contains(this)) {
                member.removeEvent(this);
            }
            this.members.remove(member);
        }
    }

    // Interested

    public List<User> getInteresteds() {
        return interesteds;
    }

    public void setInteresteds(List<User> interesteds) {
        this.interesteds = interesteds;
    }

    public void addInterested(User interested) {
        this.interesteds.add(interested);
        if (!interested.getInterestedEvents().contains(this)) {
            interested.addInterestedEvent(this);
        }
    }

    public void removeInterested(User interested) {
        this.interesteds.remove(interested);
        if (interested.getInterestedEvents().contains(this)) {
            interested.removeInterestedEvent(this);
        }
    }

    public void clearInteresteds() {
        for( User interested: interesteds )
        {
            if (interested.getInterestedEvents().contains(this)) {
                interested.removeInterestedEvent(this);
            }
            this.interesteds.remove(interested);
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        if (!tag.getEvents().contains(this)) {
            tag.addEvent(this);
        }
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        if (tag.getEvents().contains(this)) {
            tag.removeEvent(this);
        }
    }

    public void clearTags() {
        for( Tag tag: tags )
        {
            if (tag.getEvents().contains(this)) {
                tag.removeEvent(this);
            }
            this.tags.remove(tag);
        }
    }

    public void removeFromOwnersList() {
        this.owner.removeOwnedEventFromList(this);
    }

    public EventTypes getEventType() {
        return eventType;
    }

    public void setEventType(EventTypes eventType) {
        this.eventType = eventType;
    }

    public PrivacyTypes getPrivacyType() {
        return privacyType;
    }

    public void setPrivacyTypes(PrivacyTypes privacyType) {
        this.privacyType = privacyType;
    }

    public boolean hasAvailableSpaces() {
        return this.getMembers().size() < this.getMaxParticipants();
    }

    @PreUpdate
    public void preUpdate() {
        updated = new Date();
    }

    @PrePersist
    public void prePersist() {
        updated = new Date();
        created = new Date();
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

    //  Methods

    /**
     * @param otherEvent data transformed as user from app
     */
    public void mergeDataFromOtherInstance(Event otherEvent) {
        this.text = otherEvent.getText();
        this.description = otherEvent.getDescription();
        this.eventType = otherEvent.getEventType();
        this.privacyType = otherEvent.getPrivacyType();
        this.maxParticipants = otherEvent.getMaxParticipants();
    }

    public void changeOwner(User newOwner){
        this.getOwner().getOwnedEvents().remove(this);
        this.setOwner(newOwner);
    }
}
