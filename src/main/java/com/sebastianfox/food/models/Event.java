package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sebastianfox.food.enums.EventTypes;
import com.sebastianfox.food.enums.PrivacyTypes;
import com.sebastianfox.food.utils.CRUDDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="event_id")
    private Integer id;

    private String text;

    private String location;

    private Date date;

    @Enumerated(EnumType.STRING)
    private PrivacyTypes privacyTypes = PrivacyTypes.FRIENDS;

    @Enumerated(EnumType.STRING)
    private EventTypes eventType = EventTypes.OTHERS;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable=false)
    @JsonIgnoreProperties({"events", "ownedEvents"})
    private User owner;

    private String description;

    private Integer maxParticipants;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events", "ownedEvents"})
    private Set<User> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events", "ownedEvents"})
    private Set<User> memberRequests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events"})
    private Set<Tag> tags = new HashSet<>();
    private Date updated;
    private Date created;


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
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
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

    public Set<User> getMemberRequests() {
        return memberRequests;
    }

    public void setMemberRequests(Set<User> memberRequests) {
        this.memberRequests = memberRequests;
    }

    public void addMemberRequest(User memberRequest) {
        this.memberRequests.add(memberRequest);
        if (!memberRequest.getEvents().contains(this)) {
            memberRequest.addEvent(this);
        }
    }

    public void removeMemberRequest(User memberRequest) {
        this.memberRequests.remove(memberRequest);
        if (memberRequest.getEvents().contains(this)) {
            memberRequest.removeEvent(this);
        }
    }

    public void clearMemberRequests() {
        for( User memberRequest: memberRequests )
        {
            if (memberRequest.getEvents().contains(this)) {
                memberRequest.removeEvent(this);
            }
            this.memberRequests.remove(memberRequest);
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

    public PrivacyTypes getPrivacyTypes() {
        return privacyTypes;
    }

    public void setPrivacyTypes(PrivacyTypes privacyTypes) {
        this.privacyTypes = privacyTypes;
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
}
